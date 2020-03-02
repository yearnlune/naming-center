package yearnlune.lab.namingcenter.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yearnlune.lab.namingcenter.database.table.Naming;

/**
 * Project : naming-center
 * Created by IntelliJ IDEA
 * Author : DONGHWAN, KIM
 * DATE : 2020.03.02
 * DESCRIPTION :
 */
public interface NamingRepository extends JpaRepository<Naming, Integer> {
}
