package br.com.query.builder.condition;

import static br.com.query.builder.QueryBuilder.EMPTY;
import static br.com.query.builder.QueryBuilder.SPECIAL_CHARS_REGEX;
import static java.util.Optional.ofNullable;

import java.util.HashMap;
import java.util.Map;

public class ConditionGreater implements Condition {

    private static final String CONDITION_GREATER = "%s > :%s";
    private static final String CONDITION_GREATER_OR_EQUAL = "%s >= :%s";
    private final String source;
    private final Object value;
    private final boolean isEqual;
    private final String parameterPattern;

    ConditionGreater(String suffix, String source, Object value, boolean isEqual) {
        this.source = source;
        this.value = value;
        this.isEqual = isEqual;
        this.parameterPattern = source.replaceAll(SPECIAL_CHARS_REGEX, EMPTY).concat(ofNullable(suffix).orElse(EMPTY));
    }

    @Override
    public Map<String, Object> getParameters() {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(parameterPattern, value);
        return parameters;
    }

    @Override
    public String toSqlString() {
        return String.format(isEqual ? CONDITION_GREATER_OR_EQUAL : CONDITION_GREATER, source, parameterPattern);
    }
}
