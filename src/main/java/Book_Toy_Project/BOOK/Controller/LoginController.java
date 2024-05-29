package Book_Toy_Project.BOOK.Controller;

import Book_Toy_Project.BOOK.Cookie_Session.SessionConst;
import Book_Toy_Project.BOOK.Entity.Member;
import Book_Toy_Project.BOOK.Form.LoginForm;
import Book_Toy_Project.BOOK.Repository.MemberRepository;
import Book_Toy_Project.BOOK.Service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@Slf4j //로그
@RequiredArgsConstructor
public class LoginController {

    private final MemberRepository memberRepository;
    private final MemberService memberService;


    @GetMapping("/")
    public String home(Model model) {
        log.info("home Controller");
        model.addAttribute("loginForm", new LoginForm());
        return "login";
    }

    @GetMapping("/login")
    public String startHomePage(@SessionAttribute(
            name = SessionConst.LOGIN_MEMBER, required = false)
                                Member loginMember, Model model) {
        log.info("startHomePage");
        //세션에 회원 데이터가 없으면 home
        if (loginMember == null) {
            return "/login";
        }

        //세션이 유지되면 로그인으로 이동
        model.addAttribute("member", loginMember);
        return "home/home";
    }

    @PostMapping("/login")
    public String postHomePage(@Valid LoginForm loginForm, BindingResult result, HttpServletRequest request, Model model) {
        if (result.hasErrors()) {
            log.error("로그인 오류 : 회원정보를 다시 확인해주세요.");
            return "login";
        }

        Member findMember = memberRepository.findByEmail(loginForm.getEmail());
        if (findMember == null) {
            log.error("로그인 실패 : 존재하지 않는 이메일입니다.");
            model.addAttribute("loginError", "로그인 실패, 아이디 또는 비밀번호를 다시 확인해주세요.");
            return "login";
        }

        //Validation 검증
        //검증 실패시
        if (!memberService.authenticateUser(loginForm.getEmail(), loginForm.getPassword())) {
            log.error("로그인 실패 : 아이디 또는 비밀번호가 일치하지 않습니다.");
            model.addAttribute("loginError", "로그인 실패, 아이디 또는 비밀번호를 다시 확인해주세요.");
            return "login";
        }

        //검증 성공
        log.info("로그인 성공");

        //세션 처리 및 저장
        memberService.processLoginSuccess(request,findMember);

        log.info("홈 화면으로 이동합니다");

        return "home/home";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        //세션을 삭제한다.
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "login";
    }
}
