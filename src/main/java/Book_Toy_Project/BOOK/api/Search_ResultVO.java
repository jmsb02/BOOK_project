package Book_Toy_Project.BOOK.api;

import Book_Toy_Project.BOOK.Entity.BookItem;
import lombok.*;

import java.util.List;

/**
 * Search_ResultVO -> 네이버 검색 API로 넘어오는 JSON 값을 담기 위해 사용되는 DTO 객체
 * API 응답 데이터 매핑, 응답 데이터 관리, 간편한 데이터 접근
 */
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class Search_ResultVO {
    private String lastBuildDate;
    private int total;
    private int start;
    private int display;
    private List<BookItem> items;
}
