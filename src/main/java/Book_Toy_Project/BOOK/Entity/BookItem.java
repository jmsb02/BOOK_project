package Book_Toy_Project.BOOK.Entity;

import Book_Toy_Project.BOOK.api.BookVO;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "BookItem")
public class BookItem {

    //네이버 검색 api를 통해 넘어오는 json 값 매핑 객체

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("link")
    private String link;

    @JsonProperty("image")
    private String image;

    @JsonProperty("author")
    private String author;

    @JsonProperty("discount")
    private String discount;

    @JsonProperty("publisher")
    private String publisher;

    @JsonProperty("pubdate")
    private String pubdate;

    @JsonProperty("isbn")
    private String isbn;

    @JsonProperty("description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_vo_id")
    private BookVO bookVO;
}
