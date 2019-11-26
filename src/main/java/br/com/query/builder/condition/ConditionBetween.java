package br.com.query.builder.condition;

import static br.com.query.builder.QueryBuilder.EMPTY;
import static br.com.query.builder.QueryBuilder.SPECIAL_CHARS_REGEX;
import static java.util.Optional.ofNullable;

import java.util.HashMap;
import java.util.Map;


public class ConditionBetween implements Condition {

    private static final String CONDITION_BETWEEN = "%s BETWEEN :start%s AND :stop%s";
    private final String source;
    private final Object value1;
    private final Object value2;
    private final String parameterPattern;

    ConditionBetween(String suffix, String source, Object value1, Object value2) {
        this.source = source;
        this.value1 = value1;
        this.value2 = value2;
        this.parameterPattern = source.replaceAll(SPECIAL_CHARS_REGEX, EMPTY).concat(ofNullable(suffix).orElse(EMPTY));
    }
    
    @Override
    public Map<String, Object> getParameters() {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("start"+parameterPattern, value1);
        parameters.put("stop"+parameterPattern, value2);
        return parameters;
    }

    @Override
    public String toSqlString() {
        return String.format(CONDITION_BETWEEN, source, parameterPattern, parameterPattern);
    }
}
