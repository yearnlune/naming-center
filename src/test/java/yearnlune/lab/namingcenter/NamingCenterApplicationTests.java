package yearnlune.lab.namingcenter;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;
import yearnlune.lab.namingcenter.database.dto.AccountDTO;
import yearnlune.lab.namingcenter.database.dto.NamingDTO;
import yearnlune.lab.namingcenter.database.repository.NamingRepository;
import yearnlune.lab.namingcenter.database.service.AccountService;
import yearnlune.lab.namingcenter.database.service.NamingService;

import java.util.List;

@SpringBootTest
class NamingCenterApplicationTests {

    @Autowired
    private AccountService accountService;

    @Autowired
    private NamingService namingService;

    @Autowired
    private NamingRepository namingRepository;

    @Test
    void tSaveAccountIfNotExist() {
        AccountDTO.CommonResponse account = accountService.saveAccountIfNotExist(AccountDTO.RegisterRequest.builder()
                .id("TEST")
                .name("TEST_NAME")
                .password("password")
                .build());
        Assert.notNull(account, "사용자 등록 완료");
    }

    @Test
    void tSaveNamingIfNotExist() {
        NamingDTO.CommonResponse naming = namingService.saveNamingIfNotExist(
                NamingDTO.RegisterRequest.builder()
                        .name("TEST_1")
                        .description("TEST_1_DESC")
                        .account(AccountDTO.CommonResponse.builder()
                                .idx(1).id("TEST").name("TEST_NAME")
                                .build())
                        .build()
        );
        Assert.notNull(naming, "네이밍 등록 완료");
    }

    @Test
    void tFindNaming() {
        List<NamingDTO.CommonResponse> namingList = namingService.findNaming("pass");
        Assert.notEmpty(namingList, "YES");
    }

    @Test
    void tFindNames() {
        List<String> nameList = namingRepository.findNames();
        Assert.notEmpty(nameList, "nameList is empty");
    }
}
