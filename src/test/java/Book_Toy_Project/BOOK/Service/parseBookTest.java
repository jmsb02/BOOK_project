package Book_Toy_Project.BOOK.Service;

import Book_Toy_Project.BOOK.Entity.BookItem;
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
    public class parseBookTest {

    @Autowired
    parseBook parseBook;

    //정상 처리
    @Test
    public void parseBookInfo_SuccessreturnBooklist() {
        //given
        String responseBody = "{\"items\":[{\"title\":\"Book1\",\"link\":\"http://example.com/book1\",\"image\":\"http://example.com/book1.jpg\"},{\"title\":\"Book2\",\"link\":\"http://example.com/book2\",\"image\":\"http://example.com/book2.jpg\"}]}";

        //when
        List<BookItem> bookItems = parseBook.parseBookInfo(responseBody);

        //then
        assertThat(bookItems).isNotNull();
        assertThat(bookItems.get(0).getTitle()).isEqualTo("Book1");
        assertThat(bookItems.get(0).getLink()).isEqualTo("http://example.com/book1");
        assertThat(bookItems.get(0).getImage()).isEqualTo("http://example.com/book1.jpg");
        assertThat(bookItems.get(1).getTitle()).isEqualTo("Book2");
        assertThat(bookItems.get(1).getLink()).isEqualTo("http://example.com/book2");
        assertThat(bookItems.get(1).getImage()).isEqualTo("http://example.com/book2.jpg");
    }

    //JsonSyntaxException, Exception -> null 반환 처리
    @Test
    public void parseBookInfo_returnNull() {
        // given
        String responseBody = "Invalid JSON";

        // when
        List<BookItem> bookList = parseBook.parseBookInfo(responseBody);

        // then
        assertThat(bookList).isNull();
    }

    //IllegalArgumentException 해결
    @Test
    public void parseBookInfo_IllegalArgumentException() {
        // given
        String responseBody = null;

        // when & then
        assertThatThrownBy(() -> parseBook.parseBookInfo(responseBody))
                .isInstanceOf(IllegalArgumentException.class);
    }
}