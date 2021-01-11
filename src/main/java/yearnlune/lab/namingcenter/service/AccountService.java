package yearnlune.lab.namingcenter.service;

import static yearnlune.lab.namingcenter.config.AuthenticationFilter.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import yearnlune.lab.convertobject.ConvertObject;
import yearnlune.lab.namingcenter.database.dto.AccountDTO;
import yearnlune.lab.namingcenter.database.repository.AccountRepository;
import yearnlune.lab.namingcenter.database.table.Account;
import yearnlune.lab.namingcenter.exception.BadRequestException;
import yearnlune.lab.namingcenter.exception.TooManyRequestException;

@Slf4j
@Service
public class AccountService {
	private final PasswordEncoder passwordEncoder;

	private final AccountRepository accountRepository;

	private final RedisService redisService;

	public AccountService(AccountRepository accountRepository, PasswordEncoder passwordEncoder,
		RedisService redisService) {
		this.accountRepository = accountRepository;
		this.passwordEncoder = passwordEncoder;
		this.redisService = redisService;
	}

	public AccountDTO.CommonResponse saveAccountIfNotExist(AccountDTO.RegisterRequest registerRequest) {
		if (!hasAccount(registerRequest.getId())) {
			return convertToCommonResponse(accountRepository.save(
				ConvertObject.object2Object(registerRequest, Account.class).passwordEncode(passwordEncoder)));
		}
		return null;
	}

	public List<AccountDTO.CommonResponse> findAllAccounts() {
		List<Account> accountList = accountRepository.findAll();
		return convertToCommonResponse(accountList);
	}

	public Pair<AccountDTO.CommonResponse, HttpStatus> loginAccount(AccountDTO.LoginRequest loginRequest) {
		if (redisService.isLoginLock(loginRequest.getId())) {
			throw new TooManyRequestException("로그인에 5회 실패하였습니다. 잠시 후 다시 시도해주세요.");
		}

		Account account = accountRepository.findById(loginRequest.getId()).orElse(null);

		if (account == null || !isCorrectPassword(loginRequest.getPassword(), account.getPassword())) {
			redisService.increaseLoginFailCount(loginRequest.getId());
			return Pair.of(AccountDTO.CommonResponse.builder().build(), HttpStatus.UNAUTHORIZED);
		} else {
			redisService.initializeLoginFailCount(loginRequest.getId());
			return Pair.of(convertToCommonResponse(account), HttpStatus.OK);
		}
	}

	public AccountDTO.CommonResponse updateAccount(Integer accountIdx, AccountDTO.PatchedRequest patchedRequest) throws
		BadRequestException {
		Optional<Account> accountOptional = accountRepository.findById(accountIdx);

		if (!accountOptional.isPresent()) {
			throw new BadRequestException("해당 계정이 존재하지 않습니다. : " + accountIdx.toString());
		}

		accountOptional.ifPresent(account -> {
			if (patchedRequest.getName() != null) {
				account.setName(patchedRequest.getName());
			}
			if (patchedRequest.getPassword() != null) {
				account.setPassword(passwordEncoder.encode(patchedRequest.getPassword()));
			}
		});

		return convertToCommonResponse(accountOptional.get());
	}

	public String createAuthorizationToken(AccountDTO.CommonResponse account) {
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList(
			account.getRole());

		return Jwts.builder()
			.setSubject("namingCenterJWT")
			.setIssuer("namingCenter")
			.claim("authorities",
				grantedAuthorities.stream()
					.map(GrantedAuthority::getAuthority)
					.collect(Collectors.toList()))
			.claim("id", account.getId())
			.setIssuedAt(new Date(System.currentTimeMillis()))
			.setExpiration(new Date(System.currentTimeMillis() + DURATION))
			.signWith(SignatureAlgorithm.HS512,
				SECRET_KEY.getBytes())
			.compact();
	}

	public boolean isAvailableToken(String jwt) {
		boolean isAvailable = false;
		try {
			Jws<Claims> claimsJws = Jwts.parser()
				.setSigningKey(SECRET_KEY.getBytes())
				.parseClaimsJws(jwt);
			isAvailable = true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return isAvailable;
	}

	private boolean hasAccount(String id) {
		return accountRepository.existsById(id);
	}

	private AccountDTO.CommonResponse convertToCommonResponse(Account account) {
		return ConvertObject.object2Object(account, AccountDTO.CommonResponse.class);
	}

	private List<AccountDTO.CommonResponse> convertToCommonResponse(List<Account> accounts) {
		return accounts.stream()
			.map(account -> ConvertObject.object2Object(account, AccountDTO.CommonResponse.class))
			.collect(Collectors.toList());
	}

	private boolean isCorrectPassword(String loginPassword, String accountPassword) {
		return passwordEncoder.matches(loginPassword, accountPassword);
	}

}
