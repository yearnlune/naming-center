package yearnlune.lab.namingcenter.database.table;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;

import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Project : naming-center
 * Created by IntelliJ IDEA
 * Author : DONGHWAN, KIM
 * DATE : 2020.03.13
 * DESCRIPTION :
 */

@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@Embeddable
public class LogId implements Serializable {
    private Long createdAt = System.currentTimeMillis();

    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Builder
    public LogId(Long createdAt, Long id) {
        this.createdAt = createdAt;
        this.id = id;
    }
}
