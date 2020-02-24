package yearnlune.lab.namingcenter.database.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import yearnlune.lab.convertobject.ConvertObject;
import yearnlune.lab.namingcenter.database.dto.AccountDTO;
import yearnlune.lab.namingcenter.database.repository.AccountRepository;
import yearnlune.lab.namingcenter.database.table.Account;

@Service
public class AccountService {

    private final PasswordEncoder passwordEncoder;

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean hasAccount(String id) {
        return accountRepository.existsById(id);
    }

    public Account saveAccountIfNotExist(AccountDTO.RegisterRequest registerRequest) {
        if (!hasAccount(registerRequest.getId())) {
            return accountRepository.save(ConvertObject.object2Object(registerRequest, Account.class).passwordEncode(passwordEncoder));
        }
        return null;
    }

}
