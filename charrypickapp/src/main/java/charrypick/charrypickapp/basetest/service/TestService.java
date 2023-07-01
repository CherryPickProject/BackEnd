package charrypick.charrypickapp.basetest.service;

import charrypick.charrypickapp.basetest.domain.TestEntity;
import charrypick.charrypickapp.basetest.dto.TestRequest;
import charrypick.charrypickapp.basetest.dto.TestResponse;
import charrypick.charrypickapp.basetest.repository.TestRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TestService {

	private final TestRepository testRepository;

	@Transactional
	public TestResponse save(TestRequest testRequest) {
		TestEntity testEntity = new TestEntity(testRequest.getTestProduct(),
			testRequest.getTestPrice());
		testRepository.save(testEntity);
		return TestResponse.toDto(testEntity);
	}

	@Transactional
	public List<TestResponse> getTestAll() {
		List<TestResponse> testResponseList = new ArrayList<>();
		List<TestEntity> testEntityList = testRepository.findAll();
		for (TestEntity testEntity : testEntityList) {
			testResponseList.add(TestResponse.toDto(testEntity));
		}
		return testResponseList;
	}

	@Transactional
	public String deleteAllTest() {
		testRepository.deleteAll();
		return "success deleteAllTest";
	}

}
