package Book_Toy_Project.BOOK.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "orders")
@Getter @Setter
public class Order {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    //orderBook 객체 -> order
    private String name;
    private int price;
    private String author;
    private String publisher;
    private String image;
    private String pubdate;
    private String link;
    @Column(name = "isbn", unique = true)
    private String isbn;
    private Integer count;

    //card -> order
    //주문 카드 정보
    private String cardNumber;
    private String expiryDate;

    private String cvv;
    private String cardHolder;







}