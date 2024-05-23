package Book_Toy_Project.BOOK.Controller;

import Book_Toy_Project.BOOK.Entity.Book;
import Book_Toy_Project.BOOK.Exception.DuplicateIsbnException;
import Book_Toy_Project.BOOK.Service.BookService;
import Book_Toy_Project.BOOK.Service.NaverApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
public class BasketController {

    @Autowired
    private BookService bookService;

    @Autowired
    private NaverApiService naverApiService;


    // isbn을 통해 찾은 book을 db에 저장 (web 화면 : from 홈화면 to 장바구니)
    @GetMapping("/home/basket")
    public String addToBasket(@RequestParam("isbn") String isbn) throws Exception {

        log.info("BasketController 실행합니다");

        log.info("장바구니에 상품 추가 요청을 받았습니다. ISBN: {}", isbn);

        // 네이버 책 검색 API를 통해 해당 ISBN에 해당하는 책 추출
        Book book = naverApiService.searchBookByIsbn(isbn);

        if (book.getTitle() == null || book.getIsbn() == null) {
            // 유효성 검사 실패 시 적절한 응답 반환
            log.error("북 이름, isbn이 필요합니다.");
            throw new Exception("북 이름, isbn 값이 필요합니다");
        }

        try {
            log.info("책 정보를 db에 저장합니다.");
            //책 정보를 db에 저장
            bookService.saveBook(book);

            // 적절한 응답 반환
            return "redirect:/home";

        } catch (DuplicateIsbnException e) {
            // 중복된 ISBN이 발생한 경우 적절한 응답 반환
            log.error("중복된 ISBN이 발생했습니다: {}", e.getMessage());
            throw new DuplicateIsbnException("해당 책이 이미 장바구니에 존재합니다.");

        } catch (Exception e) {
            // 저장 중에 예외가 발생한 경우 적절한 응답 반환
            log.error("저장 중 예외가 발생하였습니다.", e.getMessage());
            throw new Exception("예상치 못한 예외가 발생했습니다 다시 시도해주세요.");
        }
    }


    @GetMapping("/delete_book")
    public String delete_book(@RequestParam("isbn") String isbn) {
        bookService.deleteBook(isbn);
        return "redirect:/home";
    }

}
