package yearnlune.lab.namingcenter.database.table;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Project : naming-center
 * Created by IntelliJ IDEA
 * Author : DONGHWAN, KIM
 * DATE : 2020.03.01
 * DESCRIPTION :
 */

@Entity
@Table(name = "ot_name",
        indexes = {
                @Index(name = "uk_ot_name_name", columnList = "name", unique = true)
        })
@NoArgsConstructor
@Getter
@Setter
public class Naming {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idx;

    @Column(length = 128, nullable = false)
    private String name;

    @Column
    private String description;

    @Column(nullable = false)
    @CreationTimestamp
    private Timestamp createdAt;

    @Column(nullable = false)
    @UpdateTimestamp
    private Timestamp updatedAt;

    @Column(length = 256)
    private String keyword;

    @ManyToOne
    @JoinColumn(name = "account_idx", columnDefinition = "int", foreignKey = @ForeignKey(name = "fk_ot_acnt_ot_name"), nullable = false)
    private Account account;

    @Builder
    public Naming(String name, String description, Timestamp createdAt, Timestamp updatedAt, Account account) {
        this.name = name;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.keyword = (name + "|" + description).toLowerCase();
        this.account = account;
    }
}
