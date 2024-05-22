package Book_Toy_Project.BOOK.Service;

import Book_Toy_Project.BOOK.Entity.Member;
import Book_Toy_Project.BOOK.Exception.UsernameAlreadyExistsException;
import Book_Toy_Project.BOOK.Repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberService memberService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void join()  {

        //given
        Member member = new Member("hello");

        //when
        memberService.validateDuplicateMember(member);
        Long saveId = memberService.join(member);
        Member findMember = memberRepository.findById(saveId).get();

        //then
        assertEquals(member,findMember);
    }

    @Test(expected = UsernameAlreadyExistsException.class)
    public void validate_member() {

        //given
        Member member1 = new Member("hello");
        Member member2 = new Member("hello");

        //when
        memberService.join(member1);
        memberService.join(member2);

        fail("예외가 발생해야 한다.");
    }

    @Test
    public void authenticateUser() {

        //given
        String email = "email";
        String rawPassword = "12345!";
        String authenPassword = passwordEncoder.encode(rawPassword);

        Member member = new Member();
        member.setEmail(email);
        member.setPassword(authenPassword);

        memberRepository.save(member);

        //when
        boolean authenticated = memberService.authenticateUser(member.getEmail(),rawPassword);

        //then
        Assertions.assertThat(authenticated).isTrue();

    }
}