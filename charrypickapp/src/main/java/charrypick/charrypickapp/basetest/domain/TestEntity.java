//package charrypick.charrypickapp.basetest.domain;
//
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import lombok.AccessLevel;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import org.hibernate.annotations.DynamicUpdate;
//
//@Entity
//@AllArgsConstructor
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@DynamicUpdate
//@Getter
//@Builder
//@Setter
//public class TestEntity extends BaseEntity {
//
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@Column(name = "test_id")
//	private Long id; // PK로 활용하기 위한 id값
//
//	private String testProduct;
//
//	private String testPrice;
//
//	public TestEntity(String testProduct, String testPrice) {
//		this.testProduct = testProduct;
//		this.testPrice = testPrice;
//	}
//}
