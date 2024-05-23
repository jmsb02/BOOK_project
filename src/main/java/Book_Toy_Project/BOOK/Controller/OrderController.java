package Book_Toy_Project.BOOK.Controller;

import Book_Toy_Project.BOOK.Entity.Order;
import Book_Toy_Project.BOOK.Entity.OrderBook;
import Book_Toy_Project.BOOK.Exception.DuplicateOrderException;
import Book_Toy_Project.BOOK.Repository.OrderBookRepository;
import Book_Toy_Project.BOOK.Repository.OrderRepository;
import Book_Toy_Project.BOOK.Service.OrderManagementService;
import Book_Toy_Project.BOOK.Service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
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

    private final OrderRepository orderRepository;

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

        Order order = new Order();
        //주문 생성
        Order findOrder = orderService.createOrder(order, card_number, expiryDate, cvv, cardHolder);

        //빈 Order 객체에 주문 하기 db인 orderBook 객체를 넘김 (orderBook - 주문하기, order - 주문 내역)
        List<OrderBook> orderBooks = orderBookRepository.orderBookList();
        Order EndOrder = orderService.getOrderByOrderBook(findOrder, orderBooks);

        //order db에 해당 주문이 존재하지 않을 경우 저장
        if (orderRepository.findByIsbn(EndOrder.getIsbn()) == null) {
            orderManagementService.saveOrderAndDeleteOrderBook(EndOrder);
        }
        //존재할 경우 예외 터트림
        else {
            throw new DuplicateOrderException("이 상품은 이미 주문되었습니다. 주문 내역을 확인하거나 다른 상품을 선택해주세요.");
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