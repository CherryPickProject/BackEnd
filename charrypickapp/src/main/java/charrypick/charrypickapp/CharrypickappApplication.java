package charrypick.charrypickapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableJpaAuditing // BaseEntity를 통하여 각 도메인들이 처음으로 생성된 날짜와 수정된 날짜를 파악하도록 사용
@SpringBootApplication(exclude = SecurityAutoConfiguration.class) //시큐리티 기본로그인 제거
public class CharrypickappApplication {

	public static void main(String[] args) {
		SpringApplication.run(CharrypickappApplication.class, args);
	}

}
