package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import action.CategoryListProAction;
import action.CategoryWriteProAction;
import action.OptionListProAction;
import action.OptionWriteProAction;
import action.ProductDeleteProAction;
import action.ProductListProAction;
import action.ProductModifyFormAction;
import action.ProductModifyProAction;
import action.ProductViewProAction;
import action.ProductWriteProAction;
import svc.OptionWriteService;
import svc.ProductWriteService;
import vo.ActionForward;

@WebServlet("*.admin")
public class AdminFrontController extends HttpServlet {

	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		String command = request.getServletPath();
		
		Action action = null;
		ActionForward forward = null;
		
		ProductWriteService productWriteService = new ProductWriteService();
		OptionWriteService optionWriteService = new OptionWriteService();
		
		if(command.equals("/home.admin")) { // 관리자 페이지 메인화면
			forward = new ActionForward();
			forward.setPath("/admin/adminHome.jsp");
		}else if(command.equals("/CategoryWriteForm.admin")) {
			// 글쓰기 페이지 요청은 비즈니스 로직이 없는 View 페이지(JSP)로 바로 연결 수행
			// => ActionForward 객체의 포워딩 방식을 별도로 설정하지 않음 => dispatcher 방식...
			forward = new ActionForward();
//			forward.setRedirect(false); 기본값이라서 생략 가능함
			forward.setPath("/admin/registCategory.jsp");
		}else if(command.equals("/CategoryWritePro.admin")) {
			action = new CategoryWriteProAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/CategoryList.admin")) {
			action = new CategoryListProAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/ProductWriteForm.admin")) { // 상품 등록 페이지
			forward = new ActionForward();
			
			String categorySelectList = productWriteService.categorySelectList();
			request.setAttribute("categorySelectList", categorySelectList);
			String optionSelectList = productWriteService.optionSelectList();
			request.setAttribute("optionSelectList", optionSelectList);
			
			forward.setPath("/admin/registProduct.jsp");
			
		}else if(command.equals("/ProductWritePro.admin")) { // 상품 등록 수행
			action = new ProductWriteProAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/ProductList.admin")) { // 상품 리스트 페이지
			action = new ProductListProAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/ProductView.admin")) { // 상품 보기 페이지
			action = new ProductViewProAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/ProductModifyForm.admin")) { // 상품 수정 페이지 보기
			action = new ProductModifyFormAction();
			
			String categorySelectList = productWriteService.categorySelectList();
			request.setAttribute("categorySelectList", categorySelectList);
			String optionSelectList = productWriteService.optionSelectList();
			request.setAttribute("optionSelectList", optionSelectList);
			
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/ProductModifyPro.admin")) { // 상품 수정 페이지 수행
			action = new ProductModifyProAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/ProductDeletePro.admin")) { // 상품 삭제 수행
			action = new ProductDeleteProAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/OptionWriteForm.admin")) { // 옵션 등록 페이지
			String productSelectList = optionWriteService.productSelectList();
			request.setAttribute("productSelectList", productSelectList);
			forward = new ActionForward();
			forward.setPath("/admin/registOption.jsp");
			
		}else if(command.equals("/OptionWritePro.admin")) { // 옵션 등록 하기
			action = new OptionWriteProAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/OptionList.admin")) { // 옵션 리스트 페이지
			action = new OptionListProAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		
		
		
		// ActionForward 객체 내의 포워딩 방식에 따라 각각의 포워딩 작업 수행
		if(forward != null) {
			
			if(forward.isRedirect()) { // redirect 방식
				response.sendRedirect(forward.getPath());
			}else { // dispatcher 방식
				RequestDispatcher dispatcher = request.getRequestDispatcher(forward.getPath());
				dispatcher.forward(request, response);
			}
			
		}
		
	}	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

}