package yearnlune.lab.namingcenter.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Project : naming-center
 * Created by IntelliJ IDEA
 * Author : DONGHWAN, KIM
 * DATE : 2021.01.26
 * DESCRIPTION :
 */

@Getter
@AllArgsConstructor
public enum AccountRoleType {
	GUEST("ROLE_GUEST"),
	USER("ROLE_USER"),
	ADMIN("ROLE ADDMIN");

	private final String value;
}
