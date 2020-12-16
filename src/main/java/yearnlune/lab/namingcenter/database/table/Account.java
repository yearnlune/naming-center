
package yearnlune.lab.namingcenter.database.table;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Check;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ot_acnt")
@Check(constraints = "role IN ('ROLE_GUEST', 'ROLE_USER', 'ROLE_ADMIN')")
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

	@Column(columnDefinition = "varchar(64) DEFAULT 'ROLE_GUEST'", nullable = false)
	private String role = "ROLE_GUEST";

	@Column(nullable = false)
	@CreationTimestamp
	private Timestamp createdAt;

	public Account passwordEncode(PasswordEncoder passwordEncoder) {
		this.password = passwordEncoder.encode(password);
		return this;
	}
}
