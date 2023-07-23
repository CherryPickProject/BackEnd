package charrypick.charrypickapp.crawling.service;

import charrypick.charrypickapp.crawling.domain.Article;
import charrypick.charrypickapp.crawling.repository.ArticleRepository;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CrawlingService {

	private final ArticleRepository articleRepository;

	private static final String baseURL = "https://n.news.naver.com/mnews/article/";
	private static final String 부산일보 = "082";
	private static final String url = baseURL + 부산일보 + "/0001221194";



	@PostConstruct
	public void getNewsDatas() {
		String mergedKoreanText = "";
		int articleNumber = 1221194; // Initial article number
		boolean continueLoop = true;
		StringBuilder result = new StringBuilder();
		while (continueLoop) {
			try {

				String currentUrl = baseURL + 부산일보 + "/000" + articleNumber;
				Document document = Jsoup.connect(currentUrl).get();

				//Document document = Jsoup.connect(url).get();

				//Elements contents = document.select("#dic_area");
				Element dicArea = document.select("#dic_area").first();
				if (dicArea != null) {
					StringBuilder koreanTextBuilder = new StringBuilder(dicArea.ownText());

					Elements spanElements = dicArea.select("span[data-type=ore]");
					for (Element spanElement : spanElements) {
						String spanKoreanText = spanElement.ownText();
						koreanTextBuilder.append(spanKoreanText);
					}

					mergedKoreanText = koreanTextBuilder.toString();
					System.out.println("mergedKoreanText = " + mergedKoreanText);
				}
				Elements title = document.select("#title_area span");
				Elements create = document.select(".media_end_head_journalist_name");
				Element createTime = document.select("._ARTICLE_DATE_TIME").first();

				int i = 1;
				List<String> images = new ArrayList<>();
				while (true) {
					String imgId = "img" + i;
					Element htmlImage = document.select("#" + imgId).first();
					if (htmlImage != null) {
						images.add(htmlImage.attr("data-src"));
					} else {
						break; // 루프 중지
					}
					i++;
				}
				System.out.println("createTime = " + createTime.ownText());
				System.out.println("title = " + title.first().ownText());
				for (Element element : create) {
					System.out.println("element = " + element.ownText());
				}
				for (String image : images) {
					System.out.println("image = " + image);
				}
				for (String image : images) {
					result.append(image).append(", ");
				}
				if (result.length() > 0) {
					result.setLength(result.length() - 2);
				}
				articleNumber++;

				if (articleNumber == 1221200) {
					break;
				}



				System.out.println("======================================================");

				Article article = new Article(mergedKoreanText,title.first().ownText(),"부산일보",
					images.get(0), 1L);


//				articleRepository.save(article);

			} catch (Exception e) {
				// Exception occurred, print the error message and continue the loop
				System.out.println("Error fetching data for article number: " + articleNumber);
				e.printStackTrace();
				if (articleNumber == 1221200) {
					break;
				}

				articleNumber++;

			}

		}

	}
}
