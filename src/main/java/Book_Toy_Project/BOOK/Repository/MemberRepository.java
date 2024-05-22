package Book_Toy_Project.BOOK.Repository;

import Book_Toy_Project.BOOK.Entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member,Long> {

    //select m from Member m where m.name =: name
    List<Member> findByName(String name);

    Member findByEmail(String email);

}
