package model.board;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.common.JDBC;
import model.users.UsersVO;

public class ReplyDAO {

	// 댓글 새로 작성 
	// 입력 값 : (R_ID, USER_NUM, B_ID, R_CONTENT, R_WRITER, PARENT_ID) 
	// DEFAULT 자동 입력값(R_DATE, DELETE_AT)
	static String sql_INSERT = "INSERT INTO boardreply (rid, bid, usernum, rcontent, rwriter, parentid) "
			+ "VALUES((SELECT NVL(MAX(rid), 0)+1 FROM boardreply),?,?,?,?,?)";
	// 댓글 삭제
	static String sql_DELETE = "UPDATE boardreply SET deleteat='Y' WHERE rid=?";
	// 댓글 업데이트 
	static String sql_UPDATE = "UPDATE boardreply SET rcontent=? WHERE rid=?";

	// 유저 ID 값 댓글 전체출력
	static String sql_SELECT_ALL_USER = "SELECT rid, bid, usernum, "
			+ "CASE WHEN deleteat='Y' THEN 'unknown' ELSE rwriter END AS rwriter, "
			+ "CASE WHEN deleteat='Y' THEN '*삭제된 댓글입니다.' ELSE rcontent END AS rcontent , "
			+ "rdate, deleteat, parentid FROM ("
			+ "SELECT ROWNUM AS RNUM, boardreply.* FROM ("
			+ "SELECT * FROM boardreply WHERE usernum=? AND deleteat='N' ORDER BY rdate DESC"
			+ ") boardreply WHERE ROWNUM<=? "
			+ ") WHERE RNUM > ? ORDER BY rdate DESC";

	// 댓글 전체출력
	static String sql_SELECT_ALL = "SELECT rid, bid, usernum, "
			+ "CASE WHEN deleteat='Y' THEN 'unknown' ELSE rwriter END AS rwriter, "
			+ "CASE WHEN deleteat='Y' THEN '*삭제된 댓글입니다.' ELSE rcontent END AS rcontent , "
			+ "rdate, deleteat, parentid FROM ("
			+ "SELECT ROWNUM AS RNUM, boardreply.* FROM ("
			+ "SELECT * FROM boardreply WHERE bid=? AND parentid=0 ORDER BY rdate DESC"
			+ ") boardreply WHERE ROWNUM<=? "
			+ ") WHERE RNUM > ? ORDER BY rdate DESC";


	// 댓글 하나 출력
	static String sql_SELECT_ONE = "SELECT rid, bid, usernum, "
			+ "CASE WHEN deleteat='Y' THEN 'unknown' ELSE rwriter END AS rwriter, "
			+ "CASE WHEN deleteat='Y' THEN '*삭제된 댓글입니다.' ELSE rcontent END AS rcontent, "
			+ "rdate, deleteat, parentid FROM boardreply WHERE rid=?";

	//========================================================================================
	// selectAll 댓글 객체 + 대댓글 List가 들어있는 datas 반환

	static int pageSize = 10; // 페이지 10개씩 출력 출력갯수 바꾸실때 여기 바꾸시면 됩니다.

	// userNum 값 없을 때 0넣어주세요
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
			// 전체 출력
			pstmt = conn.prepareStatement(sql_SELECT_ALL);
			pstmt.setInt(1, vo.getbId());
			pstmt.setInt(2, lastNum);
			pstmt.setInt(3, startNum);
			// System.out.println("전체출력 통과");

			System.out.println("start : "+lastNum);
			System.out.println("last : "+startNum);

			ResultSet rs = pstmt.executeQuery();
			// 댓글 객체

