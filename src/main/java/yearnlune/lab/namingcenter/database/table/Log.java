package yearnlune.lab.namingcenter.database.table;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Project : naming-center
 * Created by IntelliJ IDEA
 * Author : DONGHWAN, KIM
 * DATE : 2020.03.13
 * DESCRIPTION :
 */

@Entity
@Table(name = "ot_log",
	indexes = {
		@Index(name = "idx_ot_log_id", columnList = "id")
	})
@NoArgsConstructor
@Getter
@Setter
public class Log {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "account_idx", columnDefinition = "int", foreignKey = @ForeignKey(name = "fk_ot_acnt_ot_name"))
	private Account account;

	@Column(nullable = false)
	private String endpoint;

	private String content;

	@CreationTimestamp
	private Timestamp createdAt;

	@Builder
	public Log(Account account, String endpoint, String content, Timestamp createdAt) {
		this.account = account;
		this.endpoint = endpoint;
		this.content = content;
		this.createdAt = createdAt;
	}
}
