package yearnlune.lab.namingcenter;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;
import yearnlune.lab.convertobject.ConvertObject;
import yearnlune.lab.namingcenter.database.dto.AccountDTO;
import yearnlune.lab.namingcenter.database.repository.AccountRepository;
import yearnlune.lab.namingcenter.database.service.AccountService;
import yearnlune.lab.namingcenter.database.table.Account;

@SpringBootTest
class NamingCenterApplicationTests {

    @Autowired
    private AccountService accountService;

    @Test
    void tSaveAccountIfNotExist() {
        Account account = accountService.saveAccountIfNotExist(AccountDTO.RegisterRequest.builder()
                .id("id")
                .name("name")
                .password("password")
                .build());
        Assert.notNull(account, "사용자 등록 완료");
    }

}
