package yearnlune.lab.namingcenter.database.table;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import yearnlune.lab.namingcenter.database.dto.AccountDTO;

import javax.persistence.*;

@Entity
@Table(name = "ot_acnt")
@NoArgsConstructor
@Getter
@Setter
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idx;

    @Column(length = 64, nullable = false, unique = true)
    private String id;

    @Column(length = 128, nullable = false)
    private String name;

    @Column(length = 128, nullable = false)
    private String password;

    @Builder
    public Account(String id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public Account(AccountDTO.RegisterRequest registerRequest) {
        this.id = registerRequest.getId();
        this.name = registerRequest.getName();
        this.password = registerRequest.getPassword();
    }
}
