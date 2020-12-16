package yearnlune.lab.namingcenter.database.repository.custom.impl;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import yearnlune.lab.namingcenter.database.repository.custom.AccountCustomRepository;
import yearnlune.lab.namingcenter.database.table.Account;

public class AccountCustomRepositoryImpl extends QuerydslRepositorySupport implements AccountCustomRepository {
	public AccountCustomRepositoryImpl() {
		super(Account.class);
	}
}
