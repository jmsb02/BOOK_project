package Book_Toy_Project.BOOK.Controller;

import Book_Toy_Project.BOOK.Cookie_Session.SessionConst;
import Book_Toy_Project.BOOK.Entity.*;

import Book_Toy_Project.BOOK.Repository.*;
import Book_Toy_Project.BOOK.Service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;


@Controller
@Slf4j //로그
@RequiredArgsConstructor
public class HomeController {

    private final BookRepository bookRepository;

    private final WishlistRepository wishlistRepository;

    private final OrderService orderService;


    @GetMapping("/home")
    public String directlyHome() {
        return "home/home";
    }


    @GetMapping("home/mypage/basket")
    public String basket(Model model) {
        log.info("Basket page");
        List<Book> books = bookRepository.findAll();

        model.addAttribute("books", books);
        return "home/mypage/basket";
    }

    @GetMapping("home/mypage/wishlist")
    public String wishlist(Model model) {
        log.info("wishlist page");

        List<Wishlist> wishlists = wishlistRepository.findAll();

        model.addAttribute("wishlists", wishlists);
        return "home/mypage/wishlist";
    }


    @GetMapping("home/mypage/getAllOrders")
    public String getOrderlist(Model model) {
        log.info("orderList, 주문 목록");
        List<Order> orderBooks = orderService.findOrder();
        log.info("orderBooks={}", orderBooks);
        model.addAttribute("orderBooks", orderBooks);

        return "home/mypage/getAllOrders";
    }


    @GetMapping("home/mypage")
    public String mypage(@SessionAttribute(SessionConst.LOGIN_MEMBER) Member member, Model model) {

        //세션에 LoginMember를 상수로 설정 -> SessionAttribute로 바로 가져올 수 있음
        model.addAttribute("member", member);

        log.info("My Page");

        return "home/mypage/mypage";
    }
}
