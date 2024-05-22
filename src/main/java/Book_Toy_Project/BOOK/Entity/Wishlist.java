package Book_Toy_Project.BOOK.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Entity
@Data
@Getter @Setter
@Table(name = "wishlist")
public class Wishlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wishlist_id")
    private Long id;

    //isbn -> wish list 형식 위한 객체 추가
    private String name;
    private int price;
    private String author;
    private String publisher;
    private String image;
    private String pubdate;
    private String link;
    @Column(name = "isbn", unique = true)
    private String isbn;
    private int count;



}
