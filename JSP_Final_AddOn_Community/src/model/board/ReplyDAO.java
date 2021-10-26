package model.board;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.common.JDBC;
import model.users.UsersVO;

public class ReplyDAO {

	// ��� ���� �ۼ� 
	// �Է� �� : (R_ID, USER_NUM, B_ID, R_CONTENT, R_WRITER, PARENT_ID) 
	// DEFAULT �ڵ� �Է°�(R_DATE, DELETE_AT)
	static String sql_INSERT = "INSERT INTO boardreply (rid, bid, usernum, rcontent, rwriter, parentid) "
			+ "VALUES((SELECT NVL(MAX(rid), 0)+1 FROM boardreply),?,?,?,?,?)";
	// ��� ����
	static String sql_DELETE = "UPDATE boardreply SET deleteat='Y' WHERE rid=?";
	// ��� ������Ʈ 
	static String sql_UPDATE = "UPDATE boardreply SET rcontent=? WHERE rid=?";

	// ���� ID �� ��� ��ü���
	static String sql_SELECT_ALL_USER = "SELECT rid, bid, usernum, "
			+ "CASE WHEN deleteat='Y' THEN 'unknown' ELSE rwriter END AS rwriter, "
			+ "CASE WHEN deleteat='Y' THEN '*������ ����Դϴ�.' ELSE rcontent END AS rcontent , "
			+ "rdate, deleteat, parentid FROM ("
			+ "SELECT ROWNUM AS RNUM, boardreply.* FROM ("
			+ "SELECT * FROM boardreply WHERE usernum=? AND deleteat='N' ORDER BY rdate DESC"
			+ ") boardreply WHERE ROWNUM<=? "
			+ ") WHERE RNUM > ? ORDER BY rdate DESC";

	// ��� ��ü���
	static String sql_SELECT_ALL = "SELECT rid, bid, usernum, "
			+ "CASE WHEN deleteat='Y' THEN 'unknown' ELSE rwriter END AS rwriter, "
			+ "CASE WHEN deleteat='Y' THEN '*������ ����Դϴ�.' ELSE rcontent END AS rcontent , "
			+ "rdate, deleteat, parentid FROM ("
			+ "SELECT ROWNUM AS RNUM, boardreply.* FROM ("
			+ "SELECT * FROM boardreply WHERE bid=? AND parentid=0 ORDER BY rdate DESC"
			+ ") boardreply WHERE ROWNUM<=? "
			+ ") WHERE RNUM > ? ORDER BY rdate DESC";


	// ��� �ϳ� ���
	static String sql_SELECT_ONE = "SELECT rid, bid, usernum, "
			+ "CASE WHEN deleteat='Y' THEN 'unknown' ELSE rwriter END AS rwriter, "
			+ "CASE WHEN deleteat='Y' THEN '*������ ����Դϴ�.' ELSE rcontent END AS rcontent, "
			+ "rdate, deleteat, parentid FROM boardreply WHERE rid=?";

	//========================================================================================
	// selectAll ��� ��ü + ���� List�� ����ִ� datas ��ȯ

	static int pageSize = 10; // ������ 10���� ��� ��°��� �ٲٽǶ� ���� �ٲٽø� �˴ϴ�.

