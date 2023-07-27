package charrypick.charrypickapp.crawling.domain;





import charrypick.charrypickapp.basetest.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
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

	private String title;

	private String publisher;
	private String reporter;
	private String uploadDate;


	@OneToMany(mappedBy = "article", fetch = FetchType.LAZY)
	private List<ArticlePhoto> articlePhoto = new ArrayList<>();

	@Column(nullable = false, columnDefinition = "INTEGER DEFAULT 0")
	private int likeCount = 0;

	public Article(String contents, String title, String publisher, String reporter, String uploadDate, int likeCount) {
		this.contents = contents;
		this.title = title;
		this.publisher = publisher;
		this.reporter = reporter;
		this.uploadDate = uploadDate;
		this.likeCount = likeCount;
	}
}