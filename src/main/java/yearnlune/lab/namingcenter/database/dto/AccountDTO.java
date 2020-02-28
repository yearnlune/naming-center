package yearnlune.lab.namingcenter.database.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@NoArgsConstructor
public class AccountDTO {

    @Data
    @NoArgsConstructor
    public static class CommonResponse {
        private Integer idx;

        private String id;

        private String name;

        @Builder
        public CommonResponse(Integer idx, String id, String name) {
            this.idx = idx;
            this.id = id;
            this.name = name;
        }
    }

    @Data
    @NoArgsConstructor
    public static class RegisterRequest {
        private String id;

        private String name;

        private String password;

        @Builder
        public RegisterRequest(String id, String name, String password) {
            this.id = id;
            this.name = name;
            this.password = password;
        }
    }

    @Data
    @NoArgsConstructor
    public static class LoginRequest {
        private String id;

        private String password;

        @Builder
        public LoginRequest(String id, String password) {
            this.id = id;
            this.password = password;
        }
    }
}
