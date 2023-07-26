package charrypick.charrypickapp.crawling.service;

import charrypick.charrypickapp.crawling.domain.Article;
import charrypick.charrypickapp.crawling.domain.PressIndex;
import charrypick.charrypickapp.crawling.repository.ArticleRepository;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;

import charrypick.charrypickapp.crawling.repository.PressIndexRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Slf4j
public class CrawlingService {

	private final ArticleRepository articleRepository;
	private final PressIndexRepository pressIndexRepository;

	private static final String baseURL = "https://n.news.naver.com/mnews/article/";

	public static class NewspaperInfo {
		private String code;
		private String data;

		public NewspaperInfo(String code, String data) {
			this.code = code;
			this.data = data;
		}

		public String getCode() {
			return code;
		}

		public String getData() {
			return data;
		}
	}

	Map<String, NewspaperInfo> newspaperCodes = new HashMap<String, NewspaperInfo>() {{
		//put("경향신문", new NewspaperInfo("032", "3201884"));
		put("경향신문", new NewspaperInfo("032", "3238750"));
		put("국민일보", new NewspaperInfo("005", "1583397"));
		put("동아일보", new NewspaperInfo("020", "3476599"));
		put("서울신문", new NewspaperInfo("081", "3335707"));
		put("세계일보", new NewspaperInfo("022", "3778772"));
		put("조선일보", new NewspaperInfo("032", "3743316"));
		put("중앙일보", new NewspaperInfo("025", "3256539"));
		put("한겨레", new NewspaperInfo("028", "2625266"));
		put("한국일보", new NewspaperInfo("469", "0720810"));
	}};




	@Transactional
	@Scheduled(initialDelay = 10000, fixedDelay = Long.MAX_VALUE)
	//@PostConstruct
	public void getNewsDatas() {

		for (Map.Entry<String, NewspaperInfo> entry : newspaperCodes.entrySet()) {
			String newspaperName = entry.getKey();
			NewspaperInfo newspaperInfo = entry.getValue();

			String startNum = newspaperInfo.getData();
			String newsCode = newspaperInfo.getCode();

			if(!pressIndexRepository.findByPress(newspaperName).isPresent()){
				PressIndex pressIndex = new PressIndex(newspaperName, newspaperInfo.getData());
				pressIndexRepository.save(pressIndex);
			} else {
				PressIndex pressIndex = pressIndexRepository.findByPress(newspaperName).get();
				startNum = pressIndex.getUpdateIndex();
			}

			String mergedKoreanText = "";
			boolean continueLoop = true;
			StringBuilder result = new StringBuilder();


			while (continueLoop) {
				try {

					String currentUrl = baseURL + newsCode + "/000" + startNum;
					//String currentUrl = "https://n.news.naver.com/mnews/article/032/0003238759";
					System.out.println("사이트주소 = "+ currentUrl);
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
					//System.out.println("createTime = " + createTime.ownText());
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







					System.out.println("======================================================");

					Article article = new Article(mergedKoreanText,title.first().ownText(),newspaperName,
							images, 1L);


					articleRepository.save(article);

					int number = Integer.parseInt(startNum);
					number += 1;

					startNum = (newspaperName == "한국일보") ? String.format("%07d", number) : Integer.toString(number);

				} catch (HttpStatusException e) {
					System.out.println("httpSta");
					int number = Integer.parseInt(startNum);
					number += 1;
					startNum = (newspaperName == "한국일보") ? String.format("%07d", number) : Integer.toString(number);
					pressIndexUpdate(newspaperName, startNum);
					System.out.println("왜 트렌젝션 안됨?");
					break;


				} catch (Exception e) {
					// Exception occurred, print the error message and continue the loop
					System.out.println("Error fetching data for article number: " + startNum);
					e.printStackTrace();


				}
			}
			break; //임시
		}
	}

	@Transactional
	public void pressIndexUpdate(String newspaperName, String startNum) {
		System.out.println(startNum);
		PressIndex pressIndex = pressIndexRepository.findByPress(newspaperName).orElseThrow(NullPointerException::new);
		System.out.println("pressIndex.getPress() ffff = " + pressIndex.getPress());
		pressIndex.setUpdateIndex(startNum);
	}
}
