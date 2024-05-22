package Book_Toy_Project.BOOK.Service;

import Book_Toy_Project.BOOK.Entity.BookItem;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonSyntaxException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class parseBook {

    public List<BookItem> parseBookInfo(String responseBody) {
        log.info("parseBookInfo_responseBody : {}", responseBody);

        // JSON 형식의 responseBody를 파싱하여 필요한 정보 추출

        // 1) responseBody가 null인지 검증
        if (responseBody == null) {
            throw new IllegalArgumentException("responseBody is null");
        }

        List<BookItem> bookList = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); // 알려지지 않은 속성 무시 설정

        try {
            JsonNode root = objectMapper.readTree(responseBody);
            JsonNode itemsNode = root.get("items");

            // items 필드가 존재하고 배열인지 확인
            if (itemsNode != null && itemsNode.isArray()) {
                for (JsonNode itemNode : itemsNode) {
                    BookItem bookItem = objectMapper.treeToValue(itemNode, BookItem.class);
                    bookList.add(bookItem);
                }
            }
            log.info("책 정보를 {}개 만큼 파싱하였습니다.", bookList.size());
            return bookList;

        } catch (JsonSyntaxException e) {
            log.error("JsonSyntaxException : {}", e.getMessage());
            return null;

        } catch (Exception e) {
            log.error("Exception : {}", e.getMessage());
            return null;
        }

    }
}
