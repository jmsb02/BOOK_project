package Book_Toy_Project.BOOK.Entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBookItem is a Querydsl query type for BookItem
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBookItem extends EntityPathBase<BookItem> {

    private static final long serialVersionUID = -293989394L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBookItem bookItem = new QBookItem("bookItem");

    public final StringPath author = createString("author");

    public final Book_Toy_Project.BOOK.api.QBookVO bookVO;

    public final StringPath description = createString("description");

    public final StringPath discount = createString("discount");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath image = createString("image");

    public final StringPath isbn = createString("isbn");

    public final StringPath link = createString("link");

    public final StringPath pubdate = createString("pubdate");

    public final StringPath publisher = createString("publisher");

    public final StringPath title = createString("title");

    public QBookItem(String variable) {
        this(BookItem.class, forVariable(variable), INITS);
    }

    public QBookItem(Path<? extends BookItem> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBookItem(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBookItem(PathMetadata metadata, PathInits inits) {
        this(BookItem.class, metadata, inits);
    }

    public QBookItem(Class<? extends BookItem> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.bookVO = inits.isInitialized("bookVO") ? new Book_Toy_Project.BOOK.api.QBookVO(forProperty("bookVO")) : null;
    }

}

