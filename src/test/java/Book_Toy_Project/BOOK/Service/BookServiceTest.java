package Book_Toy_Project.BOOK.Service;

import Book_Toy_Project.BOOK.Entity.Book;
import Book_Toy_Project.BOOK.Entity.BookItem;
import Book_Toy_Project.BOOK.Exception.DuplicateIsbnException;
import Book_Toy_Project.BOOK.Repository.BookRepository;
import Book_Toy_Project.BOOK.api.BookVO;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.AbstractThrowableAssert;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Slf4j
@RunWith(SpringRunner.class)
public class BookServiceTest {

    @Autowired
    BookService bookService;
    @Autowired
    BookRepository bookRepository;


    //db에 이미 존재하는 isbn을 가진 책을 저장 -> DuplicateIsbnException 예외 발생해야 함
    @Test
    @Transactional
    public void saveBookSuccess() {

        //given
        Book book1 = new Book();
        book1.setIsbn("1234");
        log.info("book1 = {}", book1.getIsbn());

        //when
        bookService.saveBook(book1);
        Book findBook = bookRepository.findByIsbn(book1.getIsbn());

        //then
        assertThat(book1).isEqualTo(findBook);
    }

    @Test
    @Transactional
    public void saveBookFail() {

        //given
        Book book1 = new Book();
        book1.setIsbn("1234");
        log.info("book1 = {}", book1.getIsbn());


        Book book2 = new Book();
        book2.setIsbn("1234");
        log.info("book2 = {}", book2.getIsbn());

        //when
        bookService.saveBook(book1);

        //then
        assertThatThrownBy(() -> bookService.saveBook(book2))
                .isInstanceOf(DuplicateIsbnException.class);
    }

    @Test
    public void deleteBook() {
        //given
        Book book1 = new Book();
        bookService.saveBook(book1);

        //when
        Book findBook = bookRepository.findByIsbn(book1.getIsbn());
        bookService.deleteBook(findBook.getIsbn());

        //then
        assertThat(bookRepository.findByIsbn(findBook.getIsbn())).isNull();
    }

    @Test
    public void createBookFromBookVO() {

        //given
        BookVO bookVO = new BookVO();
        bookVO.setItems(new ArrayList<>());

        BookItem item = new BookItem();
        item.setTitle("title");
        item.setLink("link");
        item.setImage("image");
        item.setAuthor("author");
        item.setDiscount("1");
        item.setPublisher("publisher");
        item.setPubdate("pubdate");
        item.setIsbn("isbn");

        bookVO.getItems().add(item);

        //when
        Book book = bookService.createBookFromBookVO(bookVO);

        //then
        Assertions.assertThat(book.getTitle()).isEqualTo("title");
        Assertions.assertThat(book.getLink()).isEqualTo("link");
        Assertions.assertThat(book.getImage()).isEqualTo("image");
        Assertions.assertThat(book.getAuthor()).isEqualTo("author");
        Assertions.assertThat(book.getPrice()).isEqualTo(1);
        Assertions.assertThat(book.getPublisher()).isEqualTo("publisher");
        Assertions.assertThat(book.getPubdate()).isEqualTo("pubdate");
        Assertions.assertThat(book.getIsbn()).isEqualTo("isbn");
    }
}