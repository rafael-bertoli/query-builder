package br.com.query.builder.condition;

import java.util.concurrent.atomic.AtomicInteger;

import br.com.query.builder.QueryBuilder;

public class Restrictions {
    
    private static final ThreadLocal<AtomicInteger> count = ThreadLocal.withInitial(() -> new AtomicInteger(0));
    
    public static Condition and(Condition condition1, Condition condition2) {
        return new Conjunction(condition1, condition2);
    }
    
    public static Condition or(Condition condition1, Condition condition2) {
        return new Disjunction(condition1, condition2);
    }
    
    public static Condition exists(QueryBuilder query) {
        return new ConditionExists(query, false);
    }
    
    public static Condition notExists(QueryBuilder query) {
        return new ConditionExists(query, true);
    }

    public static Condition between(String propertySource, Object value1, Object value2) {
        return new ConditionBetween(String.valueOf(count.get().incrementAndGet()), propertySource, value1, value2);
    }
    
    public static Condition eq(String propertySource, Object value) {
        return new ConditionEquals(String.valueOf(count.get().incrementAndGet()), propertySource, value, false, false);
    }
    
    public static Condition eq(String propertySource, QueryBuilder query) {
        return new ConditionEquals(String.valueOf(count.get().incrementAndGet()), propertySource, query, false, false);
    }
    
    public static Condition eqProperty(String propertySource, Object value) {
        return new ConditionEquals(null, propertySource, value, false, true);
    }
    
    public static Condition ne(String propertySource, Object value) {
        return new ConditionEquals(String.valueOf(count.get().incrementAndGet()), propertySource, value, true, false);
    }
    
    public static Condition neProperty(String propertySource, Object value) {
        return new ConditionEquals(null, propertySource, value, true, true);
    }
    
    public static Condition like(String propertySource, Object value) {
        return new ConditionLike(String.valueOf(count.get().incrementAndGet()), propertySource, value);
    }

    public static Condition gt(String propertySource, Object value) {
        return new ConditionGreater(String.valueOf(count.get().incrementAndGet()), propertySource, value, false);
    }
    
    public static Condition ge(String propertySource, Object value) {
        return new ConditionGreater(String.valueOf(count.get().incrementAndGet()), propertySource, value, true);
    }
    
    public static Condition lt(String propertySource, Object value) {
        return new ConditionLess(String.valueOf(count.get().incrementAndGet()), propertySource, value, false);
    }
    
    public static Condition le(String propertySource, Object value) {
        return new ConditionLess(String.valueOf(count.get().incrementAndGet()), propertySource, value, true);
    }
    
    public static Condition in(String propertySource, Object value) {
        return new ConditionIn(String.valueOf(count.get().incrementAndGet()),propertySource, value, false);
    }
    
    public static Condition notIn(String propertySource, Object value) {
        return new ConditionIn(String.valueOf(count.get().incrementAndGet()),propertySource, value, true);
    }
    
    public static Condition isNull(String propertySource) {
        return new ConditionNull(propertySource, false);
    }
    
    public static Condition isNotNull(String propertySource) {
        return new ConditionNull(propertySource, true);
    }
    
    public static Condition isEmpty(String propertySource) {
        return new ConditionEmpty(propertySource, false);
    }
    
    public static Condition isNotEmpty(String propertySource) {
        return new ConditionEmpty(propertySource, true);
    }
}
