package yearnlune.lab.namingcenter.type;

import lombok.AllArgsConstructor;
import yearnlune.lab.namingcenter.type.mapper.EnumMapperType;

/**
 * Project : naming-center
 * Created by IntelliJ IDEA
 * Author : DONGHWAN, KIM
 * DATE : 2021.01.26
 * DESCRIPTION :
 */

@AllArgsConstructor
public enum AccountRoleType implements EnumMapperType {
	ROLE_GUEST("GUEST"),
	ROLE_USER("USER"),
	ROLE_ADMIN("ADMIN");

	private final String value;

	@Override
	public String getCode() {
		return name();
	}

	@Override
	public String getValue() {
		return value;
	}

	public static AccountRoleType fromValue(String value) {
		for (AccountRoleType entry : values()) {
			if (entry.getValue().equals(value)) {
				return entry;
			}
		}
		throw new IllegalArgumentException("Unknown AccountRoleType=" + value);
	}
}
