package charrypick.charrypickapp.crawling.domain;





import charrypick.charrypickapp.basetest.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Article extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "article_id")
	private Long id;

	@Column(columnDefinition="TEXT")
	private String contents;

	private String articleName;
	private String publisher;
	private String articleImgKey;

	@Builder
	public Article(String contents, String articleName, String publisher, String articleImgKey) {
		this.contents = contents;
		this.articleName = articleName;
		this.publisher = publisher;
		this.articleImgKey = articleImgKey;
	}
}