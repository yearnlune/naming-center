package yearnlune.lab.namingcenter.jpa;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import yearnlune.lab.namingcenter.database.repository.AccountRepository;

/**
 * Project : naming-center
 * Created by IntelliJ IDEA
 * Author : DONGHWAN, KIM
 * DATE : 2021.01.22
 * DESCRIPTION :
 */

@RunWith(SpringRunner.class)
@DataJpaTest
public abstract class JpaTestSupport {
	@Before
	public void setUp() {
	}

	@AfterClass
	public static void tearDownAfterClass() {
	}
}