	// userNum �� ���� �� 0�־��ּ���
	@SuppressWarnings("resource")
	public ArrayList<ReplySet> getDBList(ReplyVO vo, int pageNum) {
	

		Connection conn = JDBC.getConnection();
		PreparedStatement pstmt = null;
		ArrayList<ReplySet> datas = new ArrayList<ReplySet>();
		ReplyVO rvo = null;
		int cnt = 0;

		//System.out.println(uvo);
		//System.out.println(vo);
		//System.out.println(pageNum);

		int startNum = pageNum*pageSize;
		int lastNum = (pageNum*pageSize)+pageSize;

		try {
			// ��ü ���
			pstmt = conn.prepareStatement(sql_SELECT_ALL);
			pstmt.setInt(1, vo.getbId());
			pstmt.setInt(2, lastNum);
			pstmt.setInt(3, startNum);
			// System.out.println("��ü��� ���");

			System.out.println("start : "+lastNum);
			System.out.println("last : "+startNum);

			ResultSet rs = pstmt.executeQuery();
			// ��� ��ü

			while(rs.next()) {
				// System.out.println("replyDAO.getDBList while�ȿ��� ���");
				rvo = new ReplyVO();

				rvo.setrId(rs.getInt("rid"));            	// ��� PK
				rvo.setbId(rs.getInt("bid"));            	// �Խñ� ��ȣ
				rvo.setUserNum(rs.getInt("usernum"));       // ���� ��ȣ
				rvo.setrWriter(rs.getString("rwriter"));    // �ۼ���
				rvo.setrContent(rs.getString("rcontent"));  // ��� ����
				rvo.setrDate(rs.getDate("rdate"));         	// �ۼ���
				rvo.setDeleteAt(rs.getString("deleteat"));  // ��������
				rvo.setParentId(rs.getInt("parentid"));   	// ��� ���� ����

				// System.out.println("��� 1��ü : "+rvo);

				String rrsql = "SELECT * FROM boardreply WHERE parentid=? ORDER BY rdate DESC";
				pstmt = conn.prepareStatement(rrsql);
				pstmt.setInt(1, rvo.getrId()); // ������ parent�� ����� ID
				ResultSet rrs = pstmt.executeQuery();

				ArrayList<ReplyVO> rrlist = new ArrayList<ReplyVO>();

				while(rrs.next()) {
					ReplyVO rrvo = new ReplyVO();

					rrvo.setrId(rrs.getInt("rid"));            		// ��� PK
					rrvo.setbId(rrs.getInt("bid"));            		// �Խñ� ��ȣ
					rrvo.setUserNum(rrs.getInt("usernum"));     	// ���� ��ȣ
					rrvo.setrWriter(rrs.getString("rwriter"));  	// �ۼ���
					rrvo.setrContent(rrs.getString("rcontent"));	// ��� ����
					rrvo.setrDate(rrs.getDate("rdate"));        	// �ۼ���
					rrvo.setDeleteAt(rrs.getString("deleteat"));	// ��������
					rrvo.setParentId(rrs.getInt("parentid"));   	// ��� ���� ����

					// System.out.println("���� writer Ȯ�� : "+rrvo.getrWriter());
					rrlist.add(rrvo);

					// System.out.println("���� list"+rrlist);
				}
				rrs.close();

				ReplySet replySet = new ReplySet();
				replySet.setRvo(rvo); // ��� 1
				replySet.setRrlist(rrlist); // ��ۿ� ���� ����
				datas.add(replySet); // ���1 + ���� N��

				// System.out.println("replySet : "+replySet);
			}
			rs.close();

			String sql;

			// ��� ��ü ���� ���
			sql = "SELECT COUNT(*) FROM boardreply WHERE bid=? AND deleteat='N' AND parentid=0"; //  AND DELETE_AT='N' AND PARENT_ID=0 �̺κ� �߰�����!
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, vo.getbId());

			ReplySet replySet = new ReplySet();

			ResultSet crs = pstmt.executeQuery();
			if(crs.next()) {
				cnt=crs.getInt(1);
			}
			crs.close();

			replySet.setReplyCnt(cnt);
			datas.add(replySet);

		}  catch(SQLException e) {
			System.out.println("ReplyDAO getDBList���� �߻�");
			e.printStackTrace();
		} finally {
			JDBC.disconnect(pstmt, conn);
		}

