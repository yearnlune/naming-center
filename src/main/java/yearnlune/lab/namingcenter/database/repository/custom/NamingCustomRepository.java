package yearnlune.lab.namingcenter.database.repository.custom;

import java.util.List;

/**
 * Project : naming-center
 * Created by IntelliJ IDEA
 * Author : DONGHWAN, KIM
 * DATE : 2020.03.10
 * DESCRIPTION :
 */

public interface NamingCustomRepository {
	List<String> findNames();
}
