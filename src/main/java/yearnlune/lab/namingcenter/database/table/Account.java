package yearnlune.lab.namingcenter.database.table;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "ot_acnt")
@NoArgsConstructor
@Getter
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
}
