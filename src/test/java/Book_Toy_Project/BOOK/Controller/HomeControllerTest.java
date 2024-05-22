package Book_Toy_Project.BOOK.Controller;

import Book_Toy_Project.BOOK.Entity.Book;
import Book_Toy_Project.BOOK.Entity.OrderBook;
import Book_Toy_Project.BOOK.Exception.OrderBooknotFoundException;
import Book_Toy_Project.BOOK.Repository.BookRepository;
import Book_Toy_Project.BOOK.Repository.OrderBookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
public class HomeControllerTest {

    @Autowired
    private HomeController homeController;

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private OrderBookRepository orderBookRepository;

    @Test
    public void testOrderBookFromISBNAndCheckOrdersPage() {
        // Given
        String isbn = "1234567890";
        Book book = new Book();
        book.setIsbn(isbn);
        when(bookRepository.findByIsbn(isbn)).thenReturn(book);

        // When
        String redirectUrl = homeController.orderFromISBN(isbn);

        // Then
        assertThat(redirectUrl).isEqualTo("redirect:/home");
    }

    @Test
    public void testOrdersPageWithNoOrderBooks() throws OrderBooknotFoundException {
        // Given
        List<OrderBook> orderBooks = new ArrayList<>();
        when(orderBookRepository.orderBookList()).thenReturn(orderBooks);

        // When
        Model model = new ExtendedModelMap();
        String viewName = homeController.orders(model);

        // Then
        assertThat(viewName).isEqualTo("error/error");
    }
}
