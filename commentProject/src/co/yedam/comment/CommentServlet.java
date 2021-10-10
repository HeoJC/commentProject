package co.yedam.comment;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@WebServlet("/CommentServlet")
public class CommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public CommentServlet() {
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8") ;
		response.setCharacterEncoding("UTF-8") ;
		response.setContentType("text/html; charset=UTF-8") ; // 한글이 ?? 로 깨져서 나오는거 나오게 하려는거
		
		PrintWriter out = response.getWriter() ;
		String cmd = request.getParameter("cmd") ;
		CommentDAO dao = CommentDAO.genInstance() ;
		Gson gson = new GsonBuilder().create() ; 	// 데이터베이스에서 불러온 자료를 JSON 형태로 바꾸면 쓰기 좋은데
		
		if (cmd == null) {
			out.println("<h1>빈페이지입니다</h1>") ;
			
		} else if (cmd.equals("list")) {
			System.out.println("<h1>리스트페이지입니다</h1>") ;
			List<Comment> list = dao.getCommenList() ;
			out.println(gson.toJson(list)) ;		 	// helloWorld에서 했던 방식은 직접 JSON형태로 바꿔주는 과정을 거쳤었는데 
													 	// GSON을 쓰면 GSON이 그걸 알아서 처리해줌
		} else if (cmd.equals("add")) {
			System.out.println("<h1>추가페이지입니다</h1>") ;
			String name = request.getParameter("name") ;
			String content = request.getParameter("content") ;
			
			Comment comment = new Comment() ;
			comment.setName(name) ;
			comment.setContent(content) ;
			
			dao.insertComment(comment) ;
			
			System.out.println(comment) ;
			
			out.println(gson.toJson(comment));
			
		} else if (cmd.equals("mod")) {
			System.out.println("<h1>수정페이지입니다</h1>") ;
			String id = request.getParameter("id") ;
			String name = request.getParameter("name") ;
			String content = request.getParameter("content") ;
			
			Comment comment = new Comment() ;
			comment.setId(id) ;
			comment.setName(name) ;
			comment.setContent(content) ;
			
			dao.updateComment(comment) ;
			
			out.println(gson.toJson(comment)) ;
			
		} else if (cmd.equals("del")) {
			System.out.println("<h1>삭제페이지입니다</h1>") ;
			String id = request.getParameter("id") ;
			
			if(dao.deleteComment(id) == null) {
				// {"retCode":"fail"}
				out.println("{\"retCode\":\"fail\"}") ;
				return ;
			}
			out.println("{\"retCode\":\"success\"}") ;
		} else {
			out.println("<h1>" + cmd + "</h1>") ;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
