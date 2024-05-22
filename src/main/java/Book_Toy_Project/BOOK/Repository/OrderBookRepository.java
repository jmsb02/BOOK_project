package Book_Toy_Project.BOOK.Repository;

import Book_Toy_Project.BOOK.Entity.Book;
import Book_Toy_Project.BOOK.Entity.OrderBook;
import Book_Toy_Project.BOOK.api.BookVO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class OrderBookRepository {

    @PersistenceContext
    private final EntityManager em;

    @Transactional
    public void save(OrderBook orderBook) {
        OrderBook existingOrderBook = findByIsbn(orderBook.getIsbn());
        if (existingOrderBook == null) { //이미 같은 책이 존재하지 않으면
            em.persist(orderBook);
        }
        em.merge(orderBook);
    }

    public OrderBook findByIsbn(String isbn) {
        try {
            String jpql = "SELECT ob FROM OrderBook ob WHERE ob.isbn = :isbn";
            return em.createQuery(jpql, OrderBook.class)
                    .setParameter("isbn", isbn)
                    .getSingleResult();
        } catch (NoResultException e) { //jpql 쿼리 오류 막기 위한 예외 처리
            log.info("NoResultException");
            return null;
        }
    }

    @Transactional
    public void delete(OrderBook orderBook) {
        log.info("orderBook.getIsbn() = {}", orderBook.getIsbn());
        em.remove(orderBook);
    }

    @Transactional
    public void deleteAll() {
        em.createQuery("delete from OrderBook ob", OrderBook.class).getResultList();
    }

    public List<OrderBook> orderBookList() {
        return em.createQuery("select ob from OrderBook ob", OrderBook.class).getResultList();
    }

    public List<OrderBook> findAll() {
        return em.createQuery("select distinct ob from OrderBook ob", OrderBook.class).getResultList();
    }

    public OrderBook findOne(Long id) {
        return em.find(OrderBook.class, id);
    }
}
