package Book_Toy_Project.BOOK.Service;

import Book_Toy_Project.BOOK.Entity.Book;
import Book_Toy_Project.BOOK.Entity.BookItem;
import Book_Toy_Project.BOOK.Exception.DuplicateIsbnException;
import Book_Toy_Project.BOOK.Repository.BookRepository;
import Book_Toy_Project.BOOK.api.BookVO;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class BookService {

    private final BookRepository bookRepository;

    public void saveBook(Book book) {
            Book existingBook = bookRepository.findByIsbn(book.getIsbn());
            if (existingBook != null) {
                log.error("중복된 isbn값이 존재합니다: {}", book.getIsbn());
                //GlobalExceptionHandler -> 예외 처리
                throw new DuplicateIsbnException("해당 책이 이미 장바구니에 존재합니다. 다른 책을 주문해주세요" + book.getIsbn());
            } else {
                log.info("상품을 저장합니다");
                bookRepository.save(book);
            }
        }

    public void deleteBook(String isbn) {
        log.info("deleteBook - isbn = {}", isbn);

        Book book = bookRepository.findByIsbn(isbn);
        log.info("book.isbn = {}", book.getIsbn());

            //책이 존재하면
            if (book != null) {
                bookRepository.deleteByIsbn(book.getIsbn());
                log.info("Book with ISBN {} deleted successfully", isbn);
            } else {
                log.error("상품이 존재하지 않습니다. isbn = {}", isbn);
                throw new EntityNotFoundException("상품이 존재하지 않습니다. isbn = {}" + isbn);
            }
    }

    @Transactional(readOnly = true)
    public Book createBookFromBookVO(BookVO bookVO) {
        BookItem item = bookVO.getItems().get(0); // 첫 번째 BookItem을 가져옴
        Book book = new Book();
        book.setTitle(item.getTitle());
        book.setIsbn(item.getIsbn());
        book.setAuthor(item.getAuthor());
        book.setPublisher(item.getPublisher());
        book.setImage(item.getImage());
        book.setPubdate(item.getPubdate());
        book.setPrice(Integer.parseInt(item.getDiscount()));
        book.setLink(item.getLink());
        return book;
    }

}
