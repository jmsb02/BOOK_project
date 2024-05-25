package Book_Toy_Project.BOOK.Controller;

import Book_Toy_Project.BOOK.Cookie_Session.SessionConst;
import Book_Toy_Project.BOOK.Entity.*;
import Book_Toy_Project.BOOK.Exception.DuplicateOrderException;
import Book_Toy_Project.BOOK.Exception.OrderBooknotFoundException;
import Book_Toy_Project.BOOK.Form.LoginForm;
import Book_Toy_Project.BOOK.Repository.*;
import Book_Toy_Project.BOOK.Service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;
import java.util.NoSuchElementException;

@Controller
@Slf4j //로그
@RequiredArgsConstructor
public class HomeController {

    private final BookRepository bookRepository;

    private final OrderBookRepository orderBookRepository;

    private final OrderBookService orderBookService;

    private final WishlistService wishlistService;
    private final WishlistRepository wishlistRepository;

    private final OrderService orderService;

    private final MemberService memberService;
    private final MemberRepository memberRepository;


    @GetMapping("/")
    public String home(Model model) {
        log.info("home Controller");
        model.addAttribute("loginForm", new LoginForm());
        return "login";
    }

    @GetMapping("/home")
    public String directlyHome() {
        return "home/home";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        //세션을 삭제한다.
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "login";
    }

    @GetMapping("/login")
    public String startHomePage(@SessionAttribute(
            name = SessionConst.LOGIN_MEMBER, required = false)
                                Member loginMember, Model model) {
        log.info("startHomePage");
        //세션에 회원 데이터가 없으면 home
        if (loginMember == null) {
            return "/login";
        }

        //세션이 유지되면 로그인으로 이동
        model.addAttribute("member", loginMember);
        return "home/home";
    }

    @PostMapping("/login")
    public String postHomePage(@Valid LoginForm loginForm, BindingResult result, HttpServletRequest request, Model model) {
        if (result.hasErrors()) {
            log.error("로그인 오류 : 회원정보를 다시 확인해주세요.");
            return "login";
        }

        Member findMember = memberRepository.findByEmail(loginForm.getEmail());
        if (findMember == null) {
            log.error("로그인 실패 : 존재하지 않는 이메일입니다.");
            model.addAttribute("loginError", "로그인 실패, 아이디 또는 비밀번호를 다시 확인해주세요.");
            return "login";
        }

        //Validation 검증
        //검증 실패시
        if (!memberService.authenticateUser(loginForm.getEmail(), loginForm.getPassword())) {
            log.error("로그인 실패 : 아이디 또는 비밀번호가 일치하지 않습니다.");
            model.addAttribute("loginError", "로그인 실패, 아이디 또는 비밀번호를 다시 확인해주세요.");
            return "login";
        }

        //검증 성공
        log.info("로그인 성공");

        //세션 처리 및 저장
        memberService.processLoginSuccess(request,findMember);

        log.info("홈 화면으로 이동합니다");
        
        return "home/home";
    }


    @GetMapping("home/mypage/basket")
    public String basket(Model model) {
        log.info("Basket page");
        List<Book> books = bookRepository.findAll();

        model.addAttribute("books", books);
        return "home/mypage/basket";
    }

    @GetMapping("home/orders")
    public String FromBasketToOrderBook(@RequestParam("isbn") String isbn) {
        log.info("FromBasketToOrderBook");
        try {
            orderBookService.processOrderBook(isbn);
            return "redirect:/home";
        } catch (NoSuchElementException | DuplicateOrderException | IllegalStateException e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    //홈 화면에서 db에 값이 있을 경우 바로 접근
    @GetMapping("home/mypage/orders")
    public String OrderBookPage(Model model) {

        log.info("주문하기 화면으로 이동합니다.");
        List<OrderBook> orderBooks = orderBookRepository.orderBookList();
        log.info("orderBooks={}", orderBooks);

        int totalOrderAmount = orderBookService.calculateTotalOrderAmount(orderBooks);

        if (orderBooks != null && !orderBooks.isEmpty()) {
            model.addAttribute("orderBooks", orderBooks);
            model.addAttribute("totalOrderAmount", totalOrderAmount);
            return "home/mypage/orders";
        } else {
            throw new OrderBooknotFoundException("장바구니에 상품이 존재하지 않습니다.");
        }
    }

    @GetMapping("home/wishlist")
    //isbn와 찜 목록 버튼을 통해 값 보내주기
    public String FromBasketToWishlist(@RequestParam("isbn") String isbn) {
        try {
            log.info("wishlistFromISBN");

            wishlistService.addFromBasketToWishlist(isbn);

            //성공적으로 저장 되었음 -> 홈 화면으로 리다이렉션
            return "redirect:/home";

        } catch (Exception e) {
            // Wishlist 저장 실패 또는 다른 예외 발생 시 처리
            log.error("해당 상품이 이미 찜 목록에 존재합니다 : {}", e.getMessage());
            throw new IllegalStateException("해당 상품이 이미 찜 목록에 존재합니다.");
        }
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
