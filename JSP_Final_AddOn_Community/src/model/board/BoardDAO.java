package model.board;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.common.JDBC;
import model.test.TestVO;
import model.users.UsersVO;

public class BoardDAO {


	// �Խñ� ���� �ۼ� 
	// �Է� �� : (B_ID, USER_NUM, B_CTGR, B_TITLE, B_CONTENT, B_WRITER, B_LANG)
	// DEFAULT �ڵ� �Է°�(B_DATE, B_HIT, RE_CNT)
	static String sql_INSERT = "INSERT INTO board(bid, usernum, bctgr, btitle, bcontent, bwriter, blang) "
			+ "VALUES((SELECT NVL(MAX(bid), 0)+1 FROM board),?,?,?,?,?,?)";
	// �Խñ� ����
	static String sql_DELETE = "DELETE FROM board WHERE bid=?";
	// �Խñ� ������Ʈ 
	// ���氡�� ���� : (ī�װ�, Ÿ��Ʋ, ������(�Խñ� ����), ���α׷��� ���) 
	// ����Ұ� : (B_ID, USER_NUM, �ۼ���,�ۼ���, ��ۼ�, ��ȸ��)
	static String sql_UPDATE = "UPDATE board SET bctgr=?, btitle=?, bcontent=?, blang=? WHERE bid=?";

	// ���ڽ� ����¡
	/*static String sql_SELECT_ALL_USER = "SELECT * FROM (SELECT ROWNUM AS RNUM, "
			+ "BOARD.* FROM BOARD WHERE USER_NUM=? AND ROWNUM<=? ORDER BY ? DESC, B_ID DESC) WHERE ?<RNUM AND B_TITLE LIKE '%'||?||'%'";*/
	static String sql_SELECT_ALL_USER;

	// ���� �ֽ� selectAll_USER ******
	/*static String sql_SELECT_ALL_USER = "SELECT * FROM ("
			+ "SELECT ROWNUM AS RNUM, board.* FROM ("
			+ "SELECT * FROM board WHERE usernum=? AND btitle LIKE '%'||?||'%' ORDER BY bdate DESC"
			+ ") board WHERE ROWNUM <= ?" //����
			+ ") WHERE RNUM > ? ORDER BY ? DESC, bdate DESC";*/

	static String sql_SELECT_ALL;

	// ���� �ֽ� selectAll ******
	/*static String sql_SELECT_ALL = "SELECT * FROM ("
			+ "SELECT ROWNUM AS RNUM, board.* FROM ("
			+ "SELECT * FROM BOARD WHERE bctgr = ? AND btitle LIKE '%'||?||'%' ORDER BY bdate DESC"
			+ ") board WHERE ROWNUM <= ?" //����
			+ ") WHERE RNUM > ? ORDER BY recnt DESC, bdate DESC";*/

	// �Խñ� ��ü���
	/*static String sql_SELECT_ALL = "SELECT * FROM (SELECT ROWNUM AS RNUM, "
			+ "BOARD.* FROM BOARD WHERE B_CTGR=? AND ROWNUM<=? ORDER BY ? DESC, B_ID DESC) WHERE ?<RNUM AND B_TITLE LIKE '%'||?||'%'";*/

	// �Խñ� �ϳ� ���
	static String sql_SELECT_ONE = "SELECT * FROM board WHERE bid=?";
	//=====================================================================================
	// selectAll BoardVO�� ����ִ� datas ��ȯ

	static int pageSize = 10; // ������ 10���� ��� ��°��� �ٲٽǶ� ���� �ٲٽø� �˴ϴ�.

	// userNum �� ���� �� 0�־��ּ���

