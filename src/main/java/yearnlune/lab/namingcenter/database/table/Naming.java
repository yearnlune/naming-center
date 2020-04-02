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

    @Builder
    public Naming(String name, String description) {
        this.name = name;
        this.description = description;
        this.keyword = (name + "|" + description).toLowerCase();
    }
}
