package svc;

import vo.EventBean;
import vo.ProductBean;

import static db.jdbcUtil.*;

import java.sql.Connection;
import java.util.ArrayList;

import dao.EventDAO;


public class EventService {

	// 이벤트 등록
	public boolean registEvent(EventBean eventBean) {
		boolean writeSuccess = false;

		Connection con = getConnection();
		
		EventDAO eventDAO = EventDAO.getInstance();
		eventDAO.setConnection(con);
		
		int isSuccess = eventDAO.registEvent(eventBean);
		
		if(isSuccess>0) {
			writeSuccess = true;
			commit(con);
		}else {
			rollback(con);
		}
		
		
		return writeSuccess;
	}
	
	// 이벤트 게시물 카운트
	public int getListCount() {
		System.out.println("EventService - getListCount");
		int listCount = 0;
		
		Connection con = getConnection();
		EventDAO eventDAO = EventDAO.getInstance();
		eventDAO.setConnection(con);
		
		listCount = eventDAO.selectListCount(); // create함
		
		close(con);
		
		return listCount;
	}

	// 이벤트 리스트 가져오기
	public ArrayList<EventBean> getArticleList(int page, int limit) {
		
		ArrayList<EventBean> articleList = null;
		
		Connection con = getConnection();
		EventDAO eventDAO = EventDAO.getInstance();
		eventDAO.setConnection(con);
		
		articleList = eventDAO.selectArticleList(page,limit);
		
		close(con);
		
		return articleList;
	}
	
	// 클릭한 이벤트
		public EventBean getEventArticle(int eBoard_Num) {
			System.out.println("EventService - getEventArticle : "+eBoard_Num);
			
			Connection con = getConnection();
			EventDAO eventDAO = EventDAO.getInstance();
			eventDAO.setConnection(con);
			
			EventBean article = eventDAO.selectEventArticle(eBoard_Num);
			
			close(con);
			
			return article;
		}
	
	
	// 클릭한 이벤트 카테고리 상품 카운트
	public int selectEventItemListCount(String condition) {
		System.out.println("EventService - getEventViewListCount"+condition);
		int listCount = 0;
		
		Connection con = getConnection();
		EventDAO eventDAO = EventDAO.getInstance();
		eventDAO.setConnection(con);
		
		listCount = eventDAO.selectEventItemListCount(condition); // create함
		
		close(con);
		
		return listCount;
	}

	// 클릭한 이벤트 카테고리 상품
	public ArrayList<ProductBean> getEventItemList(int page, int limit,String condition) {
		ArrayList<ProductBean> articleList = null;
		
		Connection con = getConnection();
		EventDAO eventDAO = EventDAO.getInstance();
		eventDAO.setConnection(con);
		
		articleList = eventDAO.selectEventItemList(page,limit,condition);
		
		close(con);
		
		return articleList;
	}

	// 이벤트 수정
	public boolean updateEvent(EventBean eventBean) {
		boolean modifySuccess = false;

		Connection con = getConnection();
		
		EventDAO eventDAO = EventDAO.getInstance();
		eventDAO.setConnection(con);
		
		int isSuccess = eventDAO.modifyEvent(eventBean);
		
		if(isSuccess>0) {
			modifySuccess = true;
			commit(con);
		}else {
			rollback(con);
		}
		
		
		return modifySuccess;
	}



	
	
	
	

}
