
package yearnlune.lab.namingcenter.database.table;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import yearnlune.lab.namingcenter.type.AccountRoleType;

@Entity
@Table(name = "ot_acnt")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@FilterDef(name = "activatedAccount")
@Filter(name = "activatedAccount", condition = "is_deleted = false")
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

	@Builder.Default
	@Enumerated(EnumType.STRING)
	@ColumnDefault("ROLE_GUEST")
	@Column(length = 64, nullable = false)
	private AccountRoleType role = AccountRoleType.ROLE_GUEST;

	@Column(nullable = false)
	@CreationTimestamp
	private Timestamp createdAt;

	@Builder.Default
	@ColumnDefault("false")
	@Column(nullable = false)
	private Boolean isDeleted = false;

	@OneToMany(mappedBy = "account")
	@Fetch(FetchMode.SUBSELECT)
	private List<Log> logList;

	public Account passwordEncode(PasswordEncoder passwordEncoder) {
		this.password = passwordEncoder.encode(password);
		return this;
	}
}
