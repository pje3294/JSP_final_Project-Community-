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
		int num = 0; // url �� ������ �̵� ����

		// ũ�Ѹ� �� ������ 6��
		// https://dailypoint.tistory.com/43
		// https://dailypoint.tistory.com/44
		// https://dailypoint.tistory.com/45
		// https://dailypoint.tistory.com/46
		// https://dailypoint.tistory.com/47
		// https://dailypoint.tistory.com/48

		Document doc = null;

		// ũ�Ѹ� ������ ��Ƽ� DB�� ������ ������
		String title = null; // ����
		// ArrayList<String> content = new ArrayList<String>(); //���� == �ڵ�
		String content = null; // ���� == �ڵ�
		String answer = null; // ��(�ܴ�)
		String ex = null; // ����� ����
		String writer = null; // �ۼ���



		for (int i = 3; i < 9; i++) { // �� url �޹�ȣ ��ü�� ���� for������ ���� --> jsoup ������� �ʿ�
			try {

				num = i;
				String url = "https://dailypoint.tistory.com/4" + num; // ũ�Ѹ��� url

				System.out.println("-------------------");
				System.out.println("urlȮ�� : " + url);
				doc = Jsoup.connect(url).get();

			} catch (IOException e) {
				e.printStackTrace();
			}

			// 1. title
			Iterator<Element> iter1 = doc.select("h2.title").iterator();

			// 2. content -- ��������
			Iterator<Element> iter2 = doc.select("code").iterator(); // ����

			// 3. EX -- �����Է� + ���� ���
			Iterator<Element> iter3 = doc.select("p.ex").iterator(); // �����Է�

			// 4. answer -- ��(�ڵ�)
			Iterator<Element> iter4 = doc.select("p.answer").iterator(); // ��(�ڵ�)

			// 5. writer -- ��ó
			Iterator<Element> iter5 = doc.select("p.writer").iterator();

			while (iter1.hasNext()) {

				// 1. title
				String str = iter1.next().toString();
				str = str.substring(0, str.lastIndexOf("<"));
				str = str.substring(str.lastIndexOf(">") + 1);
				title = str;

				// 2. content -- ����

				str = iter2.next().toString();
				str = str.substring(0, str.lastIndexOf("<"));
				str = str.substring(str.lastIndexOf(">") + 1);

				// content.add(str);

				content = str;

				// 3. EX -- ����
				str = iter3.next().toString();
				str = str.substring(0, str.lastIndexOf("<"));

				str = str.substring(str.lastIndexOf(">") + 1);

				ex = str;

				// 4. answer -- ��(�ڵ�)
				str = iter4.next().toString();
				str = str.substring(0, str.lastIndexOf("<"));

				str = str.substring(str.lastIndexOf(">") + 1);

				answer = str;

				// 5. writer -- ��ó

				str = iter5.next().toString();
				str = str.substring(0, str.lastIndexOf("<"));

				str = str.substring(str.lastIndexOf(">") + 1);

				writer = str;

				System.out.println("\n===========Ȯ�ο�===========\n");

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
			System.out.println(i + "��° ��α� ��  DB���ԿϷ�");
			
			
			//1�� ���
			try {Thread.sleep(1000);} catch (InterruptedException e) {}
			

		}


	}
}