			while(rs.next()) {
				// System.out.println("replyDAO.getDBList while안에서 출력");
				rvo = new ReplyVO();

				rvo.setrId(rs.getInt("rid"));            	// 댓글 PK
				rvo.setbId(rs.getInt("bid"));            	// 게시글 번호
				rvo.setUserNum(rs.getInt("usernum"));       // 유저 번호
				rvo.setrWriter(rs.getString("rwriter"));    // 작성자
				rvo.setrContent(rs.getString("rcontent"));  // 댓글 내용
				rvo.setrDate(rs.getDate("rdate"));         	// 작성일
				rvo.setDeleteAt(rs.getString("deleteat"));  // 삭제여부
				rvo.setParentId(rs.getInt("parentid"));   	// 댓글 대댓글 구분

				// System.out.println("댓글 1객체 : "+rvo);

				String rrsql = "SELECT * FROM boardreply WHERE parentid=? ORDER BY rdate DESC";
				pstmt = conn.prepareStatement(rrsql);
				pstmt.setInt(1, rvo.getrId()); // 대댓글의 parent는 댓글의 ID
				ResultSet rrs = pstmt.executeQuery();

				ArrayList<ReplyVO> rrlist = new ArrayList<ReplyVO>();

				while(rrs.next()) {
					ReplyVO rrvo = new ReplyVO();

					rrvo.setrId(rrs.getInt("rid"));            		// 댓글 PK
					rrvo.setbId(rrs.getInt("bid"));            		// 게시글 번호
					rrvo.setUserNum(rrs.getInt("usernum"));     	// 유저 번호
					rrvo.setrWriter(rrs.getString("rwriter"));  	// 작성자
					rrvo.setrContent(rrs.getString("rcontent"));	// 댓글 내용
					rrvo.setrDate(rrs.getDate("rdate"));        	// 작성일
					rrvo.setDeleteAt(rrs.getString("deleteat"));	// 삭제여부
					rrvo.setParentId(rrs.getInt("parentid"));   	// 댓글 대댓글 구분

					// System.out.println("대댓글 writer 확인 : "+rrvo.getrWriter());
					rrlist.add(rrvo);

					// System.out.println("대댓글 list"+rrlist);
				}
				rrs.close();

				ReplySet replySet = new ReplySet();
				replySet.setRvo(rvo); // 댓글 1
				replySet.setRrlist(rrlist); // 댓글에 대한 대댓글
				datas.add(replySet); // 댓글1 + 대댓글 N개

				// System.out.println("replySet : "+replySet);
			}
			rs.close();

			String sql;

