package charrypick.charrypickapp.crawling.service;

import charrypick.charrypickapp.crawling.domain.Article;
import charrypick.charrypickapp.crawling.domain.ArticlePhoto;
import charrypick.charrypickapp.crawling.domain.Industry;
import charrypick.charrypickapp.crawling.domain.PressIndex;
import charrypick.charrypickapp.crawling.dto.KeywordManager;
import charrypick.charrypickapp.crawling.repository.ArticlePhotoRepository;
import charrypick.charrypickapp.crawling.repository.ArticleRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Random;
import java.security.SecureRandom;
import java.util.*;
import javax.annotation.PostConstruct;

import charrypick.charrypickapp.crawling.repository.PressIndexRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.asm.Advice.Local;
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
	private final ArticlePhotoRepository articlePhotoRepository;

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

	//진짜 데이터
//	Map<String, NewspaperInfo> newspaperCodes = new HashMap<String, NewspaperInfo>() {{
//		put("경향신문", new NewspaperInfo("032", "3201884"));
//		put("국민일보", new NewspaperInfo("005", "1583397"));
//		put("동아일보", new NewspaperInfo("020", "3476599"));
//		put("서울신문", new NewspaperInfo("081", "3335707"));
//		put("세계일보", new NewspaperInfo("022", "3778772"));
//		put("조선일보", new NewspaperInfo("032", "3743316"));
//		put("중앙일보", new NewspaperInfo("025", "3256539"));
//		put("한겨레", new NewspaperInfo("028", "2625266"));
//		put("한국일보", new NewspaperInfo("469", "0720810"));
//	}};

	Map<String, NewspaperInfo> newspaperCodes = new HashMap<String, NewspaperInfo>() {{
		put("경향신문", new NewspaperInfo("032", "3238750"));
		put("국민일보", new NewspaperInfo("005", "1626553"));
		put("동아일보", new NewspaperInfo("020", "3511666"));
		put("서울신문", new NewspaperInfo("081", "3380320"));
		put("세계일보", new NewspaperInfo("022", "3838522"));
		put("조선일보", new NewspaperInfo("032", "3778238"));
		put("중앙일보", new NewspaperInfo("025", "3296802"));
		put("한겨레", new NewspaperInfo("028", "2649855"));
		put("한국일보", new NewspaperInfo("469", "0751943"));
	}};





	//@Transactional
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

			int exceptionCount = 0;

			while (continueLoop) {
				try {

					String currentUrl = baseURL + newsCode + "/000" + startNum;
					//String currentUrl = "https://n.news.naver.com/mnews/article/032/0003202217";
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
					StringJoiner joiner = new StringJoiner(",");
					//System.out.println("createTime = " + createTime.ownText());
					System.out.println("title = " + title.first().ownText());
					for (Element element : create) {
						System.out.println("element = " + element.ownText());
						joiner.add(element.ownText());
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

					//Industry randomIndustry = getRandomIndustry();

					//------------------------------
					Map<Industry, Integer> countMap = new HashMap<>();

					// 모든 Industry enum에 대해서 반복하면서 단어 개수를 세어 countMap에 저장합니다.
					for (Industry industry : Industry.values()) {
						List<String> keywords = KeywordManager.getKeywordsForIndustry(industry);
						int count = countKeywords(mergedKoreanText, keywords);
						countMap.put(industry, count);
					}

					// countMap에서 가장 큰 값을 가지는 enum을 찾습니다.
					Industry largestIndustry = findLargestIndustry(countMap);

					System.out.println("가장 큰 값을 가지는 직군: " + largestIndustry);
					System.out.println("키워드 개수: " + countMap.get(largestIndustry));
					if (countMap.get(largestIndustry) < 4) {
						throw new KeywordCountException("너무 적은 키워드 개수입니다.");
					}



					//-------------------------------------

					LocalDateTime dateTime = convertStringToLocalDateTime(createTime.text());
					Article article = saveArticle(newspaperName, mergedKoreanText, title, dateTime, joiner,largestIndustry);
					saveArticlePhoto(images, article);

					int number = Integer.parseInt(startNum);
					number += 1;
					startNum = (newspaperName == "한국일보") ? String.format("%07d", number) : Integer.toString(number);

				} catch (HttpStatusException e) {
					System.out.println("httpSta");
					int number = Integer.parseInt(startNum);
					number += 1;
					startNum = (newspaperName == "한국일보") ? String.format("%07d", number) : Integer.toString(number);

					exceptionCount ++;
					System.out.println("exceptionCount = " + exceptionCount);
					if (exceptionCount == 10) {
						number -= 10;
						startNum = (newspaperName == "한국일보") ? String.format("%07d", number) : Integer.toString(number);
						pressIndexUpdate(newspaperName, startNum);
						continueLoop = false;
					}


				} catch (Exception e) {
					// Exception occurred, print the error message and continue the loop
					System.out.println("Error fetching data for article number: " + startNum);
					int number = Integer.parseInt(startNum);
					number += 1;
					startNum = (newspaperName == "한국일보") ? String.format("%07d", number) : Integer.toString(number);
					e.printStackTrace();


				}
			}
			//break; //임시
		}
	}

	@Transactional
	public void saveArticlePhoto(List<String> images, Article article) {
		for (String image : images) {
			ArticlePhoto articlePhoto = new ArticlePhoto(article, image);
			articlePhotoRepository.save(articlePhoto);
		}
	}

	@Transactional
	public Article saveArticle(String newspaperName, String mergedKoreanText, Elements title, LocalDateTime createTime, StringJoiner joiner, Industry randomIndustry) {
		Article article = new Article(mergedKoreanText, title.first().ownText(), newspaperName, joiner.toString(), createTime,0, randomIndustry);
		articleRepository.save(article);
		return article;
	}

	@Transactional
	public void pressIndexUpdate(String newspaperName, String startNum) {
		System.out.println(startNum);
		PressIndex pressIndex = pressIndexRepository.findByPress(newspaperName).orElseThrow(NullPointerException::new);
		System.out.println("pressIndex.getPress() ffff = " + pressIndex.getPress());
		pressIndex.setUpdateIndex(startNum);
	}

	public static Industry getRandomIndustry() {
		Random random = new Random();
		Industry[] industries = Industry.values();
		int randomIndex = random.nextInt(industries.length);
		return industries[randomIndex];
	}


	private static Industry findLargestIndustry(Map<Industry, Integer> countMap) {
		Industry largestIndustry = null;
		int largestCount = -1;

		for (Map.Entry<Industry, Integer> entry : countMap.entrySet()) {
			Industry industry = entry.getKey();
			int count = entry.getValue();

			if (count > largestCount) {
				largestIndustry = industry;
				largestCount = count;
			}
		}

		return largestIndustry;
	}

	private static int countKeywords(String text, List<String> keywords) {
		int count = 0;
		for (String keyword : keywords) {
			// 단어 개수를 세는 로직 (대소문자 구분 없이 검색)
			int index = 0;
			while (index != -1) {
				index = text.indexOf(keyword, index);
				if (index != -1) {
					count++;
					index += keyword.length();
				}
			}
		}
		return count;
	}

	static class KeywordCountException extends Exception {
		public KeywordCountException(String message) {
			super(message);
		}
	}

	public static LocalDateTime convertStringToLocalDateTime(String dateString) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd. a h:mm");
		try {
			return LocalDateTime.parse(dateString, formatter);
		} catch (DateTimeParseException e) {
			e.printStackTrace();
			// 또는 다른 예외 처리 방법을 선택할 수 있습니다.
			return null;
		}
	}


}
