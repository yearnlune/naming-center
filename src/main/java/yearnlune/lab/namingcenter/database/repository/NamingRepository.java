package yearnlune.lab.namingcenter.database.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import yearnlune.lab.namingcenter.database.repository.custom.NamingCustomRepository;
import yearnlune.lab.namingcenter.database.table.Naming;

/**
 * Project : naming-center
 * Created by IntelliJ IDEA
 * Author : DONGHWAN, KIM
 * DATE : 2020.03.02
 * DESCRIPTION :
 */

@Repository
public interface NamingRepository
	extends JpaRepository<Naming, Integer>, QuerydslPredicateExecutor<Naming>, NamingCustomRepository {
	boolean existsByName(String name);

	List<Naming> findAllByKeywordContaining(String keyword);

	List<Naming> findAllByUpdatedAtGreaterThanEqual(Timestamp updatedAt);
}
