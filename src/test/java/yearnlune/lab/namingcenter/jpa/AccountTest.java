package yearnlune.lab.namingcenter.jpa;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import yearnlune.lab.namingcenter.database.repository.AccountRepository;
import yearnlune.lab.namingcenter.database.table.Account;
import yearnlune.lab.namingcenter.type.AccountRoleType;

/**
 * Project : naming-center
 * Created by IntelliJ IDEA
 * Author : DONGHWAN, KIM
 * DATE : 2021.01.22
 * DESCRIPTION :
 */

public class AccountTest extends JpaTestSupport {

	@Autowired
	AccountRepository accountRepository;

	@Override
	public void setUp() {
		super.setUp();
		Account accountMock = Account.builder()
			.id("mock")
			.name("nameMock")
			.password("mock")
			.role(AccountRoleType.ROLE_GUEST)
			.build();

		accountRepository.save(accountMock);
	}

	@Test
	public void existsById_existentAccountId_shouldBeSuccess() {
		/* GIVEN */
		String id = "mock";

		/* WHEN */
		Boolean hasAccount = accountRepository.existsById(id);

		/* THEN */
		assertThat(hasAccount, is(true));
	}

	@Test
	public void findById_existentAccountId_shouldBeSuccess() {
		/* GIVEN */
		String id = "mock";

		/* WHEN */
		Optional<Account> accountOptional = accountRepository.findById(id);
		Account account = accountOptional.orElse(null);

		/* THEN */
		assertThat(account, notNullValue());
		assertThat(account.getId(), is(id));
	}
}

