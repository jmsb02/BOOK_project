package Book_Toy_Project.BOOK.Controller;

import Book_Toy_Project.BOOK.Entity.Member;
import Book_Toy_Project.BOOK.Exception.DuplicateMemberException;
import Book_Toy_Project.BOOK.Form.MemberForm;
import Book_Toy_Project.BOOK.Service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/signup")
    public String createForm(Model model) {
        log.info("회원 가입 폼을 표시합니다.");
        model.addAttribute("memberForm", new MemberForm());
        return "signup";
    }

    @PostMapping("/signup")
    public String create(@Valid MemberForm form, BindingResult result) {
        if (result.hasErrors()) {
            log.error("회원가입 실패: 유효성 검사 오류가 발생했습니다.");
            return "signup";
        }

        String hashedPassword = passwordEncoder.encode(form.getPassword());

        Member member = memberService.getMember(form, hashedPassword);

        try {
            memberService.join(member);
            log.info("회원가입 성공: {}", member.getName());
            return "redirect:/";
        } catch (DuplicateMemberException e) {
            log.error("회원가입 실패 : 이미 존재하는 회원입니다.");
            result.rejectValue("email", "duplicate.email", "이미 존재하는 회원입니다.");
            return "signup";
        }
    }

}