package Book_Toy_Project.BOOK.Service;

import Book_Toy_Project.BOOK.Entity.Order;
import Book_Toy_Project.BOOK.Entity.OrderBook;
import Book_Toy_Project.BOOK.Exception.DuplicateOrderException;
import Book_Toy_Project.BOOK.Repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class OrderManagementService {

    private final OrderService orderService;
    private final OrderRepository orderRepository;
    private final OrderBookService orderBookService;

    //order db에 상품 저장 orderbook db에서 상품 삭제
    public void saveOrderAndDeleteOrderBook(Order order) {
        try {
            orderService.saveOrder(order);
            orderBookService.deleteBook(order.getIsbn());
        } catch (Exception e) {
            log.error("주문 저장 및 주문 도서 삭제 중 오류가 발생했습니다: {}", e.getMessage());
            //보상 트랜잭션 수행
            compensateOrderManagement(order);
        }
    }

    public void processOrder(String card_number, String expiryDate, String cvv, String cardHolder, OrderBook orderBook) {
        Order order = new Order();
        //주문 생성
        Order findOrder = orderService.createOrder(order, card_number, expiryDate, cvv, cardHolder);
        Order EndOrder = orderService.getOrderByOrderBook(findOrder, orderBook);

        if (orderRepository.findByIsbn(EndOrder.getIsbn()) != null) {
            throw new DuplicateOrderException("이 상품은 이미 주문되었습니다. 주문 내역을 확인하거나 다른 상품을 선택해주세요.");
        }
        saveOrderAndDeleteOrderBook(EndOrder);
    }

    private void compensateOrderManagement(Order order) {
        try {
            //주문 저장 성공했으나 주문 도서 삭제 실패 -> 저장된 주문 취소
            orderBookService.deleteBook(order.getIsbn());
            log.info("보상 트랜잭션 : 도서 취소 완료");
        } catch (Exception e) {
            log.info("보상 트랜잭션 중 오류가 발생했습니다 : {}", e.getMessage());
        }
    }
}