package yearnlune.lab.namingcenter.database.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import yearnlune.lab.namingcenter.database.repository.custom.AccountCustomRepository;
import yearnlune.lab.namingcenter.database.table.Account;

@Repository
public interface AccountRepository extends CrudRepository<Account, Integer>, AccountCustomRepository {
    boolean existsById(String id);
}
