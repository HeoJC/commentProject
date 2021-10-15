package co.yedam.apiData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SampleDataServlet2")
public class SampleDataServlet2 extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public SampleDataServlet2() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8") ;
		response.setContentType("text/xml;charset=UTF-8") ;
		response.setCharacterEncoding("UTF-8") ; 
		
		String nor = request.getParameter("numberOfRow") ;
		String pn = request.getParameter("pageNo") ;
		String sd = request.getParameter("startDate") ;
		String ed = request.getParameter("endDate") ;
		
		String hostUrl = "https://api.odcloud.kr/api/15077586/v1/centers?page=1&perPage=100&returnType=JSON&serviceKey=0CI0dctV8C7Fe39hIGh3QKnXyvK5s5rcg7vaHwUhtQYLMBzDFwxwVXkOgUowvEJDBGAdWMgI1cMQIHNx%2Bfmcww%3D%3D" ;
		hostUrl += "&pageNo=" + pn ;
		hostUrl += "&numOfRows=" + nor ;
		hostUrl += "&startCreateDt=" + sd ;
		hostUrl += "&endCreateDt=" + ed ;
		
		URL url = new URL(hostUrl) ;
		HttpURLConnection urlconn = (HttpURLConnection) url.openConnection() ;
		urlconn.setRequestMethod("GET") ;
		BufferedReader br = new BufferedReader(new InputStreamReader(urlconn.getInputStream() , "UTF-8")) ;
		String result = "" , line = "" ;
		while((line = br.readLine()) != null) {
			result += line + "\n" ;
		};
		
		response.getWriter().println(result) ;
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
