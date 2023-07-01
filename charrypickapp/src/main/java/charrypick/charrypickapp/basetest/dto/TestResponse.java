package charrypick.charrypickapp.basetest.dto;

import charrypick.charrypickapp.basetest.domain.TestEntity;
import io.swagger.annotations.ApiModel;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@ApiModel(value = "테스트 Response ")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class TestResponse {

	private String testProduct;

	private String testPrice;

	private LocalDateTime createdDate;

	private LocalDateTime lastModified;

	public static TestResponse toDto(TestEntity testEntity) {
		TestResponse testResponse = TestResponse.builder()
			.testProduct(testEntity.getTestProduct())
			.testPrice(testEntity.getTestPrice())
			.createdDate(testEntity.getCreatedDate())
			.lastModified(testEntity.getLastModified())
			.build();
		return testResponse;
	}
}
