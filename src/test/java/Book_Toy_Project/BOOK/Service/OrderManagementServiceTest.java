package Book_Toy_Project.BOOK.Service;

import Book_Toy_Project.BOOK.Entity.Order;
import Book_Toy_Project.BOOK.Entity.OrderBook;
import Book_Toy_Project.BOOK.Exception.DuplicateOrderException;
import Book_Toy_Project.BOOK.Repository.OrderBookRepository;
import Book_Toy_Project.BOOK.Repository.OrderRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderManagementServiceTest {

    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderBookService orderBookService;
    @Autowired
    OrderBookRepository orderBookRepository;

    @Autowired
    OrderManagementService orderManagementService;

    @Test
    public void saveOrderAndDeleteOrderBook() {

        //given - order db와 orderbook db에 같은 상품이 들어가있다고 가정
        Order order = new Order();
        order.setIsbn("1234");

        OrderBook orderBook = new OrderBook();
        orderBook.setIsbn("1234");

        orderBookRepository.save(orderBook);

        //when
        orderManagementService.saveOrderAndDeleteOrderBook(order);

        //then
        Assertions.assertThat(orderRepository.findByIsbn(order.getIsbn())).isNotNull();
        Assertions.assertThat(orderBookRepository.findByIsbn(order.getIsbn())).isNull();

    }

    @Test
    public void saveOrderAndDeleteOrderBook_Exception() {

        //given - 수동으로 저장 및 삭제하여 보상 트랜잭션 수행
        Order order = new Order();
        order.setIsbn("1234");

        OrderBook orderBook = new OrderBook();
        orderBook.setIsbn("1234");
        orderBookRepository.delete(orderBook);

        //when
        orderManagementService.saveOrderAndDeleteOrderBook(order);

        //then
        Assertions.assertThat(orderRepository.findByIsbn(order.getIsbn())).isNotNull();
        Assertions.assertThat(orderBookRepository.findByIsbn(order.getIsbn())).isNull();

    }

    @Test
    public void processOrder_Success() {

        String card_number = "cN";
        String expiryDate = "eD";
        String cvv = "cvv";
        String cardHolder = "cH";

        OrderBook orderBook = new OrderBook();
        orderBook.setIsbn("1234");
        orderBookRepository.save(orderBook);

        //when
        orderManagementService.processOrder(card_number, expiryDate, cvv, cardHolder, orderBook);

        //then
        Assertions.assertThat(orderRepository.findByIsbn(orderBook.getIsbn())).isNotNull();
        Assertions.assertThat(orderBookRepository.findByIsbn(orderBook.getIsbn())).isNull();
    }

    @Test
    public void processOrder_Fail() {

        //given
        Order order = new Order();
        order.setIsbn("1234");
        orderRepository.save(order);

        String card_number = "cN";
        String expiryDate = "eD";
        String cvv = "cvv";
        String cardHolder = "cH";

        OrderBook orderBook = new OrderBook();
        orderBook.setIsbn("1234");
        orderBookRepository.save(orderBook);

        //when, then
        assertThatThrownBy(() -> orderManagementService.processOrder(card_number, expiryDate, cvv, cardHolder, orderBook))
                .isInstanceOf(DuplicateOrderException.class);
    }

}