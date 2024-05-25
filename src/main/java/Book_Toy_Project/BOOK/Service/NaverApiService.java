package Book_Toy_Project.BOOK.Service;

import Book_Toy_Project.BOOK.Entity.Book;
import Book_Toy_Project.BOOK.Entity.BookItem;
import Book_Toy_Project.BOOK.api.BookVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class NaverApiService {

    private final String clientId = ""; // 네이버 API 애플리케이션에서 발급받은 클라이언트 아이디
    private final String clientSecret = ""; // 네이버 API 애플리케이션에서 발급받은 클라이언트 시크릿

    @Autowired
    private final parseBook parseBook;

    @Autowired
    private final BookService bookService;

    @Transactional
    public Book searchBookByIsbn(String isbn) throws Exception {

        log.info("NaverApiService _ searchBookByIsbn 실행");
        // 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Naver-Client-Id", clientId);
        headers.set("X-Naver-Client-Secret", clientSecret);

        // API 호출을 위한 URI 생성
        URI uri = UriComponentsBuilder
                .fromUriString("https://openapi.naver.com")
                .path("/v1/search/book")
                .queryParam("query", isbn)
                .encode()
                .build()
                .toUri();

        // API 호출을 위한 요청 객체 생성
        RequestEntity<Void> req = RequestEntity
                .get(uri)
                .headers(headers)
                .build();

        // API 호출하여 응답 받기
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> resp = restTemplate.exchange(req, String.class);

        //body 확인 - 정상 작동
        log.info("Response Body: {}", resp.getBody());

        if (resp.getBody() == null) {
            log.error("응답 본문이 비워져 있습니다.");
            throw new Exception("응답 본문이 비워져 있습니다");
        }
        List<BookItem> bookItems = parseBook.parseBookInfo(resp.getBody());

        // 응답 본문 파싱하여 BookVO 객체로 변환 후 최종적으로 Book 객체로
        log.info("응답 본문을 파싱하여 BookVO 객체로 변환합니다.");
        Book book = new Book();
        if (!bookItems.isEmpty()) {
            BookVO bookVO = new BookVO();
            bookVO.setItems(bookItems);

            //BookV0 객체를 Book 객체로 변환
            log.info("BookV0 객체를 Book 객체로 변환");
            book = bookService.createBookFromBookVO(bookVO);
            log.info("변환된 Book 객체 : {}", book);
        }
        return book;
    }

    @Transactional
    public String BookListPaging(String text, int page) {

        //한 페이지에 표시할 결과 수
        int display = 10;

        //검색 시작 위치 계산
        int start = (page - 1) * display + 1;

        //String apiURL = "https://openapi.naver.com/v1/search/blog?query=" + text;    // JSON 결과
        URI uri = UriComponentsBuilder
                .fromUriString("https://openapi.naver.com")
                .path("/v1/search/book.json")
                .queryParam("query", text)
                .queryParam("display", display)
                .queryParam("start", start)
                .queryParam("sort", "sim")
                .encode()
                .build()
                .toUri();

        // Spring 요청 제공 클래스, HTTP 요청 생성
        RequestEntity<Void> req = RequestEntity
                .get(uri)
                .header("X-Naver-Client-Id", clientId)
                .header("X-Naver-Client-Secret", clientSecret)
                .build();

        // Spring 제공 restTemplate, 요청한 HTTP를 통해 응답을 받음
        RestTemplate restTemplate = new RestTemplate();

        //exchange를 통해 요청을 실행하고 응답으로 ResponseEntity<String>을 받음 (String - 응답의 본문)
        ResponseEntity<String> resp = restTemplate.exchange(req, String.class);

        //받은 응답 본문을 문자열로 받아옴
        String responseBody = resp.getBody();

        return responseBody;
    }
}
