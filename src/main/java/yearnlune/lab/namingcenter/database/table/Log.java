package yearnlune.lab.namingcenter.database.table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

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
    @EmbeddedId
    private LogId id;

    @ManyToOne
    @JoinColumn(name = "account_idx", columnDefinition = "int", foreignKey = @ForeignKey(name = "fk_ot_acnt_ot_name"), nullable = false)
    private Account account;

    @Column(nullable = false)
    private String endpoint;

    private String content;

    public Log(LogId id, Account account, String endpoint, String content) {
        this.id = id;
        this.account = account;
        this.endpoint = endpoint;
        this.content = content;
    }
}
