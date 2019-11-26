package br.com.query.builder;

import static br.com.query.builder.Where.AND;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.Optional.ofNullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import br.com.query.builder.condition.Condition;

public class Having {

    private static final String HAVING = "HAVING %s ";
    
    private List<Condition> conditions;

    private Having(List<Condition> conditions) {
        this.conditions = conditions;
    }
    
    private List<Condition> getConditions(){
        return isNull(conditions) ? conditions = new ArrayList<>() : conditions;
    }
    
    public Having add(Condition condition){
        if(nonNull(condition))
            getConditions().add(condition);
        return this;
    }
    
    public static Having of(Condition... conditions){
        if(isNull(conditions)) throw new IllegalArgumentException("A condition is required to initialize having clause.");
        return new Having(new ArrayList<>(Arrays.asList(conditions)));
    }
    
    String buildHaving(Map<String, Object> parameters) {
        String having = getConditions().stream().map(condition -> {
            ofNullable(condition.getParameters()).ifPresent(entry -> parameters.putAll(entry));
            return condition.toSqlString();
        }).collect(Collectors.joining(AND));
        return having.isEmpty() ? having : String.format(HAVING, having);
    }
}
