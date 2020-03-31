package yearnlune.lab.namingcenter.database.service;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import yearnlune.lab.convertobject.ConvertObject;
import yearnlune.lab.namingcenter.database.repository.AccountRepository;
import yearnlune.lab.namingcenter.database.repository.LogRepository;
import yearnlune.lab.namingcenter.database.table.Account;
import yearnlune.lab.namingcenter.database.table.Log;

/**
 * Project : naming-center
 * Created by IntelliJ IDEA
 * Author : DONGHWAN, KIM
 * DATE : 2020.03.19
 * DESCRIPTION :
 */

@Service
public class LogService {

    private final LogRepository logRepository;

    private final AccountRepository accountRepository;

    public LogService(LogRepository logRepository, AccountRepository accountRepository) {
        this.logRepository = logRepository;
        this.accountRepository = accountRepository;
    }

    public void saveLog(LogContent logContent, String accountId) {
        logRepository.save(Log.builder()
                .account(accountId == null ? null : accountRepository.findById(accountId).orElse(null))
                .endpoint(logContent.getRequestUri())
                .content(ConvertObject.objectClass2String(logContent))
                .build());
    }

    @Data
    @NoArgsConstructor
    public static class LogContent {
        private String requestUri;

        private String remoteAddr;

        private String method;

        @Builder
        public LogContent(String requestUri, String remoteAddr, String method) {
            this.requestUri = requestUri;
            this.remoteAddr = remoteAddr;
            this.method = method;
        }
    }
}
