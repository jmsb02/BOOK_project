package Book_Toy_Project.BOOK.Controller;

import Book_Toy_Project.BOOK.Entity.Book;
import Book_Toy_Project.BOOK.Entity.BookItem;
import Book_Toy_Project.BOOK.Entity.OrderBook;
import Book_Toy_Project.BOOK.Exception.DuplicateOrderException;
import Book_Toy_Project.BOOK.Service.NaverApiService;
import Book_Toy_Project.BOOK.Service.OrderBookService;
import Book_Toy_Project.BOOK.api.Search_ResultVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@Slf4j
public class BookListController {


    @Autowired
    private OrderBookService orderBookService;

    @Autowired
    private NaverApiService naverApiService;


    //페이징 처리
    @GetMapping("list/Booklist")
    public String list(@RequestParam(required = false, value = "query") String text, @RequestParam(value = "page", defaultValue = "1") int page, Model model) {
        try {
            log.info("BookListController - list 메서드 실행");

            if (text == null || text.trim().isEmpty()) {
                // 검색어가 비어 있는 경우
                model.addAttribute("errorMessage", "다시 입력해주세요.");
                return "home/home"; // 홈으로 리다이렉트
            }

            //api 응답 본문 string 형식으로 가져옴
            String responseBody = naverApiService.BookListPaging(text, page);

            // JSON 파싱 객체 생성 (Json 문자열을 객체로 만듦, 문서화)
            ObjectMapper om = new ObjectMapper();
            try {
                //readValue 메서드 -> JSON 문자열을 'Search_ResultVO' 객체로 변환
                Search_ResultVO resultVO = om.readValue(responseBody, Search_ResultVO.class);

                log.info("resultVO 객체 total = {}", resultVO.getTotal());

                List<BookItem> books = resultVO.getItems();
                model.addAttribute("books", books);

                // 총 책 수를 모델에 추가
                model.addAttribute("totalBooks", resultVO.getTotal());

                // 현재 페이지 번호를 모델에 추가하여 뷰로 전달
                model.addAttribute("currentPage", page);

                model.addAttribute("query", text);

                // 총 페이지 수를 모델에 추가하여 뷰로 전달
                int display = 10;
                int totalPages = (resultVO.getTotal() + display - 1) / display;
                log.info("totalPages = {}", totalPages);
                model.addAttribute("totalPages", totalPages);

            } catch (JsonMappingException e) {
                log.error("JSON 매핑 중 오류 발생 : {}", e.getMessage());
                e.printStackTrace();
            } catch (JsonProcessingException e) {
                log.error("JSON 처리 중 오류 발생 : {}", e.getMessage());
                e.printStackTrace();
            }
            log.info("책 목록입니다.");
            return "list/Booklist";

        } catch (Exception e) {
            // 예외 처리
            model.addAttribute("errorMessage", "다시 입력해주세요.");
            return "/home/home"; // 홈 페이지로 이동하는 뷰 이름을 반환합니다.
        }
    }


    @GetMapping("/BooklistToOrderBook")
    public String BooklistToOrderBook(@RequestParam("isbn") String isbn) throws Exception {
        //basket -> orderBook 바로 상품 주문하기 메서드

        log.info("BooklistToOrderBook 메서드 실행");

        log.info("Booklist 상품 주문하기 버튼 클릭. ISBN: {}", isbn);

        //isbn으로 Json 객체 -> book 객체로 convert
        Book book = naverApiService.searchBookByIsbn(isbn);

        if (book.getTitle() == null || book.getIsbn() == null) {
            // 유효성 검사 실패 시 적절한 응답 반환
            log.error("북 이름, isbn이 필요합니다.");
            throw new Exception("북 이름, isbn 값이 필요합니다");
        }

        try {
            log.info("Booklist -> orderBook");
            //책 정보를 db에 저장
            OrderBook orderBook = orderBookService.getOrderBook(book);
            orderBookService.saveOrderBook(orderBook);

            // 적절한 응답 반환
            return "redirect:/home";

        } catch (DuplicateOrderException e) {
            // 중복된 주문이 발생한 경우 적절한 응답 반환
            log.error("중복된 주문이 발생하였습니다: {}", e.getMessage());
            throw new DuplicateOrderException("중복된 주문이 발생하였습니다.");

        } catch (Exception e) {
            // 저장 중에 예외가 발생한 경우 적절한 응답 반환
            log.error("저장 중 예외가 발생하였습니다.");
            throw new Exception("저장 중 예외가 발생하였습니다. 다시 한 번 확인해주세요.", e);
        }
    }

}
