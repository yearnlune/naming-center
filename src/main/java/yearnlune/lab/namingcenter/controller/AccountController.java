package yearnlune.lab.namingcenter.controller;

import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import yearnlune.lab.namingcenter.database.dto.AccountDTO;
import yearnlune.lab.namingcenter.database.service.AccountService;

import javax.servlet.http.HttpServletResponse;

import static yearnlune.lab.namingcenter.constant.AccountConstant.ACCOUNT;
import static yearnlune.lab.namingcenter.constant.AccountConstant.LOGIN;
/**
 * Project : naming-center
 * Created by IntelliJ IDEA
 * Author : DONGHWAN, KIM
 * DATE : 2020.02.11
 * DESCRIPTION :
 */

@RestController
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @RequestMapping(value = ACCOUNT, method = RequestMethod.POST)
    public ResponseEntity<AccountDTO.CommonResponse> createAccount(
            HttpServletResponse httpServletResponse,
            @RequestBody AccountDTO.RegisterRequest registerRequest) {
        AccountDTO.CommonResponse account = accountService.saveAccountIfNotExist(registerRequest);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @RequestMapping(value = LOGIN, method = RequestMethod.POST)
    public ResponseEntity<AccountDTO.CommonResponse> loginAccount(
            HttpServletResponse httpServletResponse,
            @RequestBody AccountDTO.LoginRequest loginRequest) {
        Pair<AccountDTO.CommonResponse, HttpStatus> account = accountService.loginAccount(loginRequest);
        return new ResponseEntity<>(account.getFirst(), account.getSecond());
    }
}
