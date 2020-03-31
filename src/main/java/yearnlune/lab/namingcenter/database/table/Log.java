package yearnlune.lab.namingcenter.database.table;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;

/**
 * Project : naming-center
 * Created by IntelliJ IDEA
 * Author : DONGHWAN, KIM
 * DATE : 2020.03.13
 * DESCRIPTION :
 */

@Entity
@Table(name = "ot_log",
        indexes = {
                @Index(name = "idx_ot_log_id", columnList = "id")
        })
@NoArgsConstructor
@Getter
@Setter
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "account_idx", columnDefinition = "int", foreignKey = @ForeignKey(name = "fk_ot_acnt_ot_name"))
    private Account account;

    @Column(nullable = false)
    private String endpoint;

    private String content;

    @CreationTimestamp
    private Timestamp createdAt;

    @Builder
    public Log(Account account, String endpoint, String content, Timestamp createdAt) {
        this.account = account;
        this.endpoint = endpoint;
        this.content = content;
        this.createdAt = createdAt;
    }
}