			// 댓글 전체 개수 출력
			sql = "SELECT COUNT(*) FROM boardreply WHERE bid=? AND deleteat='N' AND parentid=0"; //  AND DELETE_AT='N' AND PARENT_ID=0 이부분 추가했음!
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
			System.out.println("ReplyDAO getDBList에서 발생");
			e.printStackTrace();
		} finally {
			JDBC.disconnect(pstmt, conn);
		}

		/*for(ReplySet v : datas) {
	         System.out.println("최종 : "+v);
	      }*/
		return datas;
	}
	//========================================================================================	
	@SuppressWarnings("resource")
	public MyReplySet myReply(UsersVO uvo, int pageNum) {
		System.out.println("myReply() 입장");
		System.out.println("userNum : "+ uvo.getUserNum());
		Connection conn = JDBC.getConnection();
		PreparedStatement pstmt = null;
		ArrayList<ReplyVO> rlist = new ArrayList<ReplyVO>();
		MyReplySet datas = new MyReplySet();
		// 총 전체 길이
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
				System.out.println("와일문 입장");
				ReplyVO rvo = new ReplyVO();

				rvo.setrId(rs.getInt("rid"));            	// 댓글 PK
				rvo.setbId(rs.getInt("bid"));            	// 게시글 번호
				rvo.setUserNum(rs.getInt("usernum"));       // 유저 번호
				rvo.setrWriter(rs.getString("rwriter"));    // 작성자
				rvo.setrContent(rs.getString("rcontent"));  // 댓글 내용
				rvo.setrDate(rs.getDate("rdate"));         	// 작성일
				rvo.setDeleteAt(rs.getString("deleteat"));  // 삭제여부
				rvo.setParentId(rs.getInt("parentid"));   	// 댓글 대댓글 구분

				rlist.add(rvo);

				System.out.println("rvo : "+rvo);
			}
			rs.close();
			System.out.println("와일문 통과");
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
			System.out.println("replyDAO myReply에서 발생");
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

				rvo.setrId(rs.getInt("rid"));				// 댓글 PK
				rvo.setbId(rs.getInt("bid"));				// 게시글 번호
				rvo.setUserNum(rs.getInt("usernum"));		// 유저 번호
				rvo.setrContent(rs.getString("rcontent"));	// 댓글 내용
				rvo.setrDate(rs.getDate("rdate"));			// 작성일
				rvo.setDeleteAt(rs.getString("deleteat"));	// 삭제여부
				rvo.setrWriter(rs.getString("rwriter"));	// 작성자
				rvo.setParentId(rs.getInt("parentid"));		// 댓글 대댓글 구분

				ArrayList<ReplyVO> rrlist = new ArrayList<ReplyVO>();

				if(rvo.getParentId() > 0) {
					String sql = "SELECT * FROM boardreply WHERE parentid=?";
					pstmt = conn.prepareStatement(sql);
					pstmt.setInt(1, rvo.getrId()); // 대댓글의 parent는 댓글의 ID
					ResultSet rrs = pstmt.executeQuery();
					// 대댓글

					while (rrs.next()) {
						ReplyVO rrvo = new ReplyVO();

						rrvo.setrId(rrs.getInt("rid"));					// 댓글 PK
						rrvo.setbId(rrs.getInt("bid"));					// 게시글 번호
						rrvo.setUserNum(rrs.getInt("usernum"));			// 유저 번호
						rrvo.setrContent(rrs.getString("rcontent"));	// 댓글 내용
						rrvo.setrDate(rrs.getDate("rdate"));			// 작성일
						rrvo.setDeleteAt(rrs.getString("deleteat"));	// 삭제여부
						rrvo.setrWriter(rrs.getString("rwriter"));		// 작성자
						rrvo.setParentId(rrs.getInt("parentid"));		// 댓글 대댓글 구분
						
						rrlist.add(rrvo);
					}
					rrs.close();
				}
				data.setRvo(rvo);
				data.setRrlist(rrlist);
			}
			rs.close();
			
		} catch (SQLException e) {
			System.out.println("ReplySet getDBDate에서 출력");
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
		// 트렌젝션 확인
		boolean check = false;
		try {
			// (SELECT NVL(MAX(R_ID), 0)+1 FROM BOARD_REPLY),?,?,?,?,?)
			// ? 입력값 : (B_ID, USER_NUM, R_CONTENT, R_WRITER, PARENT_ID)
			conn.setAutoCommit(false);
			String getRidSql = "SELECT NVL(MAX(rid), 0)+1 FROM boardreply";
			pstmt = conn.prepareStatement(getRidSql);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				res = rs.getInt(1);
			}
					

			pstmt = conn.prepareStatement(sql_INSERT);
			pstmt.setInt(1, vo.getbId());			// 보드 ID
			pstmt.setInt(2, vo.getUserNum());		// 유저넘버
			pstmt.setString(3, vo.getrContent());	// 댓글내용
			pstmt.setString(4, vo.getrWriter());	// 작성자
			pstmt.setInt(5, vo.getParentId());		// 댓글 대댓글 구분 부모 값
			pstmt.executeUpdate();

			// 보드에 댓글 카운트 1증가
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
			System.out.println("replyDAO insert에서 발생");
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
		int cnt=0; // 대댓글 개수 확인
		
		// 커밋확인용
		boolean check = false;

		try {
			// 오토 커밋 해제
			conn.setAutoCommit(false);
			// 댓글이라면
			if (vo.getParentId() == 0) {

				sql ="SELECT COUNT(*) FROM boardreply WHERE parentid=?";
				// sql = "SELECT * FROM BOARD_REPLY WHERE PARENT_ID=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, vo.getrId());
				rs = pstmt.executeQuery();
				if(rs.next()) {
					cnt=rs.getInt(1);
				}
				System.out.println("댓글일때 일 때 대댓글 갯수 : "+cnt);

				// 만약에 댓글에 대댓글이 없으면 해당 댓글 삭제
				if (cnt == 0) {
					sql = "DELETE FROM boardreply WHERE rid=?";
					pstmt = conn.prepareStatement(sql);
					pstmt.setInt(1, vo.getrId());
					pstmt.executeUpdate();
					System.out.println("대댓글 없을때 댓글삭제 통과");
				}
				// 대댓글이 있으면 DELETE_AT Y로 수정
				else {
					pstmt = conn.prepareStatement(sql_DELETE);
					pstmt.setInt(1, vo.getrId());
					pstmt.executeUpdate();
					System.out.println("대댓글 있을때 업데이트 통과");
				}
			}
			// 대댓글 이라면
			else {
				sql = "DELETE FROM boardreply WHERE rid=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, vo.getrId());
				pstmt.executeUpdate();
				System.out.println("대댓글 일때 삭제 통과");
				
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
						System.out.println("대댓글 없을때 댓글삭제 통과");
					}
				}
			}

			// 댓글 수 1 제거
			sql = "UPDATE board SET recnt = recnt-1 WHERE bid=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, vo.getbId());
			pstmt.executeUpdate();
			System.out.println("댓글수 차감완료");

			check = true;

			if (check) {
				conn.commit();
				res= true;
				System.out.println("커밋완료");
			}
			else {
				conn.rollback();
				System.out.println("롤백");
			}

		} catch (SQLException e) {
			System.out.println("ReplyDAO delete에서 발생");
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
			System.out.println("ReplyDAO update에서 발생");
			e.printStackTrace();
		} finally {
			JDBC.disconnect(pstmt, conn);
		}
		return res;
	}

	/* 댓글 찾기 */
	
	
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
