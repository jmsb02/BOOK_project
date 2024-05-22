package Book_Toy_Project.BOOK.Cookie_Session;

import Book_Toy_Project.BOOK.Entity.Member;
import jakarta.servlet.http.Cookie;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.junit.Assert.*;

@SpringBootTest
public class SessionManagerTest {
    SessionManager sessionManager = new SessionManager();

    @Test
    public void sessionTest() {

        //세션 생성
        MockHttpServletResponse response = new MockHttpServletResponse();
        Member member = new Member();
        sessionManager.createSession(member,response);

        //요청에 응답 쿠키 저장
        MockHttpServletRequest request = new MockHttpServletRequest();
        // 생성된 쿠키를 가져와서 쿠키 배열에 추가
        Cookie[] cookies = new Cookie[]{response.getCookie(SessionManager.SESSION_COOKIE_NAME)};
        // 요청 객체에 쿠키 배열 설정
        request.setCookies(cookies);

        //세션 조회
        Object result = sessionManager.getSession(request);
        Assertions.assertThat(result).isEqualTo(member);

        //세션 만료
        sessionManager.expire(request,response);
        Object expired = sessionManager.getSession(request);
        Assertions.assertThat(expired).isNull();


    }
}