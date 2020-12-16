package yearnlune.lab.namingcenter.constant;

import lombok.Getter;

/**
 * Project : naming-center
 * Created by IntelliJ IDEA
 * Author : DONGHWAN, KIM
 * DATE : 2020.03.20
 * DESCRIPTION :
 */

@Getter
public enum AccountRoleEnum {
	GUEST("ROLE_GUEST"),
	USER("ROLE_USER"),
	ADMIN("ROLE_ADMIN");

	AccountRoleEnum(String value) {
		this.value = value;
	}

	String value;
}
