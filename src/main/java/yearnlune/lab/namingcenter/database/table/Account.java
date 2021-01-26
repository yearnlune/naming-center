
package yearnlune.lab.namingcenter.database.table;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import yearnlune.lab.namingcenter.type.AccountRoleType;

@Entity
@Table(name = "ot_acnt")
// @Check(constraints = "'role' IN ('ROLE_GUEST', 'ROLE_USER', 'ROLE_ADMIN')")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Account {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idx;

	@Column(length = 64, nullable = false, unique = true)
	private String id;

	@Column(length = 128, nullable = false)
	private String name;

	@Column(length = 128, nullable = false)
	private String password;

	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = "varchar(64) DEFAULT 'ROLE_GUEST'", nullable = false)
	private AccountRoleType role;

	@Column(nullable = false)
	@CreationTimestamp
	private Timestamp createdAt;

	public Account passwordEncode(PasswordEncoder passwordEncoder) {
		this.password = passwordEncoder.encode(password);
		return this;
	}

	@Override
	public String toString() {
		return "Account{" +
			"idx=" + idx +
			", id='" + id + '\'' +
			", name='" + name + '\'' +
			", password='" + password + '\'' +
			", role=" + role.getValue() +
			", createdAt=" + createdAt +
			'}';
	}
}
