package crawlingc;

import java.io.IOException;
import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import crawling.model.CodingDAO;
import crawling.model.CodingVO;

public class CrawlingTest {
	public static void main(String[] args) {
		int num = 0; // url 상세 페이지 이동 변수

		// 크롤링 할 페이지 6개
		// https://dailypoint.tistory.com/43
		// https://dailypoint.tistory.com/44
		// https://dailypoint.tistory.com/45
		// https://dailypoint.tistory.com/46
		// https://dailypoint.tistory.com/47
		// https://dailypoint.tistory.com/48

		Document doc = null;

		// 크롤링 데이터 담아서 DB로 저장할 변수들
		String title = null; // 제목
		// ArrayList<String> content = new ArrayList<String>(); //내용 == 코드
		String content = null; // 내용 == 코드
		String answer = null; // 답(단답)
		String ex = null; // 입출력 예시
		String writer = null; // 작성자



		for (int i = 3; i < 9; i++) { // ★ url 뒷번호 대체할 숫자 for문으로 돌림 --> jsoup 사용으로 필요
			try {

				num = i;
				String url = "https://dailypoint.tistory.com/4" + num; // 크롤링할 url

				System.out.println("-------------------");
				System.out.println("url확인 : " + url);
				doc = Jsoup.connect(url).get();

			} catch (IOException e) {
				e.printStackTrace();
			}

			// 1. title
			Iterator<Element> iter1 = doc.select("h2.title").iterator();

			// 2. content -- 문제내용
			Iterator<Element> iter2 = doc.select("code").iterator(); // 문제

			// 3. EX -- 예제입력 + 예제 출력
			Iterator<Element> iter3 = doc.select("p.ex").iterator(); // 예제입력

			// 4. answer -- 답(코드)
			Iterator<Element> iter4 = doc.select("p.answer").iterator(); // 답(코드)

			// 5. writer -- 출처
			Iterator<Element> iter5 = doc.select("p.writer").iterator();

			while (iter1.hasNext()) {

				// 1. title
				String str = iter1.next().toString();
				str = str.substring(0, str.lastIndexOf("<"));
				str = str.substring(str.lastIndexOf(">") + 1);
				title = str;

				// 2. content -- 문제

				str = iter2.next().toString();
				str = str.substring(0, str.lastIndexOf("<"));
				str = str.substring(str.lastIndexOf(">") + 1);

				// content.add(str);

				content = str;

				// 3. EX -- 예제
				str = iter3.next().toString();
				str = str.substring(0, str.lastIndexOf("<"));

				str = str.substring(str.lastIndexOf(">") + 1);

				ex = str;

				// 4. answer -- 답(코드)
				str = iter4.next().toString();
				str = str.substring(0, str.lastIndexOf("<"));

				str = str.substring(str.lastIndexOf(">") + 1);

				answer = str;

				// 5. writer -- 출처

				str = iter5.next().toString();
				str = str.substring(0, str.lastIndexOf("<"));

				str = str.substring(str.lastIndexOf(">") + 1);

				writer = str;

				System.out.println("\n===========확인용===========\n");

				System.out.println("title :" + title);
				System.out.println("content :" + content);
				System.out.println("ex :" + ex);
				System.out.println("answer :" + answer);
				System.out.println("writer :" + writer);

				System.out.println("\n=========================\n");

			}
			
			CodingVO vo = new CodingVO();
			CodingDAO dao = new CodingDAO();

			// insert
			vo.settTitle(title);
			vo.settContent(content);
			vo.settEx(ex);
			vo.settAnswer(answer);
			vo.settWriter(writer);
			dao.insert(vo);
			System.out.println(i + "번째 블로그 글  DB삽입완료");
			
			
			//1초 대기
			try {Thread.sleep(1000);} catch (InterruptedException e) {}
			

		}


	}
}
