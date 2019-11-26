package br.com.query.builder;

import static br.com.query.builder.QueryBuilder.EMPTY;
import static br.com.query.builder.Where.AND;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.StringUtils.isEmpty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import br.com.query.builder.condition.Condition;

public class Join {
    
    private final JoinType type;
    private final String table;
    private final List<Condition> conditions;

    Join(JoinType type, String table, Condition... conditions) {
        this.type = type;
        this.table = table;
        this.conditions = new ArrayList<>(Arrays.asList(conditions));
    }
    
    public static Join from(JoinType type, String table, Condition... conditions) {
        if(isNull(conditions) || isEmpty(table)) throw new IllegalArgumentException("A table and condition is required to create join clause.");
        return new Join(type, table, conditions);
    }
    
    public Join add(Condition condition) {
        if(nonNull(condition))
            conditions.add(condition);
        return this;
    }
    
    Query result() {
        Map<String, Object> parameters = new HashMap<>();
        String join = buildJoin(parameters);
        return Query.of(join, parameters);
    }

    private String buildJoin(Map<String, Object> parameters) {
        String joinConditions = conditions.stream().map(condition -> {
            ofNullable(condition.getParameters()).ifPresent(entry -> parameters.putAll(entry));
            return condition.toSqlString();
        }).collect(Collectors.joining(AND));
        return joinConditions.isEmpty() ? EMPTY : String.format(type.getFormat(), table, joinConditions);
    }
}
