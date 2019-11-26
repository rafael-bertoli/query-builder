package br.com.query.builder.condition;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Disjunction implements Condition {

    private static final String DISJUNCTION_DELIMITER = " OR ";
    private static final String DISJUNCTION = "(%s)";
    private final List<Condition> conditions;
    
    Disjunction(Condition... conditions) {
        this.conditions = Arrays.asList(conditions);
    }
    
    @Override
    public Map<String, Object> getParameters() {
        Map<String, Object> parameters = new HashMap<>();
        conditions.forEach((condition) -> {
            parameters.putAll(condition.getParameters());
        });
        return parameters;
    }

    @Override
    public String toSqlString() {
        return String.format(DISJUNCTION, conditions.stream().map(Condition::toSqlString).collect(Collectors.joining(DISJUNCTION_DELIMITER)));
    }
}
