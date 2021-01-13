package yearnlune.lab.namingcenter.database.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Project : naming-center
 * Created by IntelliJ IDEA
 * Author : DONGHWAN, KIM
 * DATE : 2020.03.03
 * DESCRIPTION :
 */

public class NamingDTO {

	@Data
	@NoArgsConstructor
	public static class CommonResponse {
		private Integer idx;

		private String name;

		private String description;

		@Builder
		public CommonResponse(Integer idx, String name, String description) {
			this.idx = idx;
			this.name = name;
			this.description = description;
		}
	}

	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class RegisterRequest {
		private String name;

		private String description;

		private AccountDTO.CommonResponse account;
	}

	@Data
	@NoArgsConstructor
	public static class AutoCompleteResponse {
		private List<String> namingList;

		@Builder
		public AutoCompleteResponse(List<String> namingList) {
			this.namingList = namingList;
		}
	}
}
