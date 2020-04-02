package yearnlune.lab.namingcenter.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yearnlune.lab.namingcenter.database.table.Naming;

import java.sql.Timestamp;
import java.util.List;

/**
 * Project : naming-center
 * Created by IntelliJ IDEA
 * Author : DONGHWAN, KIM
 * DATE : 2020.03.02
 * DESCRIPTION :
 */

@Repository
public interface NamingRepository extends JpaRepository<Naming, Integer> {
    boolean existsByName(String name);

    List<Naming> findAllByKeywordContaining(String keyword);

    List<Naming> findAllByUpdatedAtGreaterThanEqual(Timestamp updatedAt);
}
