package svc;

import static db.jdbcUtil.*;

import java.sql.Connection;

import dao.MemberDAO;
import vo.MemberBean;
public class MemberBeanService {

	public MemberBean myName(String id) {
	
	Connection con = getConnection();
	MemberDAO dao = MemberDAO.getInstance();
	dao.setConnection(con);
	
	
	MemberBean bb = dao.myName(id);
	
	return bb;
	}
}
