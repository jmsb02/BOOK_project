package Book_Toy_Project.BOOK.Controller;

import Book_Toy_Project.BOOK.Entity.OrderBook;
import Book_Toy_Project.BOOK.Exception.DuplicateOrderException;
import Book_Toy_Project.BOOK.Repository.OrderBookRepository;
import Book_Toy_Project.BOOK.Service.OrderManagementService;
import Book_Toy_Project.BOOK.Service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    private final OrderManagementService orderManagementService;


    private final OrderBookRepository orderBookRepository;


    //주문하기
    @PostMapping("/saveOrder")
    public String saveOrder(HttpServletRequest request) {


        String card_number = request.getParameter("Card_Number");
        String expiryDate = request.getParameter("Expiry_Date");
        String cvv = request.getParameter("CVV");
        String cardHolder = request.getParameter("Card_Holder");

        if (card_number == null || expiryDate == null || cvv == null || cardHolder == null) {
            throw new IllegalArgumentException("모든 카드 정보가 필요합니다.");
        }

        log.info("Card_Number = {}, Expiry_Date = {}, CVV = {}, Card_Holder = {}",
                card_number, expiryDate, cvv, cardHolder);


        //빈 Order 객체에 주문 하기 db인 orderBook 객체를 넘김 (orderBook - 주문하기, order - 주문 내역)
        List<OrderBook> orderBooks = orderBookRepository.orderBookList();

        for (OrderBook orderBook : orderBooks) {
            try {
                orderManagementService.processOrder(card_number, expiryDate, cvv, cardHolder, orderBook);
            }catch (DuplicateOrderException e) {
                log.error("중복된 주문: {}", e.getMessage());
                throw new DuplicateOrderException("중복된 주문입니다. 다시 한 번 확인해주세요.");
            }
        }

        return "redirect:/home";
    }




    @GetMapping("/delete_order")
    public String delete_order(@RequestParam("isbn") String isbn) {
        log.info("주문 내역 페이지에서 상품을 삭제합니다.");
        orderService.deleteOrder(isbn);
        return "redirect:/home";
    }


}