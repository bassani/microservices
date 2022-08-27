package org.bassani.examplemodellib.domain.entity.mongodb.operation;

import org.bson.Document;

import java.util.Arrays;

public class CommonOperationFactory {

    private CommonOperationFactory() { throw new UnsupportedOperationException();}

    public static Document roundHalfUp(Document document) {
        return conditional(
                        greaterThanOrEquals(subtract(document, floor(document)), 0.5),
                        ceil(document),
                        floor(document)
                );
    }

    public static Document roundHalfDown(Document document) {
        return conditional(
                greaterThanOrEquals(subtract(document, floor(document)), 0.6),
                ceil(document),
                floor(document)
        );

    }

    private static Document greaterThanOrEquals(Object arg1, Object arg2) {
        return new Document("$gte", Arrays.asList(arg1, arg2));
    }

    public static Document floor(Document reference) {
        return new Document("$floor", reference);
    }

    public static Document ceil(Document reference) {
        return new Document("$ceil", reference);
    }

    public static Document divide(Object... references) {
        return new Document("$divide", Arrays.asList(references));
    }

    public static Document multiply(Object... references) { return new Document("$multiply", Arrays.asList(references));
    }

    public static Document round(Object reference, Integer scale) {
        return new Document("$round", Arrays.asList(reference, scale));
    }

    public static Document subtract(Object... references) { return new Document("$subtract", Arrays.asList(references));
    }

    public static Document add(Object... references) { return new Document("$add", Arrays.asList(references));
    }

    public static Document toInt(Document reference) {return new Document("$toInt", reference);}


    public static Document equalTo(Object reference, Object equal) {
        return new Document("$eq", Arrays.asList(reference, equal));
    }

    public static Document isNumber(Object reference) {
        return new Document("$isNumber", reference);
    }

    public static Document conditional(Document conditionalOperator, Object thenOp, Object elseOp) {
        Document ifOp = new Document("if",
                conditionalOperator)
                .append("then", thenOp)
                .append("else", elseOp);
        return new Document("$cond",ifOp);
    }
}
