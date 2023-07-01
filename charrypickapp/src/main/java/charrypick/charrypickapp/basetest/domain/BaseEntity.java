package charrypick.charrypickapp.basetest.domain;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Getter
public class BaseEntity {

	@CreatedDate
	@Column(updatable = false) // Entity가 생성된 시간을 수정하면 안되기 때문에 @Column 어노테이션을 통하여 수정 차단
	private LocalDateTime createdDate; // Entity가 처음으로 생성된 시간

	@LastModifiedDate
	private LocalDateTime lastModified; // Entity가 마지막으로 수정된 시간
}