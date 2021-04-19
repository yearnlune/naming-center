package yearnlune.lab.namingcenter.database.repository.custom;

import java.util.Optional;

import yearnlune.lab.namingcenter.database.table.Account;

public interface AccountCustomRepository {
	Optional<Account> findById(String id);
}
