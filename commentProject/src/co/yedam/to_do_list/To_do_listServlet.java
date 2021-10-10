package co.yedam.to_do_list;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@WebServlet("/To_do_listServlet")
public class To_do_listServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    public To_do_listServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8") ;
		response.setCharacterEncoding("UTF-8") ;
		response.setContentType("text/html; charset=UTF-8") ;
		
		PrintWriter out = response.getWriter() ;
		String cmd = request.getParameter("cmd") ;
		To_do_listDAO dao = new To_do_listDAO() ;
		Gson gson = new GsonBuilder().create() ;
		
		if (cmd.equals("add")) { 
		String dotext = request.getParameter("dotext") ;
		
		To_do_list to_do_list = new To_do_list() ;
		to_do_list.setDotext(dotext) ;
		
		dao.insertToDo(to_do_list) ;
		
		out.println(gson.toJson(to_do_list)) ;
		} else if (cmd.equals("del")) {
			String id = request.getParameter("id") ;
			System.out.println(id) ;
			if(dao.deleteToDo(id) == null) {
				// {"retCode":"fail"}
				out.println("{\"retCode\":\"fail\"}");
				return ;
			}
			out.println("{\"retCode\":\"success\"}");
		} else {
			out.println("<h1>" + cmd + "</h1>") ;
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
