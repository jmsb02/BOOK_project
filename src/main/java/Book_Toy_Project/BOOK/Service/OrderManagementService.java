package Book_Toy_Project.BOOK.Service;

import Book_Toy_Project.BOOK.Entity.Order;
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