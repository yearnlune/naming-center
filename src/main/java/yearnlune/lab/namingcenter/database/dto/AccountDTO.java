package yearnlune.lab.namingcenter.database.dto;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class AccountDTO {

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	@Builder
	public static class CommonResponse {
		private Integer idx;

		private String id;

		private String name;

		private String role;

		private String jwt;
	}

	@Data
	@NoArgsConstructor
	public static class RegisterRequest {
		@NotEmpty
		private String id;

		@NotEmpty
		private String name;

		@NotEmpty
		private String password;

		@Builder
		public RegisterRequest(String id, String name, String password) {
			this.id = id;
			this.name = name;
			this.password = password;
		}
	}

	@Getter
	@Setter
	@NoArgsConstructor
	@Builder
	public static class PatchedRequest {
		private String name;

		private String password;

		public PatchedRequest(String name, String password) {
			this.name = name;
			this.password = password;
		}
	}

	@Data
	@NoArgsConstructor
	public static class LoginRequest {
		@NotEmpty
		private String id;

		@NotEmpty
		private String password;

		@Builder
		public LoginRequest(String id, String password) {
			this.id = id;
			this.password = password;
		}
	}

	@Data
	@NoArgsConstructor
	public static class TokenValidationRequest {
		private String jwt;

		@Builder
		public TokenValidationRequest(String jwt) {
			this.jwt = jwt;
		}
	}
}