	@SuppressWarnings("resource")
	public BoardSet getDBList(UsersVO uvo, BoardVO vo, String pageOrder, int pageNum) {
		Connection conn = JDBC.getConnection();
		PreparedStatement pstmt = null;
		BoardSet datas = new BoardSet();
		// �Խñ��� ���� ����Ʈ + ��ü �Խù� cnt;
		String sql;
		int cnt=0;

		int startNum = pageNum*pageSize;
		int lastNum = (pageNum*pageSize)+pageSize;

		// System.out.println(uvo);
		// System.out.println(vo);

		try {
			// �α��� ���̵� �ִٸ� (�� �ۺ���)
			if (uvo.getUserNum() > 0) {
				if(pageOrder.equals("reply")) {
					sql_SELECT_ALL_USER = "SELECT * FROM ("
							+ "SELECT ROWNUM AS RNUM, board.* FROM ("
							+ "SELECT * FROM BOARD WHERE usernum=? AND btitle LIKE '%'||?||'%' AND bctgr=? ORDER BY recnt DESC, bdate DESC"
							+ ") board WHERE ROWNUM <= ?" //����
							+ ") WHERE RNUM > ? ORDER BY recnt DESC";
					System.out.println("��ۼ� ���");
				}
				else if(pageOrder.equals("hit")){
					sql_SELECT_ALL_USER = "SELECT * FROM ("
							+ "SELECT ROWNUM AS RNUM, board.* FROM ("
							+ "SELECT * FROM BOARD WHERE usernum=? AND btitle LIKE '%'||?||'%' AND bctgr=? ORDER BY bhit DESC, bdate DESC"
							+ ") board WHERE ROWNUM <= ?" //����
							+ ") WHERE RNUM > ? ORDER BY bhit DESC";
					System.out.println("��ȸ�� ���");
				}
				else {
					sql_SELECT_ALL_USER = "SELECT * FROM ("
							+ "SELECT ROWNUM AS RNUM, board.* FROM ("
							+ "SELECT * FROM board WHERE usernum=? AND btitle LIKE '%'||?||'%' AND bctgr=? ORDER BY bdate DESC"
							+ ") board WHERE ROWNUM <= ?" //����
							+ ") WHERE RNUM > ? ORDER BY bdate DESC";
					System.out.println("��ü ���");
				}


				pstmt = conn.prepareStatement(sql_SELECT_ALL_USER);
				pstmt.setInt(1, uvo.getUserNum());
				pstmt.setString(2, vo.getbTitle());
				pstmt.setString(3, vo.getbCtgr());
				pstmt.setInt(4, lastNum);    // max ��ȣ
				pstmt.setInt(5, startNum);       // start ��ȣ

			}
			// ��ü ���
			else {

				if(pageOrder.equals("reply")) {
					sql_SELECT_ALL = "SELECT * FROM ("
		                     + "SELECT ROWNUM AS RNUM, board.* FROM ("
		                     + "SELECT * FROM board WHERE bctgr = ? AND btitle LIKE '%'||?||'%' ORDER BY recnt DESC, bdate DESC"
		                     + ") board WHERE ROWNUM <= ?" //����
		                     + ") WHERE RNUM > ? ORDER BY recnt DESC";
					System.out.println("��ۼ� ���");
				}
				else if(pageOrder.equals("hit")){
					sql_SELECT_ALL = "SELECT * FROM ("
		                     + "SELECT ROWNUM AS RNUM, board.* FROM ("
		                     + "SELECT * FROM board WHERE bctgr = ? AND btitle LIKE '%'||?||'%' ORDER BY bhit DESC, bdate DESC"
		                     + ") board WHERE ROWNUM <= ?" //����
		                     + ") WHERE RNUM > ? ORDER BY bhit DESC";
					System.out.println("��ȸ�� ���");
				}
				else {
					sql_SELECT_ALL = "SELECT * FROM ("
		                     + "SELECT ROWNUM AS RNUM, board.* FROM ("
		                     + "SELECT * FROM BOARD WHERE bctgr = ? AND btitle LIKE '%'||?||'%' ORDER BY bdate DESC"
		                     + ") board WHERE ROWNUM <= ?" //����
		                     + ") WHERE RNUM > ? ORDER BY bdate DESC";
					System.out.println("��ü ���");
				}
				
				pstmt = conn.prepareStatement(sql_SELECT_ALL);
				pstmt.setString(1, vo.getbCtgr());
				pstmt.setString(2, vo.getbTitle());
				pstmt.setInt(3, lastNum);    // max ��ȣ
				/*if(pageOrder.equals("��ۼ�")) {
	               pstmt.setString(3, "RE_CNT");
	               System.out.println("��ۼ� ���");
	            }
	            else if(pageOrder.equals("��ȸ��")){
	               pstmt.setString(3, "B_HIT");
	               System.out.println("��ȸ�� ���");
	            }
	            else {
	               pstmt.setString(3, "'%'");
	               System.out.println("��ü ���");
	            }*/
				pstmt.setInt(4, startNum);       // start ��ȣ
			}

			ResultSet rs = pstmt.executeQuery();
			BoardVO bvo = null;
			ArrayList<BoardVO>blist = new ArrayList<BoardVO>();

			while(rs.next()) {
				bvo = new BoardVO();

				bvo.setbId(rs.getInt("bid"));           	// �Խñ� ��ȣ
				bvo.setUserNum(rs.getInt("usernum"));      	// �ۼ� ���� ��ȣ
				bvo.setbCtgr(rs.getString("bctgr"));      	// �Խñ� ī�װ�
				bvo.setbTitle(rs.getString("btitle"));    	// �Խñ� ����
				bvo.setbContent(rs.getString("bcontent")); 	// �Խñ� ����
				bvo.setbWriter(rs.getString("bwriter"));    // �۾���
				bvo.setbDate(rs.getDate("bdate"));       	// �ۼ���
				bvo.setbHit(rs.getInt("bhit"));          	// ��ȸ��
				bvo.setbLang(rs.getString("blang"));      	// ���α׷��� ���
				bvo.setReCnt(rs.getInt("recnt"));         	// ��� �� 

				blist.add(bvo);
				System.out.println("bvo Ȯ�� : "+bvo);

			}
			rs.close();

			// System.out.println("blist Ȯ�� : "+blist);

			if(uvo.getUserNum() > 0) {
				// ���� �� �� ��ü ���� ���
				sql = "SELECT COUNT(*) FROM board WHERE bctgr=? AND usernum=? AND btitle LIKE '%'||?||'%'";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, vo.getbCtgr());
				pstmt.setInt(2, uvo.getUserNum());
				pstmt.setString(3, vo.getbTitle());
			}
			else {
				// ī�װ��� ��ü ���� ���
				sql = "SELECT COUNT(*) FROM board WHERE bctgr=? AND btitle LIKE '%'||?||'%'";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, vo.getbCtgr());
				pstmt.setString(2, vo.getbTitle());
			}

