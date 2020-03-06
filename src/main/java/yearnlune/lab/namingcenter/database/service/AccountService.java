package yearnlune.lab.namingcenter.database.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import yearnlune.lab.convertobject.ConvertObject;
import yearnlune.lab.namingcenter.database.dto.AccountDTO;
import yearnlune.lab.namingcenter.database.repository.AccountRepository;
import yearnlune.lab.namingcenter.database.table.Account;

@Slf4j
@Service
public class AccountService {
    private final PasswordEncoder passwordEncoder;

    private final AccountRepository accountRepository;

    private final RedisService redisService;

    public AccountService(AccountRepository accountRepository, PasswordEncoder passwordEncoder, RedisService redisService) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.redisService = redisService;
    }

    public AccountDTO.CommonResponse saveAccountIfNotExist(AccountDTO.RegisterRequest registerRequest) {
        if (!hasAccount(registerRequest.getId())) {
            return convertToCommonResponse(accountRepository.save(ConvertObject.object2Object(registerRequest, Account.class).passwordEncode(passwordEncoder)));
        }
        return null;
    }

    public Pair<AccountDTO.CommonResponse, HttpStatus> loginAccount(AccountDTO.LoginRequest loginRequest) {
        if (redisService.isLoginLock(loginRequest.getId())) {
            return Pair.of(AccountDTO.CommonResponse.builder().build(), HttpStatus.TOO_MANY_REQUESTS);
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

    private boolean hasAccount(String id) {
        return accountRepository.existsById(id);
    }

    private AccountDTO.CommonResponse convertToCommonResponse(Account account) {
        return ConvertObject.object2Object(account, AccountDTO.CommonResponse.class);
    }

    private boolean isCorrectPassword(String loginPassword, String accountPassword) {
        return passwordEncoder.matches(loginPassword, accountPassword);
    }

}
