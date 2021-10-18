package co.yedam.coolsms.sms;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import co.yedam.coolsms.Coolsms;

@WebServlet("/SendSMSServlet")
public class SendSMSServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public SendSMSServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8") ;
		response.setCharacterEncoding("UTF-8") ;
		response.setContentType("text/plain;charset=UTF-8") ;
		
		String receipt = request.getParameter("receipt") ;
		String content = request.getParameter("content") ;
		
		String api_key = "NCS17OHYYNNHJASX";
		String api_secret = "IK5DKSC9MVC1PUXOW0Z1SJYBBYXX5SAY";
		Coolsms coolsms = new Coolsms(api_key, api_secret);
	
		/*
		 * Parameters
		 * 관련정보 : http://www.coolsms.co.kr/SDK_Java_API_Reference_ko#toc-0
		 */
		HashMap<String, String> set = new HashMap<String, String>();
		set.put("to", receipt); // 수신번호

		// 10월 16일 이후로 발신번호 사전등록제로 인해 등록된 발신번호로만 문자를 보내실 수 있습니다.
		set.put("from", "01054946808"); // 발신번호
		set.put("text", content); // 문자내용
		set.put("type", "sms"); // 문자 타입

		/*
		 * Option Parameters
		 */
		/*
		set.put("to", "01000000000, 01000000001"); // 받는사람 번호 여러개 입력시
		set.put("image_path", "./images/"); // image file path 이미지 파일 경로 설정 (기본 "./")
		set.put("image", "test.jpg"); // image file (지원형식 : 200KB 이하의 JPEG)
		set.put("refname", "참조내용"); // 참조내용
		set.put("country", "KR"); // 국가코드 한국:KR 일본:JP 미국:US 중국:CN
		set.put("datetime", "201401151230"); // 예약전송시 날짜 설정		
		set.put("subject", "제목"); // LMS, MMS 일때 제목		
		set.put("charset", "utf8"); // 인코딩 방식
		set.put("srk", ""); // 솔루션 제공 수수료를 정산받을 솔루션 등록키
		set.put("mode", "test"); // test모드 수신번호를 반드시 01000000000 으로 테스트하세요. 예약필드 datetime는 무시됨. 결과값은 60. 잔액에서 실제 차감되며 다음날 새벽에 재충전됨
		set.put("app_version", ""); // 어플리케이션 버젼 예) Purplebook 4.1
		set.put("datetime", "201701151230"); // 예약전송시 날짜 설정		
		*/

		JSONObject result = coolsms.send(set); // 보내기&전송결과받기
		if ((Boolean) result.get("status") == true) {
			// 메시지 보내기 성공 및 전송결과 출력
			System.out.println("성공");			
			System.out.println(result.get("group_id")); // 그룹아이디
			System.out.println(result.get("result_code")); // 결과코드
			System.out.println(result.get("result_message"));  // 결과메시지
			System.out.println(result.get("success_count")); // 성공갯수
			System.out.println(result.get("error_count"));  // 발송실패 메시지 수
			
			response.getWriter().println("성공") ;
		} else {
			// 메시지 보내기 실패
			System.out.println("실패");
			System.out.println(result.get("code")); // REST API 에러코드
			System.out.println(result.get("message")); // 에러메시지
			
			response.getWriter().println("실패") ;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
