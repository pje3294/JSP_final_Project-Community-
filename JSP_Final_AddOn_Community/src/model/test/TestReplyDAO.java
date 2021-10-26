package model.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.board.ReplyVO;
import model.common.JDBC;
import model.users.UsersVO;

public class TestReplyDAO {

	static int pageSize = 10; // ����¡ ���� ����

	static String sql_INSERT = "INSERT INTO testreply (rid, tid, usernum, rcontent, rwriter, parentid) "
			+ "VALUES ((SELECT NVL(MAX(rid),0)+1 FROM testreply),?,?,?,?,?)";

	static String sql_RECNT_UP = "UPDATE test SET recnt= recnt+1 WHERE tid=?";
	// ���/������ �޸��� Ư�� TEST�Խù��� ��� �� (recnt) ++

	static String sql_UPDATE = "UPDATE testreply SET rcontent=? WHERE rid =?"; // ���/���� ����

	//--------------------------------------------------------------------------------------------------------------------------		

	// getDBList ��+��� = 1:N
	@SuppressWarnings("resource")
	public ArrayList<TestReplySet> getDBList(int pageNum, TestReplyVO vo) { // pageNum : ����¡���� ����
		Connection conn = JDBC.getConnection();
		PreparedStatement pstmt = null;
		String sql;
		int cnt = 0;

		ArrayList<TestReplySet> datas = new ArrayList<TestReplySet>();

		ArrayList<TestReplyVO> rrlist = null;
		TestReplyVO reply = null;

		try { // ��ü ��

			// ��ü (�α��� ���� ����)

			sql = "SELECT rid, tid, usernum, CASE WHEN deleteat='Y' THEN 'unknown' "
					+ "ELSE rwriter END AS rwriter, CASE WHEN deleteat='Y' THEN '*������ ����Դϴ�.' "
					+ "ELSE rcontent END AS rcontent, rdate, deleteat, parentid FROM (SELECT ROWNUM AS RNUM,"
					+ "testreply.* FROM (SELECT * FROM testreply WHERE tid=? AND parentid=0 "
					+ "ORDER BY rdate DESC) testreply WHERE ROWNUM <= ?) WHERE RNUM > ? ORDER BY rdate DESC";

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, vo.gettId());
			pstmt.setInt(2, (pageNum * pageSize) + pageSize);
			pstmt.setInt(3, pageNum * pageSize);
			// System.out.println("TestReplyVO�α��� X");

			ResultSet rs = pstmt.executeQuery(); // ���

			while (rs.next()) {

				reply = new TestReplyVO(); // ���
				// ��� �� �־���
				System.out.println("while�� ����");
				reply.setrId(rs.getInt("rid"));
				reply.settId(rs.getInt("tid"));
				reply.setUserNum(rs.getInt("usernum"));
				reply.setrContent(rs.getString("rcontent"));
				reply.setrDate(rs.getDate("rdate"));
				reply.setDeleteAt(rs.getString("deleteat"));
				reply.setrWriter(rs.getString("rwriter"));
				reply.setParentId(rs.getInt("parentid"));

				// System.out.println("reply Ȯ��: " + rvo); // �α�

				// ���� ������ ����
				sql = "SELECT * FROM testreply WHERE parentid=? ORDER BY rdate DESC";

				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, reply.getrId());

				ResultSet rrs = pstmt.executeQuery();

				rrlist = new ArrayList<TestReplyVO>();

				while (rrs.next()) {
					TestReplyVO rrvo = new TestReplyVO();

					rrvo.setrId(rrs.getInt("rid"));
					rrvo.settId(rrs.getInt("tid"));
					rrvo.setUserNum(rrs.getInt("usernum"));
					rrvo.setrContent(rrs.getString("rcontent"));
					rrvo.setrDate(rrs.getDate("rdate"));
					rrvo.setDeleteAt(rrs.getString("deleteat"));
					rrvo.setrWriter(rrs.getString("rwriter"));
					rrvo.setParentId(rrs.getInt("parentid"));

					rrlist.add(rrvo); // ��� ����Ʈ
					// System.out.println("rrlist Ȯ��: " + rrlist);

				}
				rrs.close();

				TestReplySet trs = new TestReplySet();
				trs.setReply(reply);
				trs.setRrlist(rrlist);

				sql = "SELECT COUNT(*) FROM testreply where tid=? AND parentid=0 AND deleteat='N'"; //PARENT_ID=0 AND DELETE_AT='N'" ����!
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, vo.gettId());

