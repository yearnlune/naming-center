package yearnlune.lab.namingcenter.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import yearnlune.lab.namingcenter.database.table.Log;

/**
 * Project : naming-center
 * Created by IntelliJ IDEA
 * Author : DONGHWAN, KIM
 * DATE : 2020.03.19
 * DESCRIPTION :
 */
public interface LogRepository extends JpaRepository<Log, Integer> {
}
