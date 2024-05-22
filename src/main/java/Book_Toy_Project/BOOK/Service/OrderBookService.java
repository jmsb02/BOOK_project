package Book_Toy_Project.BOOK.Service;

import Book_Toy_Project.BOOK.Entity.OrderBook;
import Book_Toy_Project.BOOK.Exception.DuplicateOrderException;
import Book_Toy_Project.BOOK.Repository.OrderBookRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderBookService {

    private final OrderBookRepository orderBookRepository;

    @Transactional
    public void saveOrderBook(OrderBook orderBook) {
        OrderBook existingBook = orderBookRepository.findByIsbn(orderBook.getIsbn());
        if (existingBook != null) {
            //이미 주문 존재할 경우 오류 처리
            log.error("주문이 중복되었습니다, 해당 주문의 isbn 값 : {}", orderBook.getIsbn());
            throw new DuplicateOrderException("주문이 중복되었습니다. 다시 한 번 확인해주세요.");
        } else {
            log.info("상품을 저장합니다");
            orderBookRepository.save(orderBook);
        }
    }

    @Transactional
    public void deleteBook(String isbn) {
        OrderBook orderBook = orderBookRepository.findByIsbn(isbn);
        if (orderBook != null) {
            orderBookRepository.delete(orderBook);
        } else {
            throw new EntityNotFoundException("삭제할 책이 존재하지 않습니다.");
        }
    }

    public int calculateTotalOrderAmount(List<OrderBook> orderBooks) {
        int totalOrderBookAmount = 0;
        for (OrderBook orderBook : orderBooks) {
            totalOrderBookAmount += orderBook.getPrice();
        }
        return totalOrderBookAmount;
    }

}
