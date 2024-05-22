package Book_Toy_Project.BOOK.Service;

import Book_Toy_Project.BOOK.Entity.Member;
import Book_Toy_Project.BOOK.Exception.UsernameAlreadyExistsException;
import Book_Toy_Project.BOOK.Form.MemberForm;
import Book_Toy_Project.BOOK.Repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service //비즈니스 로직
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    //회원 가입
    public Long join(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    //회원가입 중복 검증 -> 기존 회원 이름이 있으면 오류 터트림
    public void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new UsernameAlreadyExistsException("이미 존재하는 회원입니다.");
    }
    }
    
    //로그인 인증 (비밀번호 해쉬코드 처리)
    public boolean authenticateUser(String email, String password) {
        Member member = memberRepository.findByEmail(email);
        if (member != null) {
            return passwordEncoder.matches(password,member.getPassword());
        }
        return false;
    }

    public Member getMember(MemberForm form, String hashedPassword) {
        Member member = new Member();
        member.setName(form.getName());
        member.setEmail(form.getEmail());
        member.setPassword(hashedPassword);
        member.setAddress(form.getAddress());
        member.setAddress2(form.getAddress2());
        return member;
    }
}