			ResultSet crs = pstmt.executeQuery();
			if(crs.next()) {
				cnt=crs.getInt(1);
			}
			crs.close();   

			datas.setBlist(blist);
			datas.setBoardCnt(cnt);

		} catch(SQLException e) {
			System.out.println("boardDAO getDBList���� �߻�");
			e.printStackTrace();
		}
		// System.out.println(datas);
		return datas;
	}

	//=====================================================================================
	public BoardVO getDBData(UsersVO uvo, BoardVO bvo) {
		Connection conn = JDBC.getConnection();
		PreparedStatement pstmt = null;
		BoardVO data = new BoardVO();

		try {

		

			pstmt = conn.prepareStatement(sql_SELECT_ONE);
			pstmt.setInt(1, bvo.getbId());

			ResultSet rs = pstmt.executeQuery();

			if(rs.next()) {

				data.setbId(rs.getInt("bid"));           	// �Խñ� ��ȣ
				data.setUserNum(rs.getInt("usernum"));      // �ۼ� ���� ��ȣ
				data.setbCtgr(rs.getString("bctgr"));      	// �Խñ� ī�װ�
				data.setbTitle(rs.getString("btitle"));    	// �Խñ� ����
				data.setbContent(rs.getString("bcontent")); // �Խñ� ����
				data.setbWriter(rs.getString("bwriter"));   // �۾���
				data.setbDate(rs.getDate("bdate"));       	// �ۼ���
				data.setbHit(rs.getInt("bhit"));          	// ��ȸ��
				data.setbLang(rs.getString("blang"));      	// ���α׷��� ���
				data.setReCnt(rs.getInt("recnt"));         	// ��� �� 

				// System.out.println(data);
			}
			rs.close();

			// ���� �� ���̸� ��ȸ�� ���� ����
	

		} catch (SQLException e) {
			System.out.println("BoardDAO getDBData���� �߻�");
			e.printStackTrace();
		}
		System.out.println("BoardDAO selectOne : "+data);
		return data;
	}
	//=====================================================================================
	public boolean insert(BoardVO vo) {
		Connection conn = JDBC.getConnection();
		PreparedStatement pstmt = null;
		boolean res = false;
		try {
			// (SELECT NVL(MAX(B_ID), 0)+1 FROM BOARD),?,?,?,?,?,?)
			// ? �Է� �� : (USER_NUM, B_CTGR, B-TITLE, B_CONTENT, B_WRITER, B_LANG) 
			pstmt = conn.prepareStatement(sql_INSERT);
			pstmt.setInt(1, vo.getUserNum()); 		// �����ѹ�
			pstmt.setString(2, vo.getbCtgr());		// ī�װ�
			pstmt.setString(3, vo.getbTitle());		// Ÿ��Ʋ
			pstmt.setString(4, vo.getbContent());	// ����
			pstmt.setString(5, vo.getbWriter());	// �۾���
			pstmt.setString(6, vo.getbLang());		// ���α׷��� ���
			pstmt.executeUpdate();
			res=true;

		} catch(SQLException e) {
			System.out.println("BoardDAO insert���� �߻�");
			e.printStackTrace();
		} finally {
			JDBC.disconnect(pstmt, conn);
		}
		return res;
	}
	//=====================================================================================
	public boolean update(BoardVO vo) {
		Connection conn = JDBC.getConnection();
		PreparedStatement pstmt=null;
		boolean res = false;
		try {
			// ���氡�� ���� : (ī�װ�, Ÿ��Ʋ, ������(�Խñ� ����), ���α׷��� ���) 
			// ����Ұ� : (B_ID, USER_NUM, �ۼ���,�ۼ���, ��ۼ�, ��ȸ��)
			// B_CTGR=?, B_TITLE=?, B_CONTENT=?, B_LANG=? WHERE B_NUM=?
			pstmt = conn.prepareStatement(sql_UPDATE);
			pstmt.setString(1, vo.getbCtgr());
			pstmt.setString(2, vo.getbTitle());
			pstmt.setString(3, vo.getbContent());
			pstmt.setString(4, vo.getbLang());
			pstmt.setInt(5, vo.getbId());
			pstmt.executeUpdate();
			res=true;
		} catch (SQLException e) {
			System.out.println("BoardDAO update���� �߻�");
			e.printStackTrace();
		} finally {
			JDBC.disconnect(pstmt, conn);
		}
		return res;
	}
	//=====================================================================================
	public boolean delete(BoardVO vo) {
		Connection conn = JDBC.getConnection();
		PreparedStatement pstmt = null;
		boolean res = false;
		try {
			pstmt = conn.prepareStatement(sql_DELETE);
			pstmt.setInt(1, vo.getbId());	// ���̵� ������ ����
			pstmt.executeUpdate();
			res = true;
		} catch (SQLException e) {
			System.out.println("BoardDAO delete���� �߻�");
			e.printStackTrace();
		} finally {
			JDBC.disconnect(pstmt, conn);
		}
		return res;
	}
	//=====================================================================================
	// ī�װ��� �Խñ� ����
	/* public int boardCnt(BoardVO bvo, UsersVO uvo) {
		Connection conn = JNDI.getConnection();
		PreparedStatement pstmt = null;
		int cnt = 0;
		String sql = "SELECT COUNT(*) FROM BOARD WHERE B_CTGR=? AND USER_NUM LIKE '%'||?||'%'";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, bvo.getbCtgr());
			pstmt.setInt(2, uvo.getUserNum());

			ResultSet rs = pstmt.executeQuery();
			cnt=rs.getInt(1);

			rs.close();
		} catch (SQLException e) {
			System.out.println("BoardDAO boardCnt���� �߻�");
			e.printStackTrace();
		}
		return cnt;
	}*/
	
	public void addHit(BoardVO vo) {
		Connection conn = JDBC.getConnection();
		
		PreparedStatement pstmt = null;

		try {
			String sql = "update board set bhit = bhit+1 where bid=?";
			pstmt = conn.prepareStatement(sql);
			System.out.println("��� ��ȸ�� ���� ����!!");
			pstmt.setInt(1, vo.getbId());
			pstmt.executeUpdate();
		
		} catch (SQLException e) {
			System.out.println("TestDAO-delete ���� �α�");
			e.printStackTrace();
		} finally {
			JDBC.disconnect(pstmt, conn);
		}
		
		
	}
}

