package crawling.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import crawling.common.JDBC;



/*
   tid INT PRIMARY KEY,
   usernum int NOT NULL,
   ttitle VARCHAR(100) NOT NULL,
   tcontent VARCHAR(4000) NOT NULL,
   tanswer VARCHAR(4000) NOT NULL,
   tex VARCHAR(225) NOT NULL,
   twriter VARCHAR(50) NOT NULL,
   tdate DATE DEFAULT SYSDATE,
   thit int DEFAULT 0,
   tlang VARCHAR(20) NOT NULL,
   recnt int DEFAULT 0,
   CONSTRAINT user_num_cons FOREIGN KEY (usernum) REFERENCES users(usernum) ON DELETE CASCADE
 * */

public class CodingDAO {

	static String sql_INSERT = "INSERT INTO test (tid, usernum, ttitle, tcontent, tanswer, tex, twriter, tlang) VALUES ((SELECT NVL(MAX(tid),0)+1 FROM test),1,?,?,?,?,?,'JAVA')";
	static String sql_SELECT_ONE ="SELECT * FROM test WHERE tid=?";
	static String sql_SELECT_ALL ="SELECT * FROM test";
	
	public void insert(CodingVO vo) {
		System.out.println("CodingDAO - insert확인");

		Connection conn = JDBC.getConnection();
		PreparedStatement pstmt = null;

		try {
			pstmt = conn.prepareStatement(sql_INSERT);

			pstmt.setString(1, vo.gettTitle());
			pstmt.setString(2, vo.gettContent());
			pstmt.setString(3, vo.gettAnswer());
			pstmt.setString(4, vo.gettEx());
			pstmt.setString(5, vo.gettWriter());

			pstmt.executeUpdate();
			System.out.println("크롤링 insert 성공");

		} catch (SQLException e) {
			System.out.println("CodingDAO - insert 오류 로깅");
			e.printStackTrace();
		} finally {
			JDBC.disconnect(pstmt, conn);
		}
	}
	

	public CodingVO selectOne(CodingVO vo) {
		System.out.println("CodingDAO - selectOne확인");

		Connection conn = JDBC.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs;
		CodingVO data = null;

		try {
			pstmt = conn.prepareStatement(sql_SELECT_ONE);

			pstmt.setInt(1, vo.gettId());
			rs = pstmt.executeQuery();

			/*
			   tid INT PRIMARY KEY,
			   usernum int NOT NULL,
			   ttitle VARCHAR(100) NOT NULL,
			   tcontent VARCHAR(4000) NOT NULL,
			   tanswer VARCHAR(4000) NOT NULL,
			   tex VARCHAR(225) NOT NULL,
			   twriter VARCHAR(50) NOT NULL,
			   tdate DATE DEFAULT SYSDATE,
			   thit int DEFAULT 0,
			   tlang VARCHAR(20) NOT NULL,
			   recnt int DEFAULT 0,
			   CONSTRAINT user_num_cons FOREIGN KEY (usernum) REFERENCES users(usernum) ON DELETE CASCADE
			 * */

			if(rs.next()) {
				data = new CodingVO();
				data.settAnswer(rs.getString("tanswer"));
				data.settDate(rs.getDate("tdate"));
				data.settContent(rs.getString("tcontent"));
				data.settEx(rs.getString("tex"));
				data.settHit(rs.getInt("thit"));
				data.settId(rs.getInt("tid"));
				data.setReCnt(rs.getInt("recnt"));
				data.settTitle(rs.getString("ttitle"));
				data.setUserNum(rs.getInt("usernum"));
				data.settLang(rs.getString("tlang"));
				data.settWriter(rs.getString("twriter"));
			}
			rs.close();
			
		} catch (SQLException e) {
			System.out.println("CodingDAO - selectOne 오류 로깅");
			e.printStackTrace();
		} finally {
			JDBC.disconnect(pstmt, conn);
		}
		return data;
	}

	////////////////////////////////////////////
	///////////////////////////////////////////
	public ArrayList<CodingVO> selectAll() {
		System.out.println("CodingDAO - selectAll확인");

		Connection conn = JDBC.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs;

		ArrayList<CodingVO> datas = new ArrayList<CodingVO>();
		try {
			pstmt = conn.prepareStatement(sql_SELECT_ALL);


			rs = pstmt.executeQuery();

			/*
			   tid INT PRIMARY KEY,
			   usernum int NOT NULL,
			   ttitle VARCHAR(100) NOT NULL,
			   tcontent VARCHAR(4000) NOT NULL,
			   tanswer VARCHAR(4000) NOT NULL,
			   tex VARCHAR(225) NOT NULL,
			   twriter VARCHAR(50) NOT NULL,
			   tdate DATE DEFAULT SYSDATE,
			   thit int DEFAULT 0,
			   tlang VARCHAR(20) NOT NULL,
			   recnt int DEFAULT 0,
			   CONSTRAINT user_num_cons FOREIGN KEY (usernum) REFERENCES users(usernum) ON DELETE CASCADE
			 * */

			while(rs.next()) {
				CodingVO data = new CodingVO();
				data.settAnswer(rs.getString("tanswer"));
				data.settDate(rs.getDate("tdate"));
				data.settContent(rs.getString("tcontent"));
				//data.settContent(rs.getString("tcontent").replaceAll(";", "&nbsp;"));
				data.settEx(rs.getString("tex"));
				data.settHit(rs.getInt("thit"));
				data.settId(rs.getInt("tid"));
				data.setReCnt(rs.getInt("recnt"));
				data.settTitle(rs.getString("ttitle"));
				data.setUserNum(rs.getInt("usernum"));
				data.settLang(rs.getString("tlang"));
				data.settWriter(rs.getString("twriter"));
				datas.add(data);
				
			}
			rs.close();
			
		} catch (SQLException e) {
			System.out.println("CodingDAO - selectOne 오류 로깅");
			e.printStackTrace();
		} finally {
			JDBC.disconnect(pstmt, conn);
		}
		return datas;
	}
	
	
	
}
