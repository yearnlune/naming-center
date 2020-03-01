package yearnlune.lab.namingcenter.database.service;

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

    private AccountDTO.CommonResponse convertToCommonResponse(Account account) {
        return ConvertObject.object2Object(account, AccountDTO.CommonResponse.class);
    }

    public AccountDTO.CommonResponse saveAccountIfNotExist(AccountDTO.RegisterRequest registerRequest) {
        if (!hasAccount(registerRequest.getId())) {
            return convertToCommonResponse(accountRepository.save(ConvertObject.object2Object(registerRequest, Account.class).passwordEncode(passwordEncoder)));
        }
        return null;
    }

    public AccountDTO.CommonResponse loginAccount(AccountDTO.LoginRequest loginRequest) {
        Account account = accountRepository.findById(loginRequest.getId()).orElse(null);

        // TODO Account 존재하지 않을 경우
        if (account == null) {
            return null;
        }

        if (passwordEncoder.matches(loginRequest.getPassword(), account.getPassword())) {
            return convertToCommonResponse(account);
        } else {
            // TODO Account 비번이 틀렸을 경우
            return null;
        }
    }
}
