package yearnlune.lab.namingcenter.database.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Project : naming-center
 * Created by IntelliJ IDEA
 * Author : DONGHWAN, KIM
 * DATE : 2020.03.03
 * DESCRIPTION :
 */

public class NamingDTO {

    @Data
    @NoArgsConstructor
    public static class CommonResponse {
        private Integer idx;

        private String name;

        private String description;

        @Builder
        public CommonResponse(Integer idx, String name, String description) {
            this.idx = idx;
            this.name = name;
            this.description = description;
        }
    }

    @Data
    @NoArgsConstructor
    public static class RegisterRequest {
        private String name;

        private String description;

        private AccountDTO.CommonResponse account;

        @Builder
        public RegisterRequest(String name, String description, AccountDTO.CommonResponse account) {
            this.name = name;
            this.description = description;
            this.account = account;
        }
    }

    @Data
    @NoArgsConstructor
    public static class AutoCompleteResponse {
        private List<String> namingList;

        @Builder
        public AutoCompleteResponse(List<String> namingList) {
            this.namingList = namingList;
        }
    }
}
