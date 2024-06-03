package Book_Toy_Project.BOOK.Controller;

import Book_Toy_Project.BOOK.Entity.OrderBook;
import Book_Toy_Project.BOOK.Exception.OrderBooknotFoundException;
import Book_Toy_Project.BOOK.Repository.OrderBookRepository;
import Book_Toy_Project.BOOK.Service.OrderBookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class OrderBookController {

    private final OrderBookService orderBookService;

    private final OrderBookRepository orderBookRepository;

    //홈 화면에서 db에 값이 있을 경우 바로 접근
    @GetMapping("home/mypage/orderBooks")
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

    @GetMapping("/delete_orderBook")
    public String delete_orderBook(@RequestParam("isbn") String isbn) {
        log.info("주문 하기 페이지에서 상품을 삭제합니다.");
        orderBookService.deleteBook(isbn);
        return "redirect:/home";
    }
}
