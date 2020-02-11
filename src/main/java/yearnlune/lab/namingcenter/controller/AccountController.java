package yearnlune.lab.namingcenter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yearnlune.lab.namingcenter.database.dto.AccountDTO;
import yearnlune.lab.namingcenter.database.service.AccountService;
import yearnlune.lab.namingcenter.database.table.Account;

/**
 * Project : naming-center
 * Created by IntelliJ IDEA
 * Author : DONGHWAN, KIM
 * DATE : 2020.02.11
 * DESCRIPTION :
 */

@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;

    @RequestMapping("/account")
    public ResponseEntity<Account> createAccount(@RequestBody AccountDTO.RegisterRequest accountDTO) {
        Account account = accountService.saveAccountIfNotExist(accountDTO);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }
}