		/*for(ReplySet v : datas) {
	         System.out.println("���� : "+v);
	      }*/
		return datas;
	}
	//========================================================================================	
	@SuppressWarnings("resource")
	public MyReplySet myReply(UsersVO uvo, int pageNum) {
		System.out.println("myReply() ����");
		System.out.println("userNum : "+ uvo.getUserNum());
		Connection conn = JDBC.getConnection();
		PreparedStatement pstmt = null;
		ArrayList<ReplyVO> rlist = new ArrayList<ReplyVO>();
		MyReplySet datas = new MyReplySet();
		// �� ��ü ����
		int cnt = 0;
		String sql;

		int startNum = pageNum*pageSize;
		int lastNum = (pageNum*pageSize)+pageSize;

		System.out.println(startNum+"     "+lastNum);

		try {

			pstmt = conn.prepareStatement(sql_SELECT_ALL_USER);
			pstmt.setInt(1, uvo.getUserNum());
			pstmt.setInt(2, lastNum);
			pstmt.setInt(3, startNum);

			ResultSet rs = pstmt.executeQuery();

			while(rs.next()) {
				System.out.println("���Ϲ� ����");
				ReplyVO rvo = new ReplyVO();

				rvo.setrId(rs.getInt("rid"));            	// ��� PK
				rvo.setbId(rs.getInt("bid"));            	// �Խñ� ��ȣ
				rvo.setUserNum(rs.getInt("usernum"));       // ���� ��ȣ
				rvo.setrWriter(rs.getString("rwriter"));    // �ۼ���
				rvo.setrContent(rs.getString("rcontent"));  // ��� ����
				rvo.setrDate(rs.getDate("rdate"));         	// �ۼ���
				rvo.setDeleteAt(rs.getString("deleteat"));  // ��������
				rvo.setParentId(rs.getInt("parentid"));   	// ��� ���� ����

				rlist.add(rvo);

				System.out.println("rvo : "+rvo);
			}
			rs.close();
			System.out.println("���Ϲ� ���");
			System.out.println("rlist : "+rlist);

			sql = "SELECT COUNT(*) FROM boardreply WHERE usernum=? AND deleteat='N'";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, uvo.getUserNum());

			ResultSet crs = pstmt.executeQuery();

			if(crs.next()) {
				cnt=crs.getInt(1);
			}
			crs.close();

			datas.setRlist(rlist);
			datas.setReplyCnt(cnt);

			System.out.println("datas : "+datas);
		} catch (SQLException e) {
			System.out.println("replyDAO myReply���� �߻�");
			e.printStackTrace();
		} finally {
			JDBC.disconnect(pstmt, conn);
		}	
		return datas;
	}
	//========================================================================================
	@SuppressWarnings("resource")
	public ReplySet getDBData(ReplyVO vo) {
		Connection conn = JDBC.getConnection();
		PreparedStatement pstmt = null;
		ReplySet data = new ReplySet();
		ReplyVO rvo = null;
		try {
			// "SELECT * FROM BOARD_REPLY WHERE R_ID=?";
			pstmt = conn.prepareStatement(sql_SELECT_ONE);
			pstmt.setInt(1, vo.getrId());
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				rvo = new ReplyVO();

				rvo.setrId(rs.getInt("rid"));				// ��� PK
				rvo.setbId(rs.getInt("bid"));				// �Խñ� ��ȣ
				rvo.setUserNum(rs.getInt("usernum"));		// ���� ��ȣ
				rvo.setrContent(rs.getString("rcontent"));	// ��� ����
				rvo.setrDate(rs.getDate("rdate"));			// �ۼ���
				rvo.setDeleteAt(rs.getString("deleteat"));	// ��������
				rvo.setrWriter(rs.getString("rwriter"));	// �ۼ���
				rvo.setParentId(rs.getInt("parentid"));		// ��� ���� ����

				ArrayList<ReplyVO> rrlist = new ArrayList<ReplyVO>();

				if(rvo.getParentId() > 0) {
					String sql = "SELECT * FROM boardreply WHERE parentid=?";
					pstmt = conn.prepareStatement(sql);
					pstmt.setInt(1, rvo.getrId()); // ������ parent�� ����� ID
					ResultSet rrs = pstmt.executeQuery();
					// ����

					while (rrs.next()) {
						ReplyVO rrvo = new ReplyVO();

						rrvo.setrId(rrs.getInt("rid"));					// ��� PK
						rrvo.setbId(rrs.getInt("bid"));					// �Խñ� ��ȣ
						rrvo.setUserNum(rrs.getInt("usernum"));			// ���� ��ȣ
						rrvo.setrContent(rrs.getString("rcontent"));	// ��� ����
						rrvo.setrDate(rrs.getDate("rdate"));			// �ۼ���
						rrvo.setDeleteAt(rrs.getString("deleteat"));	// ��������
						rrvo.setrWriter(rrs.getString("rwriter"));		// �ۼ���
						rrvo.setParentId(rrs.getInt("parentid"));		// ��� ���� ����
						
						rrlist.add(rrvo);
					}
					rrs.close();
				}
				data.setRvo(rvo);
				data.setRrlist(rrlist);
			}
			rs.close();
			
		} catch (SQLException e) {
			System.out.println("ReplySet getDBDate���� ���");
			e.printStackTrace();
		} finally {
			JDBC.disconnect(pstmt, conn);
		}
		return data;
	}
	//========================================================================================	
	@SuppressWarnings("resource")
	public int insert(ReplyVO vo) {
		Connection conn = JDBC.getConnection();
		PreparedStatement pstmt = null;
		int res = 0;
		// Ʈ������ Ȯ��
		boolean check = false;
		try {
			// (SELECT NVL(MAX(R_ID), 0)+1 FROM BOARD_REPLY),?,?,?,?,?)
			// ? �Է°� : (B_ID, USER_NUM, R_CONTENT, R_WRITER, PARENT_ID)
			conn.setAutoCommit(false);
			String getRidSql = "SELECT NVL(MAX(rid), 0)+1 FROM boardreply";
			pstmt = conn.prepareStatement(getRidSql);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				res = rs.getInt(1);
			}
					

			pstmt = conn.prepareStatement(sql_INSERT);
			pstmt.setInt(1, vo.getbId());			// ���� ID
			pstmt.setInt(2, vo.getUserNum());		// �����ѹ�
			pstmt.setString(3, vo.getrContent());	// ��۳���
			pstmt.setString(4, vo.getrWriter());	// �ۼ���
			pstmt.setInt(5, vo.getParentId());		// ��� ���� ���� �θ� ��
			pstmt.executeUpdate();

			// ���忡 ��� ī��Ʈ 1����
			String sql = "UPDATE board SET recnt=recnt+1 WHERE bid=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, vo.getbId());
			pstmt.executeUpdate();
			check = true;

			if (check) {
				conn.commit();
				
			}
			else {
				conn.rollback();
			}

		} catch (SQLException e) {
			System.out.println("replyDAO insert���� �߻�");
			e.printStackTrace();
		} finally {
			JDBC.disconnect(pstmt, conn);
		}
		return res;
	}
	//========================================================================================
	@SuppressWarnings("resource")
	public boolean delete(ReplyVO vo) {
		Connection conn = JDBC.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean res = false;
		String sql;
		int cnt=0; // ���� ���� Ȯ��
		
		// Ŀ��Ȯ�ο�
		boolean check = false;

		try {
			// ���� Ŀ�� ����
			conn.setAutoCommit(false);
			// ����̶��
			if (vo.getParentId() == 0) {

				sql ="SELECT COUNT(*) FROM boardreply WHERE parentid=?";
				// sql = "SELECT * FROM BOARD_REPLY WHERE PARENT_ID=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, vo.getrId());
				rs = pstmt.executeQuery();
				if(rs.next()) {
					cnt=rs.getInt(1);
				}
				System.out.println("����϶� �� �� ���� ���� : "+cnt);

				// ���࿡ ��ۿ� ������ ������ �ش� ��� ����
				if (cnt == 0) {
					sql = "DELETE FROM boardreply WHERE rid=?";
					pstmt = conn.prepareStatement(sql);
					pstmt.setInt(1, vo.getrId());
					pstmt.executeUpdate();
					System.out.println("���� ������ ��ۻ��� ���");
				}
				// ������ ������ DELETE_AT Y�� ����
				else {
					pstmt = conn.prepareStatement(sql_DELETE);
					pstmt.setInt(1, vo.getrId());
					pstmt.executeUpdate();
					System.out.println("���� ������ ������Ʈ ���");
				}
			}
			// ���� �̶��
			else {
				sql = "DELETE FROM boardreply WHERE rid=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, vo.getrId());
				pstmt.executeUpdate();
				System.out.println("���� �϶� ���� ���");
				
				if(vo.getDeleteAt().equals("Y")) {
					sql ="SELECT COUNT(*) FROM boardreply WHERE parentid=?";
					pstmt = conn.prepareStatement(sql);
					pstmt.setInt(1, vo.getParentId());
					rs = pstmt.executeQuery();
					if(rs.next()) {
						cnt=rs.getInt(1);
					}
					if (cnt == 0) {
						sql = "DELETE FROM boardreply WHERE rid=?";
						pstmt = conn.prepareStatement(sql);
						pstmt.setInt(1, vo.getParentId());
						pstmt.executeUpdate();
						System.out.println("���� ������ ��ۻ��� ���");
					}
				}
			}

			// ��� �� 1 ����
			sql = "UPDATE board SET recnt = recnt-1 WHERE bid=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, vo.getbId());
			pstmt.executeUpdate();
			System.out.println("��ۼ� �����Ϸ�");

			check = true;

			if (check) {
				conn.commit();
				res= true;
				System.out.println("Ŀ�ԿϷ�");
			}
			else {
				conn.rollback();
				System.out.println("�ѹ�");
			}

		} catch (SQLException e) {
			System.out.println("ReplyDAO delete���� �߻�");
			e.printStackTrace();
		} finally {
			JDBC.disconnect(pstmt, conn);
		}
		return res;
	}
	//========================================================================================	
	public boolean update(ReplyVO vo) {
		Connection conn = JDBC.getConnection();
		PreparedStatement pstmt = null;
		boolean res = false;
		System.out.println("ReplyDAO update() vo.deleteat : "+vo.getDeleteAt());
		try {
			pstmt = conn.prepareStatement(sql_UPDATE);
			pstmt.setString(1, vo.getrContent());
			pstmt.setInt(2, vo.getrId());
			pstmt.executeUpdate();
			res=true;
		} catch (SQLException e) {
			System.out.println("ReplyDAO update���� �߻�");
			e.printStackTrace();
		} finally {
			JDBC.disconnect(pstmt, conn);
		}
		return res;
	}

	/* ��� ã�� */
	
	
	public int replyOrder(ReplyVO vo)  {
		Connection conn = JDBC.getConnection();
		PreparedStatement pstmt = null;
		int order =0;
		
		String sql_order = "select rnum from (select rownum as rnum, rid from (select rid from BOARDREPLY where parentid=0 and bid=? order by rdate desc) ) where rid=? ";
		
		try {
			pstmt = conn.prepareStatement(sql_order);
			pstmt.setInt(1, vo.getbId());
			pstmt.setInt(2, vo.getrId());
			
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				order = rs.getInt(1);
			}
			
			
			
			
		}catch(SQLException e) {
			e.printStackTrace();
			
		}finally{
			JDBC.disconnect(pstmt, conn);
		}
		
		
		return order;
		
	}
	
	

}
