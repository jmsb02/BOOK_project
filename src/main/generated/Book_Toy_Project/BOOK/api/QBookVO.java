package Book_Toy_Project.BOOK.api;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBookVO is a Querydsl query type for BookVO
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBookVO extends EntityPathBase<BookVO> {

    private static final long serialVersionUID = -441951025L;

    public static final QBookVO bookVO = new QBookVO("bookVO");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<Book_Toy_Project.BOOK.Entity.BookItem, Book_Toy_Project.BOOK.Entity.QBookItem> items = this.<Book_Toy_Project.BOOK.Entity.BookItem, Book_Toy_Project.BOOK.Entity.QBookItem>createList("items", Book_Toy_Project.BOOK.Entity.BookItem.class, Book_Toy_Project.BOOK.Entity.QBookItem.class, PathInits.DIRECT2);

    public QBookVO(String variable) {
        super(BookVO.class, forVariable(variable));
    }

    public QBookVO(Path<? extends BookVO> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBookVO(PathMetadata metadata) {
        super(BookVO.class, metadata);
    }

}

