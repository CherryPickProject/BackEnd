package charrypick.charrypickapp.basetest.controller;


import charrypick.charrypickapp.basetest.dto.Response;
import charrypick.charrypickapp.basetest.dto.TestRequest;
import charrypick.charrypickapp.basetest.service.TestService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "테스트")
@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
public class TestController {

	private final TestService testService;

	private final Response response;


	@PostMapping
	public ResponseEntity<?> addTest(@RequestBody TestRequest testRequest) {
		return response.success(testService.save(testRequest),"success",HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<?> getAllTest() {
		return response.success(testService.getTestAll(),"success",HttpStatus.OK);
	}

	@DeleteMapping
	public ResponseEntity<?> deleteAll() {
		return response.success(testService.deleteAllTest(), "success", HttpStatus.OK);
	}

}
