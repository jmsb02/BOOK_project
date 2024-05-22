package Book_Toy_Project.BOOK.api;

import Book_Toy_Project.BOOK.Entity.BookItem;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * 넘어오는 응답 데이터를 임시 저장하는 DTO 객체, 이후 BOOK 객체로 변환해서 처리
 * Search_ResultVO -> API 응답의 모든 정보 포함
 * BookVO -> 'BOOK' 엔티티로 변환하기 위해 필요한 정보만 포함
 */

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Entity
@Table(name = "BookVO")
public class BookVO {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    //외부와의 데이터 통신
    private Long id;

    // BookItem 클래스로 대체하여 items 필드 내의 객체를 매핑
    @OneToMany(mappedBy = "bookVO", cascade = CascadeType.ALL)
    private List<BookItem> items;
}
