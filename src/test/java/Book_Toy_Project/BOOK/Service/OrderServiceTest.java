package Book_Toy_Project.BOOK.Service;

import Book_Toy_Project.BOOK.Entity.Order;
import Book_Toy_Project.BOOK.Entity.OrderBook;
import Book_Toy_Project.BOOK.Repository.OrderBookRepository;
import Book_Toy_Project.BOOK.Repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {

    @Autowired OrderService orderService;
    @Autowired OrderManagementService orderManagementService;
    @Autowired OrderRepository orderRepository;
    @Autowired OrderBookRepository orderBookRepository;

    //동일 isbn을 가진 상품 저장할 때 order db에 상품이 없고 동시에 orderBook db에 상품이 있을시,
    // 상품을 order db에 저장해주고, orderbook db에서는 삭제해줌
    @Test
    public void saveOrderAndDeleteOrderBook() {

        //given
        Order order = new Order();
        order.setIsbn("1234");

        OrderBook orderBook = new OrderBook();
        orderBook.setIsbn("1234");
        orderBookRepository.save(orderBook);

        //when
        orderManagementService.saveOrderAndDeleteOrderBook(order);

        //then
        //1) orderRepository에 상품이 저장 되어야 함
        assertThat(orderRepository.findByIsbn(order.getIsbn())).isNotNull();

        //2) orderBookRepository에 같은 상품이 존재시 삭제 되어야 함.
        assertThat(orderBookRepository.findByIsbn(orderBook.getIsbn())).isNull();

    }

    @Test
    public void deleteOrder_Success() {

        //given
        Order order = new Order();
        order.setIsbn("1234");
        orderRepository.save(order);

        //when
        orderService.deleteOrder(order.getIsbn());
        assertThat(orderRepository.findByIsbn(order.getIsbn())).isNull();
    }

    @Test
    public void deleteOrder_Fail() {

        //given, when
        Order order = new Order();
        order.setIsbn("1234");

        //then
        assertThatThrownBy(() -> orderService.deleteOrder(order.getIsbn()))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    public void findOrder() {
        //given
        Order order1 = new Order();
        order1.setIsbn("1234");
        orderRepository.save(order1);

        Order order2 = new Order();
        order2.setIsbn("5678");
        orderRepository.save(order2);

        //when
        List<Order> orderList = orderService.findOrder();

        //then
        assertThat(orderList.size()).isEqualTo(2);
        assertThat(orderList)
                .extracting(Order::getIsbn)
                .containsExactlyInAnyOrderElementsOf(List.of("1234", "5678"));
    }
}