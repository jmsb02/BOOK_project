package Book_Toy_Project.BOOK.Controller;

import Book_Toy_Project.BOOK.Service.OrderBookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
@RequiredArgsConstructor
public class OrderBookController {

    private final OrderBookService orderBookService;

    @GetMapping("/delete_orderBook")
    public String delete_orderBook(@RequestParam("isbn") String isbn) {
        log.info("주문 하기 페이지에서 상품을 삭제합니다.");
        orderBookService.deleteBook(isbn);
        return "redirect:/home";
    }
}
