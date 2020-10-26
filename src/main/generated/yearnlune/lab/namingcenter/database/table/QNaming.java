package yearnlune.lab.namingcenter.database.table;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QNaming is a Querydsl query type for Naming
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QNaming extends EntityPathBase<Naming> {

    private static final long serialVersionUID = -1783242680L;

    public static final QNaming naming = new QNaming("naming");

    public final DateTimePath<java.sql.Timestamp> createdAt = createDateTime("createdAt", java.sql.Timestamp.class);

    public final StringPath description = createString("description");

    public final NumberPath<Integer> idx = createNumber("idx", Integer.class);

    public final StringPath keyword = createString("keyword");

    public final StringPath name = createString("name");

    public final DateTimePath<java.sql.Timestamp> updatedAt = createDateTime("updatedAt", java.sql.Timestamp.class);

    public QNaming(String variable) {
        super(Naming.class, forVariable(variable));
    }

    public QNaming(Path<? extends Naming> path) {
        super(path.getType(), path.getMetadata());
    }

    public QNaming(PathMetadata metadata) {
        super(Naming.class, metadata);
    }

}

