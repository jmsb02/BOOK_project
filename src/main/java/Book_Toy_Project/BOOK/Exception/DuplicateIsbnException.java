package Book_Toy_Project.BOOK.Exception;

// RuntimeException -> Unchecked Exception -> rollback
//-> 호출자 강제 예외 처리 x, 코드의 가독성 높이고 예외 처리 간단하게 해줌
public class DuplicateIsbnException extends RuntimeException{
    public DuplicateIsbnException(String message) {
        super(message);
    }

    public DuplicateIsbnException(String message, Throwable cause) {
        super(message, cause);
    }


}
