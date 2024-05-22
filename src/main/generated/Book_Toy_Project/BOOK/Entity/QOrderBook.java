package Book_Toy_Project.BOOK.Entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QOrderBook is a Querydsl query type for OrderBook
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrderBook extends EntityPathBase<OrderBook> {

    private static final long serialVersionUID = 1374626629L;

    public static final QOrderBook orderBook = new QOrderBook("orderBook");

    public final StringPath author = createString("author");

    public final NumberPath<Integer> count = createNumber("count", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath image = createString("image");

    public final StringPath isbn = createString("isbn");

    public final StringPath link = createString("link");

    public final StringPath name = createString("name");

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final StringPath pubdate = createString("pubdate");

    public final StringPath publisher = createString("publisher");

    public QOrderBook(String variable) {
        super(OrderBook.class, forVariable(variable));
    }

    public QOrderBook(Path<? extends OrderBook> path) {
        super(path.getType(), path.getMetadata());
    }

    public QOrderBook(PathMetadata metadata) {
        super(OrderBook.class, metadata);
    }

}

