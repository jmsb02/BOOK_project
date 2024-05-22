package Book_Toy_Project.BOOK.Service;

import Book_Toy_Project.BOOK.Entity.Order;
import Book_Toy_Project.BOOK.Entity.OrderBook;
import Book_Toy_Project.BOOK.Repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class OrderService {


    private final OrderRepository orderRepository;

    public void saveOrder(Order order) {
        try {
            log.info("saveOrder 메서드 실행, 상품을 저장합니다.");
            orderRepository.save(order);
            log.info("상품 저장 완료");
        } catch (Exception e) {
            log.info("상품 저장에 실패하였습니다.");
            throw e;
        }
    }

    //상품 주문 취소 (주문 하기 페이지)
    public void deleteOrder(String isbn) {
        Order order = orderRepository.findByIsbn(isbn);
        if (order != null) {
            orderRepository.delete(order);
        } else {
            throw new EntityNotFoundException("OrderBook with ISBN " + isbn + " not found");
        }
    }

    public List<Order> findOrder() {
        return orderRepository.findAll();
    }

    public Order createOrder(Order order, String card_number, String expiryDate, String cvv, String cardHolder) {
        order.setCardNumber(card_number);
        order.setExpiryDate(expiryDate);
        order.setCvv(cvv);
        order.setCardHolder(cardHolder);

        return order;
    }

    public Order getOrderByOrderBook(Order findOrder, List<OrderBook> orderBooks) {
        for (OrderBook orderBook : orderBooks) {
            findOrder.setName(orderBook.getName());
            findOrder.setPublisher(orderBook.getPublisher());
            findOrder.setPrice(orderBook.getPrice());
            findOrder.setAuthor(orderBook.getAuthor());
            findOrder.setImage(orderBook.getImage());
            findOrder.setLink(orderBook.getLink());
            findOrder.setIsbn(orderBook.getIsbn());
            findOrder.setCount(1); // 주문 수량은 1로 가정
            findOrder.setPubdate(orderBook.getPubdate());
        }
        return findOrder;
    }
}

