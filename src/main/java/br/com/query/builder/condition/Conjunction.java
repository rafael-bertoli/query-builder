package br.com.query.builder.condition;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Conjunction implements Condition {

    private static final String CONJUNCTION_DELIMITER = " AND ";
    private static final String CONJUNCTION = "(%s)";
    private final List<Condition> conditions;
    
    Conjunction(Condition... conditions) {
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
        return String.format(CONJUNCTION, conditions.stream().map(Condition::toSqlString).collect(Collectors.joining(CONJUNCTION_DELIMITER)));
    }
}
