package yearnlune.lab.namingcenter.controller;

import static yearnlune.lab.namingcenter.constant.AccountConstant.*;

import javax.validation.Valid;

import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import yearnlune.lab.namingcenter.database.dto.AccountDTO;
import yearnlune.lab.namingcenter.service.AccountService;

/**
 * Project : naming-center
 * Created by IntelliJ IDEA
 * Author : DONGHWAN, KIM
 * DATE : 2020.02.11
 * DESCRIPTION :
 */

@Slf4j
@RestController
public class AccountController {

	private final AccountService accountService;

	public AccountController(AccountService accountService) {
		this.accountService = accountService;
	}

	@PostMapping(ACCOUNT)
	public ResponseEntity<AccountDTO.CommonResponse> registerAccount(
		@RequestBody @Valid AccountDTO.RegisterRequest registerRequest) {
		AccountDTO.CommonResponse account = accountService.saveAccountIfNotExist(registerRequest);
		return new ResponseEntity<>(account, HttpStatus.CREATED);
	}

	@PostMapping(LOGIN)
	public ResponseEntity<AccountDTO.CommonResponse> loginAccount(
		@RequestBody AccountDTO.LoginRequest loginRequest) {
		Pair<AccountDTO.CommonResponse, HttpStatus> account = accountService.loginAccount(loginRequest);

		if (account.getSecond().equals(HttpStatus.OK)) {
			account.getFirst().setJwt(accountService.createAuthorizationToken(account.getFirst()));
		}

		return new ResponseEntity<>(account.getFirst(), account.getSecond());
	}

	@PostMapping(VALIDATE)
	public ResponseEntity<String> validateToken(@RequestBody AccountDTO.TokenValidationRequest tokenValidationRequest) {
		return new ResponseEntity<>("",
			accountService.isAvailableToken(tokenValidationRequest.getJwt()) ? HttpStatus.OK : HttpStatus.UNAUTHORIZED);
	}
}
