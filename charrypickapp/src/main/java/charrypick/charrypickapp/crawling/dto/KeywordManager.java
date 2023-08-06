package charrypick.charrypickapp.crawling.dto;

import charrypick.charrypickapp.crawling.domain.Industry;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class KeywordManager {
	// 직군별 키워드들을 EnumMap으로 저장합니다.
	private static final Map<Industry, List<String>> industryKeywordsMap = new EnumMap<>(Industry.class);

	static {
		industryKeywordsMap.put(Industry.STEEL, Arrays.asList("철강", "포스코", "포항", "탄소중립", "동국제강", "포스코홀딩스",
			"포스코인터내셔널", "수소환원제철", "현대제철", "CBAM", "포항제철소", "KG동부제철"));
		industryKeywordsMap.put(Industry.PETROLEUM_CHEMICAL, Arrays.asList("석유", "화학", "LG화학", "온실가스", "에쓰오일",
			"S-OIL", "롯데케미칼", "폐플라스틱", "현대오일뱅크", "샤힌 프로젝트", "한화토탈에너지스", "부생수소"));
		industryKeywordsMap.put(Industry.OIL_REFINING, Arrays.asList("정유", "주유소", "SK 이노베이션", "현대오일뱅크", "휘발유",
			"정제마진", "SK 에너지", "횡재세", "중앙에너비스", "에쓰오일", "한국석유", "제이씨케미칼"));
		industryKeywordsMap.put(Industry.SECONDARY_BATTERY, Arrays.asList("2차전지", "에코프로", "ETF", "포스코홀딩스",
			"포스코퓨처엠", "IRA", "음극재", "엘앤에프", "에스케이온", "리튬", "강원에너지", "리튬포어스"));
		industryKeywordsMap.put(Industry.SEMICONDUCTOR, Arrays.asList("반도체", "삼성전자", "특화단지", "SK하이닉스",
			"마이크론 테크놀로지", "DB하이텍", "네패스", "후성", "텔레칩스", "원익IPS", "솔브레인", "심텍"));
		industryKeywordsMap.put(Industry.DISPLAY, Arrays.asList("디스플레이", "LED", "OLED", "삼성전자", "LG", "TV",
			"LCD", "유기발광다이오드", "스마트폰", "SID", "현대모비스", "퀀텀닷"));
		industryKeywordsMap.put(Industry.MOBILE, Arrays.asList("휴대폰", "애플", "삼성", "LG이노텍", "덕우전자",
			"비에이치", "아이티엠 반도체", "슈피겐코리아", "이녹스첨단소재", "KH바텍", "파인엠텍", "세경하이테크"));
		industryKeywordsMap.put(Industry.IT, Arrays.asList("AI", "클라우드", "SW", "IT센터", "인공지능",
			"삼성SDI", "LG전자", "코난테크놀로지", "셀바스AI", "알체라", "마인즈랩", "솔트룩스"));
		industryKeywordsMap.put(Industry.CAR, Arrays.asList("전기차", "현대자동차", "자율주행", "내연기관", "테슬라",
			"수소차", "기아", "에스엘", "성우하이텍", "월킵스하이텍", "에코플라스틱", "아진산업"));
		industryKeywordsMap.put(Industry.SHIPBUILDING, Arrays.asList("HD한국조선해양", "HD현대중공업", "삼성중공업",
			"대우조선해양", "현대미포조선", "HJ중공업", "HMM"));
		industryKeywordsMap.put(Industry.SHIPPING, Arrays.asList("LNG", "HMM", "IMO", "HD현대", "TEU",
			"해양수산부", "현대글로비스", "SCFI", "국제해사기구", "폴라리스쉬핑", "흥아해운", "대한해운"));
		industryKeywordsMap.put(Industry.FNB, Arrays.asList("두산로보틱스", "한탑", "대한제당", "동원F&B", "오리온",
			"풀무원", "오뚜기", "CJ제일제당", "롯데칠성", "대상주식회사", "삼양", "농심"));
		industryKeywordsMap.put(Industry.RETAIL_DISTRIBUTION, Arrays.asList("백화점", "RBSI", "BGF리테일",
			"대한상공회의소", "신세계", "이마트", "CJ ENM", "롯데쇼핑", "현대백화점", "GS리테일", "커넥트웨이브", "호텔신라"));
		industryKeywordsMap.put(Industry.CONSTRUCTION, Arrays.asList("현대건설", "GS건설", "HD현대건설기계",
			"HD현대인프라코어", "두산밥캣", "금호건설", "서희건설", "태영건설", "희림", "HDC현대산업개발", "두산에너빌리티", "아세아시멘트"));
		industryKeywordsMap.put(Industry.HOTEL_TRAVEL, Arrays.asList("하나투어", "호텔신라", "파라다이스", "아난티",
			"롯데관광개발", "한진칼", "아시아나항공", "대한항공", "인천공항 면세점", "모두투어", "티웨이항공", "진에어"));
		industryKeywordsMap.put(Industry.FIBER_CLOTHING, Arrays.asList("효성티앤씨", "아웃도어", "한세실업",
			"삼성물산", "F&F 홀딩스", "코웰패션", "휠라홀딩스", "태평양물산", "신세계인터내셔날", "LF", "더네이쳐홀딩스"));
	}

	public static List<String> getKeywordsForIndustry(Industry industry) {
		return industryKeywordsMap.get(industry);
	}
}