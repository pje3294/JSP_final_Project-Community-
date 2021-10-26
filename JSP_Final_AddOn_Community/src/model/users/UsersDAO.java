package model.users;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.common.JDBC;

public class UsersDAO {

	
	
	final static String defaultImg = "default.png";
	
	
	// getDBList 
	public ArrayList<UsersVO> getDBList() {
		Connection conn = JDBC.getConnection();
		ArrayList<UsersVO> datas = new ArrayList<UsersVO>();
		PreparedStatement pstmt = null;

		String sql_SELECT_ALL = "SELECT * FROM users ORDER BY usernum DESC";
		// �ֽ� ȸ�������� ������� ������ 

		try {
			
			pstmt = conn.prepareStatement(sql_SELECT_ALL);
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
				UsersVO data = new UsersVO();
				
				data.setAddr(rs.getString("addr"));
				data.setBirth(rs.getString("birth"));
				data.setEmail(rs.getString("email"));
				data.setGender(rs.getString("gender"));
				data.setIconId(rs.getString("iconid"));
				data.setId(rs.getString("id"));
				data.setName(rs.getString("name"));
				data.setPhone(rs.getNString("phone"));
				data.setPw(rs.getString("pw"));
				data.setUserNum(rs.getInt("usernum"));
				
				datas.add(data);
			}
			
			rs.close();
			
		} catch (SQLException e) {
			System.out.println("UsersDAO-getDBList ���� �α�");
			e.printStackTrace();
		} finally {
			JDBC.disconnect(pstmt, conn);
		}
		return datas;

	}
	
	//=====================================================================================
	// getDBData
	  public UsersVO getDBData(UsersVO vo) {
	      Connection conn = JDBC.getConnection();
	      UsersVO data = null;
	      PreparedStatement pstmt = null;
	      String sql_SELECT_ONE;

	      try {

	         if (vo.getId() == null) { // == UserNum�� �Ѿ�� ��� 
	            sql_SELECT_ONE = "SELECT * FROM users WHERE usernum=?";
	            pstmt = conn.prepareStatement(sql_SELECT_ONE);
	            pstmt.setInt(1, vo.getUserNum());
	            //System.out.println("USER_NUM getDBData() ���");
	            
	         } else {  // == ID�� �Ѿ�� ��� 
	            sql_SELECT_ONE = "SELECT * FROM users WHERE id=?";
	            pstmt = conn.prepareStatement(sql_SELECT_ONE);
	            pstmt.setString(1, vo.getId());
	            //System.out.println("USER_ID getDBData() ���");
	         }

	         ResultSet rs = pstmt.executeQuery();

	         while (rs.next()) {
	            //System.out.println("while�� ����");
	            data = new UsersVO();
	            
	            data.setAddr(rs.getString("addr"));
				data.setBirth(rs.getString("birth"));
				data.setEmail(rs.getString("email"));
				data.setGender(rs.getString("gender"));
				data.setIconId(rs.getString("iconid"));
				data.setId(rs.getString("id"));
				data.setName(rs.getString("name"));
				data.setPhone(rs.getNString("phone"));
				data.setPw(rs.getString("pw"));
				data.setUserNum(rs.getInt("usernum"));
	            
	         }
	         rs.close();

	      } catch (SQLException e) {
	         System.out.println("UsersDAO-getDBData �����α�");
	         e.printStackTrace();
	      } finally {
	         JDBC.disconnect(pstmt, conn);
	      }
	      return data;

	   }

	//=====================================================================================
	// insert
	public boolean insert(UsersVO vo) {
		Connection conn = JDBC.getConnection();
		PreparedStatement pstmt = null;

		boolean res = false;

		// insert into USERS values((select NVL(MAX(user_num),0)+1 from users), '������',
		// 'je','111','01011111111','F','je@gmail.com','�ּ�','����','1');
		String sql_INSERT = "INSERT INTO users VALUES ((SELECT NVL(MAX(usernum),0)+1 FROM users),?,?,?,?,?,?,?,?,?)";
		try {
			pstmt = conn.prepareStatement(sql_INSERT);
			
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getId());
			pstmt.setString(3, vo.getPw());
			pstmt.setString(4, vo.getPhone());
			pstmt.setString(5, vo.getGender());
			pstmt.setString(6, vo.getEmail());
			pstmt.setString(7, vo.getAddr());
			pstmt.setString(8, vo.getBirth());
			pstmt.setString(9, vo.getIconId());
			
			pstmt.executeUpdate();
			res = true;
		} catch (SQLException e) {
			System.out.println("UsersDAO-insert �����α�");
			e.printStackTrace();
		} finally {
			JDBC.disconnect(pstmt, conn);
		}
		return res;
	}
	//=====================================================================================
	// update
	public boolean update(UsersVO vo) {
		Connection conn = JDBC.getConnection();
		PreparedStatement pstmt = null;

		boolean res = false;

		String sql_UPDATE = "UPDATE users SET name=?, pw=?, phone=?, gender=?, "
				+ "email=?, addr=?, birth=?, iconid=? WHERE usernum=?";
		try {
			System.out.println("UserDAO Update �Լ� try �� ��");
			pstmt = conn.prepareStatement(sql_UPDATE);
			
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getPw());
			pstmt.setString(3, vo.getPhone());
			pstmt.setString(4, vo.getGender());
			pstmt.setString(5, vo.getEmail());
			pstmt.setString(6, vo.getAddr());
			pstmt.setString(7, vo.getBirth());
			pstmt.setString(8, vo.getIconId());
			pstmt.setInt(9, vo.getUserNum());
			
			pstmt.executeUpdate();
			res = true;
		} catch (SQLException e) {
			System.out.println("UsersDAO-update �����α�");
			e.printStackTrace();
		} finally {
			JDBC.disconnect(pstmt, conn);
		}
		return res;
	}
	//=====================================================================================
	// delete
	public boolean delete(UsersVO vo) {
		Connection conn = JDBC.getConnection();
		PreparedStatement pstmt = null;

		boolean res = false;

		String sql_DELETE = "DELETE FROM users WHERE usernum=?";
		
		try {
			pstmt = conn.prepareStatement(sql_DELETE);
			pstmt.setInt(1, vo.getUserNum());
			
			pstmt.executeUpdate();
			res = true;
		} catch (SQLException e) {
			System.out.println("UsersDAO-delete �����α�");
			e.printStackTrace();
		} finally {
			JDBC.disconnect(pstmt, conn);
		}
		return res;

	}
	//=====================================================================================
	// login
	public boolean login(UsersVO vo) {
		Connection conn = JDBC.getConnection();
		UsersVO data = null;
		PreparedStatement pstmt = null;
		
		String sql_LOGIN = "SELECT id, pw FROM users WHERE id=?";
		boolean res = false;
		
		try {
			pstmt = conn.prepareStatement(sql_LOGIN);
			pstmt.setString(1, vo.getId());
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {   // rs.next() ���� ���� ��� ���� �߻��Ͽ� �������� 
				System.out.println(rs.getString("pw"));
				if (rs.getString("pw").equals(vo.getPw())) {
					res = true;
				}
			}
		} catch (SQLException e) {
			System.out.println("UserDAO-login ���� �α�");
			e.printStackTrace();
		} finally {
			JDBC.disconnect(pstmt, conn);
		}
		return res;
	}
	
	//=====================================================================================
	// ���� ������ ���� ����
	public boolean deleteImg(UsersVO vo) {
		Connection conn = JDBC.getConnection();
		PreparedStatement pstmt = null;
		
		boolean res = false;
		
		String sql_DELETE = "UPDATE USERS SET iconid=? WHERE usernum=?";
		
		try {
			System.out.println(sql_DELETE);
			pstmt = conn.prepareStatement(sql_DELETE);
			pstmt.setString(1, defaultImg);
			pstmt.setInt(2, vo.getUserNum());
			pstmt.executeUpdate();
			res = true;
			
		} catch (SQLException e) {
			System.out.println("UsersDAO-delete �����α�");
			e.printStackTrace();
		} finally {
			JDBC.disconnect(pstmt, conn);
		}
		return res;
	}
	//=====================================================================================
		// insert
		public boolean uploadImg(UsersVO vo) {
			Connection conn = JDBC.getConnection();
			PreparedStatement pstmt = null;

			boolean res = false;

			String sql_UPLOAD = "UPDATE users SET iconid=? WHERE usernum=?";
			try {
				pstmt = conn.prepareStatement(sql_UPLOAD);
				
				pstmt.setString(1, vo.getIconId());
				pstmt.setInt(2, vo.getUserNum());
				
				pstmt.executeUpdate();
				res = true;
				
			} catch (SQLException e) {
				System.out.println("UsersDAO-insert �����α�");
				e.printStackTrace();
			} finally {
				JDBC.disconnect(pstmt, conn);
			}
			return res;
		}
//------------------------------------------------------------------------
		// �̸��� ã���ִ� �޼��� 
		public ArrayList<String> getEmails(){
			Connection conn = JDBC.getConnection();
			PreparedStatement pstmt = null;
			ArrayList<String> emails = new ArrayList<String>();
			try {
				String sql = "select email from users";
				pstmt = conn.prepareStatement(sql);
				
				ResultSet rs = pstmt.executeQuery();
				
				while (rs.next()) {
					emails.add(rs.getString(1));
					System.out.println("UsersDAO getEmails rs.getString : "+ rs.getString(1));
				}
				
			}catch(SQLException e) {
				e.printStackTrace();
			}finally {
				JDBC.disconnect(pstmt, conn);
			}
			
			
			return emails;
			
			
		}
	
	
}
