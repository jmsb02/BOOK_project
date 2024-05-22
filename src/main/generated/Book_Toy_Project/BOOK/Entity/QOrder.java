package Book_Toy_Project.BOOK.Entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QOrder is a Querydsl query type for Order
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrder extends EntityPathBase<Order> {

    private static final long serialVersionUID = -1320488388L;

    public static final QOrder order = new QOrder("order1");

    public final StringPath author = createString("author");

    public final StringPath cardHolder = createString("cardHolder");

    public final StringPath cardNumber = createString("cardNumber");

    public final NumberPath<Integer> count = createNumber("count", Integer.class);

    public final StringPath cvv = createString("cvv");

    public final StringPath expiryDate = createString("expiryDate");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath image = createString("image");

    public final StringPath isbn = createString("isbn");

    public final StringPath link = createString("link");

    public final StringPath name = createString("name");

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final StringPath pubdate = createString("pubdate");

    public final StringPath publisher = createString("publisher");

    public QOrder(String variable) {
        super(Order.class, forVariable(variable));
    }

    public QOrder(Path<? extends Order> path) {
        super(path.getType(), path.getMetadata());
    }

    public QOrder(PathMetadata metadata) {
        super(Order.class, metadata);
    }

}

