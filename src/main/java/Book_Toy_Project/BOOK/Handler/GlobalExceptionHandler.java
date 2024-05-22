package Book_Toy_Project.BOOK.Handler;

import Book_Toy_Project.BOOK.Exception.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalExceptionHandler {

    //중복 isbn 오류 검증
    @ExceptionHandler(DuplicateIsbnException.class)
    private ModelAndView handleDuplicateIsbnException(DuplicateIsbnException e) {
        ModelAndView modelAndView = new ModelAndView("error/error");
        modelAndView.addObject("errorMessage", e.getMessage());
        return modelAndView;
    }

    @ExceptionHandler(IllegalStateException.class)
    private ModelAndView handleIllegalStateException(IllegalStateException e) {
        ModelAndView modelAndView = new ModelAndView("error/error");
        modelAndView.addObject("errorMessage", e.getMessage());
        return modelAndView;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    private ModelAndView handleIllegalArgumentException(IllegalArgumentException e) {
        ModelAndView modelAndView = new ModelAndView("error/error");
        modelAndView.addObject("errorMessage", e.getMessage());
        return modelAndView;
    }

    @ExceptionHandler(Exception.class)
    private ModelAndView handleException(Exception e) {
        ModelAndView modelAndView = new ModelAndView("error/error");
        modelAndView.addObject("errorMessage", e.getMessage());
        return modelAndView;
    }

    @ExceptionHandler(NoSuchElementException.class)
    private ModelAndView handleNoSuchElementException(NoSuchElementException e) {
        ModelAndView modelAndView = new ModelAndView("error/error");
        modelAndView.addObject("errorMessage", e.getMessage());
        return modelAndView;
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    private ModelAndView handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        ModelAndView modelAndView = new ModelAndView("error/error");
        modelAndView.addObject("errorMessage", e.getMessage());
        return modelAndView;
    }

    @ExceptionHandler(EntityNotFoundException.class)
    private ModelAndView handleEntityNotFoundException(EntityNotFoundException e) {
        ModelAndView modelAndView = new ModelAndView("error/error");
        modelAndView.addObject("errorMessage", e.getMessage());
        return modelAndView;
    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    private ModelAndView handleUsernameAlreadyExistsException(UsernameAlreadyExistsException e) {
        ModelAndView modelAndView = new ModelAndView("error/error");
        modelAndView.addObject("errorMessage", e.getMessage());
        return modelAndView;
    }

    @ExceptionHandler(DuplicateOrderException.class)
    private ModelAndView handleDuplicateOrderException(DuplicateOrderException e) {
        ModelAndView modelAndView = new ModelAndView("error/error");
        modelAndView.addObject("errorMessage", e.getMessage());
        return modelAndView;
    }

    @ExceptionHandler(DataAccessException.class)
    private ModelAndView handleDataAccessException(DataAccessException e) {
        ModelAndView modelAndView = new ModelAndView("error/error");
        modelAndView.addObject("errorMessage", e.getMessage());
        return modelAndView;
    }

    @ExceptionHandler(OrderBooknotFoundException.class)
    private ModelAndView handleOrderBooknotFoundException(OrderBooknotFoundException e) {
        ModelAndView modelAndView = new ModelAndView("error/error");
        modelAndView.addObject("errorMessage", e.getMessage());
        return modelAndView;
    }

    @ExceptionHandler(DuplicateMemberException.class)
    private ModelAndView handleDuplicateMemberException(DuplicateMemberException e) {
        ModelAndView modelAndView = new ModelAndView("error/error");
        modelAndView.addObject("errorMessage", e.getMessage());
        return modelAndView;
    }


}