				ResultSet total = pstmt.executeQuery();
				if (total.next()) {
					cnt = total.getInt(1);
					System.out.println("cntȮ��: " + cnt);
				}
				total.close();
				trs.setTestReCnt(cnt);

				datas.add(trs);

			}
			rs.close();

		} catch (Exception e) {
			System.out.println("ReplyDAO-selectDBList ����");
			e.printStackTrace();
		} finally {
			JDBC.disconnect(pstmt, conn);
		}
		System.out.println("datas Ȯ��: " + datas);
		return datas;

	}

	/////////////////////////////////////////////////////////////////////////			
	// getDBList ��+��� = 1:N
	@SuppressWarnings("resource")
	public TestMySet myReply(UsersVO uvo, int pageNum) { // pageNum : ����¡���� ����
		Connection conn = JDBC.getConnection();
		PreparedStatement pstmt = null;
		String sql;
		int cnt = 0;

		ArrayList<TestReplyVO> rlist = new ArrayList<TestReplyVO>();

		TestMySet datas = new TestMySet();

		try { // �α��ν�

			sql = "SELECT rid, tid, usernum, CASE WHEN deleteat='Y' THEN 'unknown' "
					+ "ELSE rwriter END AS rwriter, CASE WHEN deleteat='Y' THEN '*������ ����Դϴ�.' "
					+ "ELSE rcontent END AS rcontent, rdate, deleteat, parentid FROM (SELECT ROWNUM AS RNUM, "
					+ "testreply.* FROM (SELECT * FROM testreply WHERE usernum=? AND deleteat='N' "
					+ "ORDER BY rdate DESC) testreply WHERE ROWNUM <= ?) WHERE RNUM > ? ORDER BY rdate DESC";
			// AND T_ID=? "
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, uvo.getUserNum());
			System.out.println("getUserNum: " + uvo.getUserNum());
			pstmt.setInt(2, (pageNum * pageSize) + pageSize);
			pstmt.setInt(3, pageNum * pageSize);
			System.out.println("TestReplyVO�α��� O");

			ResultSet rs = pstmt.executeQuery(); // ���

			while (rs.next()) {
				// TestReplySet ts = new TestReplySet();
				TestReplyVO reply = new TestReplyVO(); // ���
				System.out.println("while�� ����");
				reply.setrId(rs.getInt("rid"));
				reply.settId(rs.getInt("tid"));
				reply.setUserNum(rs.getInt("usernum"));
				reply.setrContent(rs.getString("rcontent"));
				reply.setrDate(rs.getDate("rdate"));
				reply.setDeleteAt(rs.getString("deleteat"));
				reply.setrWriter(rs.getString("rwriter"));
				reply.setParentId(rs.getInt("parentid"));

				rlist.add(reply);
				// System.out.println("reply Ȯ��: " + rvo); // �α�

			}
			rs.close();

			// System.out.println("�α��� �� ��� ����");
			sql = "SELECT COUNT(*) FROM testreply WHERE usernum=? AND deleteat='N'";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, uvo.getUserNum());

			ResultSet total = pstmt.executeQuery();
			if (total.next()) {
				cnt = total.getInt(1);
				System.out.println("cntȮ��: " + cnt);
			}
			total.close();

			// reply.setTestRecnt(cnt);
			datas.setRlist(rlist);
			datas.setTestRecnt(cnt);

		} catch (Exception e) {
			System.out.println("ReplyDAO-selectDBList ����");
			e.printStackTrace();
		} finally {
			JDBC.disconnect(pstmt, conn);
		}
		System.out.println("datas Ȯ��: " + datas);
		return datas;

	}

	/////////////////////////////////////////////////////////////////////////			

	// getDBData
	@SuppressWarnings("resource")
	public TestReplySet getDBData(TestReplyVO vo) { // testSet���� ����
		Connection conn = JDBC.getConnection();
		PreparedStatement pstmt = null;
		TestReplySet data = null;

		TestReplyVO reply = null;

		String sql;

		try {
			sql = "SELECT rid, tid, usernum, CASE WHEN deleteat='Y' THEN 'unknown' ELSE rwriter END AS rwriter, "
					+ "CASE WHEN deleteat='Y' THEN '*������ ����Դϴ�.' ELSE rcontent END AS rcontent, rdate, deleteat, "
					+ "parentid FROM testreply WHERE rid=?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, vo.getrId());
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				reply = new TestReplyVO(); // ���

				reply.setrId(rs.getInt("rid"));
				reply.settId(rs.getInt("tid"));
				reply.setUserNum(rs.getInt("usernum"));
				reply.setrContent(rs.getString("rcontent"));
				reply.setrDate(rs.getDate("rdate"));
				reply.setDeleteAt(rs.getString("deleteat"));
				reply.setrWriter(rs.getString("rwriter"));
				reply.setParentId(rs.getInt("parentid"));

				System.out.println("Testreply Ȯ��: " + reply); // �α�

				ArrayList<TestReplyVO> rrlist = new ArrayList<TestReplyVO>();

				// ���� ������ ����
				if (reply.getParentId() > 0) { // ���� �����Ѵٸ�
					sql = "SELECT * FROM testreply WHERE parentid=?";
					pstmt = conn.prepareStatement(sql);
					pstmt.setInt(1, reply.getrId()); // praent_id == ����� rid

					ResultSet rrs = pstmt.executeQuery();

					while (rrs.next()) {
						TestReplyVO rrvo = new TestReplyVO();

						rrvo.setrId(rrs.getInt("rid"));
						rrvo.settId(rrs.getInt("tid"));
						rrvo.setUserNum(rrs.getInt("usernum"));
						rrvo.setrContent(rrs.getString("rcontent"));
						rrvo.setrDate(rrs.getDate("rdate"));
						rrvo.setDeleteAt(rrs.getString("deleteat"));
						rrvo.setrWriter(rrs.getString("rwriter"));
						rrvo.setParentId(rrs.getInt("parentid"));

						rrlist.add(rrvo); // ���� ����Ʈ

						System.out.println("rrlist Ȯ��: " + rrlist);
					}
					rrs.close();
				}

				data.setReply(reply);
				data.setRrlist(rrlist);
			}
			rs.close();

		} catch (SQLException e) {
			System.out.println("TestReplyDAO-getDBData �����α�");
			e.printStackTrace();
		} finally {
			JDBC.disconnect(pstmt, conn);
		}
		return data;
	}

	/////////////////////////////////////////////////////////////////////////			

	// insert --> ��� ��(RE_CNT) update Ʈ�����!!!!!!��
	@SuppressWarnings("resource")
	public int insert(TestReplyVO vo) {
		Connection conn = JDBC.getConnection();
		int res =0;
		PreparedStatement pstmt = null;

		boolean check = false; // Ʈ����� Ŀ��, �ѹ� ���� �Ǵ� ����

		try {
			conn.setAutoCommit(false);
			String getTidSql = "SELECT NVL(MAX(rid), 0)+1 FROM testreply";
			pstmt = conn.prepareStatement(getTidSql);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				res = rs.getInt(1);
			}
			
			
			pstmt = conn.prepareStatement(sql_INSERT);

			pstmt.setInt(1, vo.gettId());
			pstmt.setInt(2, vo.getUserNum());
			pstmt.setString(3, vo.getrContent());
			pstmt.setString(4, vo.getrWriter());
			pstmt.setInt(5, vo.getParentId());
			pstmt.executeUpdate();

			pstmt = conn.prepareStatement(sql_RECNT_UP);
			pstmt.setInt(1, vo.gettId());
			pstmt.executeUpdate();
			check = true;

			if (check) {
				conn.commit();
				
			} else {
				conn.rollback();
			}

		} catch (SQLException e) {
			System.out.println("TestReplyDAO-insert ���� �α�");
			e.printStackTrace();
		} finally {
			JDBC.disconnect(pstmt, conn);
		}
		return res;
	}

	/////////////////////////////////////////////////////////////////////////

	// delete 
	@SuppressWarnings("resource")
	public boolean delete(TestReplyVO vo) {
		Connection conn = JDBC.getConnection();
		boolean res = false;
		PreparedStatement pstmt = null;
		ResultSet rs= null;

		boolean check = false; // Ʈ����� Ŀ��, �ѹ� ���� �Ǵ� ����

		// ** ��� ������ ��� --> ��¥ ������ �ƴ� ,,, UPDATE ���� (DELETE_AT = 'N' --> 'Y')
		String sql_DELETE_R1 = "DELETE FROM testreply WHERE rid=?";
		
		String sql_DELETE_R2 = "UPDATE testreply SET deleteat='Y' WHERE rid =? AND parentid=0";
		
		// ** ���� ������ ��� --> ��¥ ����
		String sql_DELETE_RR = "DELETE FROM testreply WHERE rid =?";
		
		// ��۴޸� Ư�� TEST�Խù��� ��� �� (RE_CNT) --
		String sql_RECNT_DN = "UPDATE TEST SET recnt= recnt-1 WHERE tid=?";
		
		String sql_COUNT = "SELECT COUNT(*) FROM testreply WHERE parentid=?";

		int cnt = 0;

		try {
			if (vo.getParentId() == 0) {

				// ������ �޸� ������, ���� ���� ������� Ȯ��

				conn.setAutoCommit(false);

				pstmt = conn.prepareStatement(sql_COUNT);
				pstmt.setInt(1, vo.getrId());
				rs = pstmt.executeQuery();

				if (rs.next()) {
					cnt = rs.getInt(1);
					System.out.println(cnt);
				}
				if (cnt == 0) { // ���� ���� ��� ���� ��, -> ���� ����
					pstmt = conn.prepareStatement(sql_DELETE_R1); // ���� DB���� ����
					pstmt.setInt(1, vo.getrId());
					pstmt.executeUpdate();
					System.out.println("���� ���� ��� ���� �Ϸ� �α�");

				} else { // ������ �ִ� ��� ���� -> DELETE_AT�� "N -> Y"�� ����
					pstmt = conn.prepareStatement(sql_DELETE_R2);
					pstmt.setInt(1, vo.getrId());
					pstmt.executeUpdate();
					System.out.println("���� �ִ� ��� ���� �Ϸ� �α�");
				}
				
			} else { // ���� ����� "����"�̶��,
				pstmt = conn.prepareStatement(sql_DELETE_RR);
				pstmt.setInt(1, vo.getrId());
				pstmt.executeUpdate();
				
				if(vo.getDeleteAt().equals("Y")) {
					pstmt = conn.prepareStatement(sql_COUNT);
					pstmt.setInt(1, vo.getParentId());
					rs = pstmt.executeQuery();
					if(rs.next()) {
						cnt=rs.getInt(1);
					}
					if (cnt == 0) {
						pstmt = conn.prepareStatement(sql_DELETE_R1);
						pstmt.setInt(1, vo.getParentId());
						pstmt.executeUpdate();
						System.out.println("���� ������ ��ۻ��� ���");
					}
				}
			}
			
			pstmt = conn.prepareStatement(sql_RECNT_DN); // �ش� �Խù� ��ۼ� -1�ϱ� => ���, ��� ���� �� �Խñ� ��� �� --
			pstmt.setInt(1, vo.gettId());
			pstmt.executeUpdate();

			check = true; // Ŀ�� �ϱ�����

			if (check) {
				conn.commit();
				// System.out.println("Ŀ��Ȯ��");
				res = true;
			} else {
				// System.out.println("�ѹ�Ȯ��");
				conn.rollback();
			}
			
		} catch (SQLException e) {
			System.out.println("TestReplyDAO-delete ���� �α�");
			e.printStackTrace();
		} finally {
			JDBC.disconnect(pstmt, conn);
		}
		return res;

	}

	/////////////////////////////////////////////////////////////////////////	

	// update
	public boolean update(TestReplyVO vo) {
		Connection conn = JDBC.getConnection();
		boolean res = false;
		PreparedStatement pstmt = null;
		
		try {
			pstmt = conn.prepareStatement(sql_UPDATE);
			pstmt.setString(1, vo.getrContent());
			pstmt.setInt(2, vo.getrId());
			pstmt.executeUpdate();
			
			System.out.println("���/���� ���� Ȯ��"); // �α�
			
			res = true;
			
		} catch (SQLException e) {
			System.out.println("TestReplyDAO-update ���� �α�");
			e.printStackTrace();
		} finally {
			JDBC.disconnect(pstmt, conn);
		}
		return res;
	}

	/////////////////////////////////////////////////////////////////////////
	// ���ã�� ��� ////////////////////////////////////////////////////////////////////
	public int replyOrder(TestReplyVO vo)  {
		Connection conn = JDBC.getConnection();
		PreparedStatement pstmt = null;
		int order =0;
		
		String sql_order = "select rnum from (select rownum as rnum, rid from (select rid from testREPLY where parentid=0 and tid=? order by rdate desc) ) where rid=? ";
		
		try {
			pstmt = conn.prepareStatement(sql_order);
			pstmt.setInt(1, vo.gettId());
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
