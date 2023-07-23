//package charrypick.charrypickapp.basetest.dto;
//
//import charrypick.charrypickapp.basetest.domain.TestEntity;
//import io.swagger.annotations.ApiModel;
//import java.time.LocalDateTime;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import org.springframework.data.annotation.LastModifiedDate;
//
//@ApiModel(value = "테스트 request ")
//@AllArgsConstructor
//@NoArgsConstructor
//@Getter
//@Builder
//public class TestRequest {
//
//	private String testProduct;
//
//	private String testPrice;
//
//
//	public static TestRequest toDto(TestEntity testEntity) {
//		TestRequest testRequest = TestRequest.builder()
//			.testProduct(testEntity.getTestProduct())
//			.testPrice(testEntity.getTestPrice())
//			.build();
//		return testRequest;
//	}
//}
