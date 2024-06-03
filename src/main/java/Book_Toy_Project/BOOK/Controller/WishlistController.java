package Book_Toy_Project.BOOK.Controller;

import Book_Toy_Project.BOOK.Entity.*;
import Book_Toy_Project.BOOK.Exception.DuplicateOrderException;
import Book_Toy_Project.BOOK.Exception.DuplicateWishlistException;
import Book_Toy_Project.BOOK.Service.NaverApiService;
import Book_Toy_Project.BOOK.Service.WishlistService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@Slf4j
public class WishlistController {

    @Autowired
    private WishlistService wishlistService;

    @Autowired
    private NaverApiService naverApiService;


    //api book isbn을 통해 db에 저장
    @GetMapping("/savedBookinWishlist")
    public String addToWishlist(@RequestParam("isbn") String isbn) throws Exception {

        //isbn -> 네이버 책 검색 API에 요청 보냄

        log.info("savedBookinWishlist");
        //Json 객체 -> book 객체로 convert
        Book book = naverApiService.searchBookByIsbn(isbn);

        log.info("변환된 Book 객체 : {}", book);

        Wishlist wishlist = WishlistService.convertToEntityWishlistBook(book);

        log.info("변환된 Wishlist 객체 : {}", wishlist);

        if (wishlist.getName() == null || wishlist.getIsbn() == null) {
            // 유효성 검사 실패 시 적절한 응답 반환
            log.error("북 이름, isbn이 필요합니다.");
            throw new Exception("북 이름, isbn 값이 필요합니다");
        }

        try {
            log.info("책 정보를 wishlist db에 저장합니다.");
            //책 정보를 db에 저장
            wishlistService.saveWishlist(wishlist);

            // 적절한 응답 반환
            return "redirect:/home";

        } catch (DuplicateWishlistException e) {
            // 중복된 ISBN이 발생한 경우 적절한 응답 반환
            log.error("이미 찜 목록에 상품이 존재합니다.: {}", e.getMessage());
            throw new DuplicateWishlistException("이미 찜 목록에 상품이 존재합니다.");

        } catch (Exception e) {
            // 저장 중에 예외가 발생한 경우 적절한 응답 반환
            log.error("저장 중 예외가 발생하였습니다.");
            throw new Exception("장바구니에 이미 해당 책이 있습니다.", e);
        }
    }

    /**
     * from wishlist to orderBook-> isbn으로 책 db 저장 후 redirect
     */
    @GetMapping("/saveWishlistToOrderBookRepository")
    public String saveWishlistToOrderBookRepository(@RequestParam("isbn") String isbn) throws Exception {
        log.info("saveWishlistToOrderBookRepository");
        try {
            wishlistService.saveWishlistToOrderBookAndDeleteWishlist(isbn);
            return "redirect:/home";
        } catch (DuplicateOrderException e) {
            log.error("중복 주문이 발생하였습니다.");
            throw new DuplicateOrderException("중복 주문이 발생하였습니다.");
        } catch (Exception e) {
            log.error("처리 중 오류가 발생하였습니다.");
            throw new Exception("처리 중 오류가 발생하였습니다.");
        }
    }

    @GetMapping("/delete_wishlist")
    public String delete_wishlist(@RequestParam("isbn") String isbn) {
        wishlistService.deleteWishlist(isbn);
        return "redirect:/home";
    }


}
