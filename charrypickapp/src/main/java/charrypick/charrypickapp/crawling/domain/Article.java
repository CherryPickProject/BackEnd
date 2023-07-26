package charrypick.charrypickapp.crawling.domain;





import charrypick.charrypickapp.basetest.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity(name = "article")
@Table(name = "article")
public class Article extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "article_id")
	private Long id;

	@Column(columnDefinition="TEXT")
	private String contents;

	@Column(name = "article_name")
	private String articleName;

	private String publisher;

	@ElementCollection
	@Column(name = "article_img_key")
	private List<String> articleImgKey;

	@Column(name = "like_id")
	private Long listId;

	@Builder
	public Article(String contents, String articleName, String publisher, List<String> articleImgKey, Long listId) {
		this.contents = contents;
		this.articleName = articleName;
		this.publisher = publisher;
		this.articleImgKey = articleImgKey;
		this.listId = listId;
	}
}