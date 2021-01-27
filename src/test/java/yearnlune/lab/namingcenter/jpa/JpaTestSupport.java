package yearnlune.lab.namingcenter.jpa;

import javax.persistence.EntityManager;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.querydsl.jpa.impl.JPAQueryFactory;

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
	@Autowired
	EntityManager entityManager;

	JPAQueryFactory jpaQueryFactory;

	@Before
	public void setUp() {
		jpaQueryFactory = new JPAQueryFactory(entityManager);
	}

	@AfterClass
	public static void tearDownAfterClass() {
	}
}
