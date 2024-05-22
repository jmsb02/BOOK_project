package Book_Toy_Project.BOOK.Entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter @Setter
public class Book {

    @Id @GeneratedValue
    @Column(name = "book_id")
    private Long id;

    private String title;

    private int price;

    //BookVO -> Book 변환 메서드 추가를 위한 객체들 추가
    @Column(name = "isbn", unique = true)
    private String isbn;
    private String author;
    private String publisher;
    private String image;
    private String pubdate;
    private String link;


}
