
package dao;

import static db.jdbcUtil.close;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vo.ProductBean;
import vo.QuestionBean;

public class ItemDAO {
	
	private ItemDAO() {}
	
	private static ItemDAO instance;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	public static ItemDAO getInstance() {
		// ItemDAO 객체가 없을 경우에만 생성
		if(instance == null) {
			instance = new ItemDAO();
		}
		return instance;
	}
	
	private Connection con;
	
	public void setConnection(Connection con) {
		this.con = con;
	}
	

	public ArrayList<ProductBean> selectMajorLink(String majorCategory) {
		String sql = "select * from product where category_code like ?";
		ProductBean productBean = null;
		ArrayList<ProductBean> listProduct = new ArrayList<ProductBean>();
		try {
			pstmt = con.prepareStatement(sql);
		
			pstmt.setString(1, majorCategory+"%");
			rs = pstmt.executeQuery();
			while(rs.next()) {
				productBean = new ProductBean();
				productBean.setProduct_num(rs.getInt("num"));
				productBean.setProduct_code(rs.getString("code"));
				productBean.setProduct_name(rs.getString("name"));
				productBean.setProduct_image(rs.getString("image"));
				productBean.setProduct_image2(rs.getString("image2"));
				productBean.setProduct_description(rs.getString("description"));
				productBean.setProduct_price(rs.getInt("price"));
				productBean.setProduct_brand(rs.getString("brand"));
				productBean.setProduct_stock_count(rs.getInt("stock_count"));
				productBean.setProduct_sale_price(rs.getInt("sale_price"));
				productBean.setProduct_keywords(rs.getString("keywords"));
				productBean.setProduct_regdate(rs.getTimestamp("regdate"));
				productBean.setProduct_category_code(rs.getString("category_code"));
				productBean.setProduct_option_code(rs.getString("option_code"));
				listProduct.add(productBean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
			close(con);
		}
		System.out.println(listProduct);
		return 	listProduct;
		
	}	
	
	
	
	public ArrayList<ProductBean> selectMinorLink(String minorCategory) {
		String sql = "select * from product where category_code=?";
		ProductBean productBean = null;
		ArrayList<ProductBean> listProduct = new ArrayList<ProductBean>();
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, minorCategory);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				productBean = new ProductBean();
				productBean.setProduct_num(rs.getInt("num"));
				productBean.setProduct_code(rs.getString("code"));
				productBean.setProduct_name(rs.getString("name"));
				productBean.setProduct_image(rs.getString("image"));
				productBean.setProduct_image2(rs.getString("image2"));
				productBean.setProduct_description(rs.getString("description"));
				productBean.setProduct_price(rs.getInt("price"));
				productBean.setProduct_brand(rs.getString("brand"));
				productBean.setProduct_stock_count(rs.getInt("stock_count"));
				productBean.setProduct_sale_price(rs.getInt("sale_price"));
				productBean.setProduct_keywords(rs.getString("keywords"));
				productBean.setProduct_regdate(rs.getTimestamp("regdate"));
				productBean.setProduct_category_code(rs.getString("category_code"));
				productBean.setProduct_option_code(rs.getString("option_code"));
				listProduct.add(productBean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
			close(con);
		}
		return 	listProduct;
		
	}


	public int getMajorCount(String majorCategory) {
		int count = 0;
		String sql = "select count(num) from product where category_code=?";
		System.out.println(con);
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, majorCategory);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				count = rs.getInt("count(num)");
			}
				
			}catch(SQLException e){
				e.printStackTrace();
			}finally {
//				close(rs);
//				close(pstmt);
//				close(con);
			}
		
		return count;
	}


	public int getMinorCount(String minorCategory) {
		int count = 0;
		String sql = "select count(num) from product where category_code like '?%'";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, minorCategory);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				count = rs.getInt("num");
			}
				
			}catch(SQLException e){
				e.printStackTrace();
			}finally {
				close(rs);
				close(pstmt);
				close(con);
			}
		
		return count;
	}	
	
	public int insertQuestion(QuestionBean questionBean) {
		int insertCount = 0;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			int num = 1;
			
			String sql = "SELECT MAX(num) FROM question";
			
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				num = rs.getInt(1) + 1;
			} 
			
			sql = "INSERT INTO question(num,email,field,title,content,regdate,member_id,product_num) VALUES (?,?,?,?,?,now(),?,?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num); // 계산된 새 글 번호 사용
			pstmt.setString(2, questionBean.getQuestion_email());
			pstmt.setString(3, questionBean.getQuestion_field());
			pstmt.setString(4, questionBean.getQuestion_title());
			pstmt.setString(5, questionBean.getQuestion_content());
			pstmt.setString(6, questionBean.getQuestion_member_id());
			pstmt.setInt(7, questionBean.getQuestion_product_num());
			
			// INSERT 구문 실행 후 리턴되는 결과값을 insertCount 변수에 저장
			insertCount = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("ItemDAO - insertQuestion() 실패! : " + e.getMessage());
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return insertCount;
	}


	public ArrayList<QuestionBean> selectQuestionList(int product_num, int q_startRow, int q_pageSize) {
		System.out.println("selectQuestionList");
		ArrayList<QuestionBean> questionList = new ArrayList<QuestionBean>();

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "SELECT * FROM question WHERE product_num=? ORDER BY num desc LIMIT ?,?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, product_num);
			pstmt.setInt(2, q_startRow);
			pstmt.setInt(3, q_pageSize);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				QuestionBean article = new QuestionBean();
				article.setQuestion_title(rs.getString("title"));
				article.setQuestion_content(rs.getString("content"));
				article.setQuestion_answer(rs.getString("answer"));
				article.setQuestion_member_id(rs.getString("member_id"));
				article.setQuestion_regdate(rs.getTimestamp("regdate"));
				questionList.add(article);
			}
		} catch (SQLException e) {
			System.out.println("ItemDAO - selectQuestionList() 실패! : " + e.getMessage());
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return questionList;
	}


	public int getQuestionCount(int product_num) {
		System.out.println("getQuestionCount");
		int count = 0;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "SELECT count(num) FROM question WHERE product_num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, product_num);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				count = rs.getInt("count(num)");
			}
		} catch (SQLException e) {
			System.out.println("ItemDAO - getQuestionCount() 실패! : " + e.getMessage());
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return count;
	}	

}
