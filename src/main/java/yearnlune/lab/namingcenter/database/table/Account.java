package yearnlune.lab.namingcenter.database.table;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.sql.Timestamp;

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

    @Column(nullable = false)
    @CreationTimestamp
    private Timestamp createdAt;

    @Builder
    public Account(String id, String name, String password, Timestamp createdAt) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.createdAt = createdAt;
    }

    public Account passwordEncode(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(password);
        return this;
    }
}
