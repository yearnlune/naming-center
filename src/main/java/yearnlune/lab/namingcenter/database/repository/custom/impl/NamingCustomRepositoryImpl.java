package yearnlune.lab.namingcenter.database.repository.custom.impl;

import java.util.List;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import yearnlune.lab.namingcenter.database.repository.custom.NamingCustomRepository;
import yearnlune.lab.namingcenter.database.table.Naming;
import yearnlune.lab.namingcenter.database.table.QNaming;

/**
 * Project : naming-center
 * Created by IntelliJ IDEA
 * Author : DONGHWAN, KIM
 * DATE : 2020.03.10
 * DESCRIPTION :
 */

public class NamingCustomRepositoryImpl extends QuerydslRepositorySupport implements NamingCustomRepository {

	public NamingCustomRepositoryImpl() {
		super(Naming.class);
	}

	@Override
	public List<String> findNames() {
		QNaming naming = QNaming.naming;
		return from(naming)
			.select(naming.name)
			.fetch();
	}
}
