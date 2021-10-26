package crawlingc;

import java.util.ArrayList;

import crawling.model.CodingDAO;
import crawling.model.CodingVO;

public class CheckingDB {
	public static void main(String[] args) {
		CodingDAO dao = new CodingDAO();
		

		/*System.out.println("******selectOne DBÈ®ÀÎ¿ë *********");
		vo.settId(1);
		CodingVO data = dao.selectOne(vo);
		System.out.println(data);*/
		
		ArrayList<CodingVO> datas = new ArrayList<CodingVO>();
		
		datas= dao.selectAll();
		 
		for(CodingVO v : datas) {
			System.out.println(" ");
			System.out.println(v+" ");
			System.out.println("\n=====================================\n");
		}
		
		
		
	}
}
