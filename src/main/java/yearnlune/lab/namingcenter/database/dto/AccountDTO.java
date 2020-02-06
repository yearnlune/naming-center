package yearnlune.lab.namingcenter.database.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
public class AccountDTO {

    @Getter
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
}
