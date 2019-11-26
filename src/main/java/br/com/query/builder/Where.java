package br.com.query.builder;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.Optional.ofNullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import br.com.query.builder.condition.Condition;

public class Where {
    
    static final String AND = " AND ";
    private static final String WHERE = "WHERE %s ";
    
    private final List<Condition> conditions;

    private Where(Condition condition) {
        this.conditions = new ArrayList<>(Arrays.asList(condition));
    }
    
    public static Where from(Condition condition){
        if(isNull(condition)) throw new IllegalArgumentException("A condition is required to initialize where clause.");
        return new Where(condition);
    }
    
    public Where add(Condition condition){
        if(nonNull(condition))
            conditions.add(condition);
        return this;
    }
    
    Query result(){
        Map<String, Object> parameters = new HashMap<>();
        String where = buildWhere(parameters);
        return Query.of(where, parameters);
    }

    private String buildWhere(Map<String, Object> parameters) {
        String where = conditions.stream().map(condition -> {
            ofNullable(condition.getParameters()).ifPresent(entry -> parameters.putAll(entry));
            return condition.toSqlString();
        }).collect(Collectors.joining(AND));
        return where.isEmpty() ? where : String.format(WHERE, where);
    }
}
