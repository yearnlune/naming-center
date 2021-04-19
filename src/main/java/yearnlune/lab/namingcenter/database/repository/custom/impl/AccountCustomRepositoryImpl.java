package yearnlune.lab.namingcenter.database.repository.custom.impl;

import java.util.Optional;

import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import yearnlune.lab.namingcenter.database.repository.custom.AccountCustomRepository;
import yearnlune.lab.namingcenter.database.table.Account;
import yearnlune.lab.namingcenter.database.table.QAccount;

public class AccountCustomRepositoryImpl extends QuerydslRepositorySupport implements AccountCustomRepository {
	public AccountCustomRepositoryImpl() {
		super(Account.class);
	}

	@Override
	public Optional<Account> findById(String id) {
		Session session = getEntityManager().unwrap(Session.class);
		Filter filter = session.enableFilter("activatedAccount");

		QAccount qAccount = QAccount.account;

		return Optional.ofNullable(from(qAccount)
			.select(qAccount)
			.where(qAccount.id.eq(id))
			.fetchOne());
	}

}
