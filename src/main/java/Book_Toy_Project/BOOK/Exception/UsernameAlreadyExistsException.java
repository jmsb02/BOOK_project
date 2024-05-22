package Book_Toy_Project.BOOK.Exception;

// RuntimeException -> Unchecked Exception -> rollback
public class UsernameAlreadyExistsException extends RuntimeException{

    public UsernameAlreadyExistsException() {
    }

    public UsernameAlreadyExistsException(String message) {
        super(message);
    }
}
