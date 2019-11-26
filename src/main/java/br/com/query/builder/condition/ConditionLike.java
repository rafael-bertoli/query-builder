package br.com.query.builder.condition;

import static br.com.query.builder.QueryBuilder.EMPTY;
import static br.com.query.builder.QueryBuilder.SPECIAL_CHARS_REGEX;
import static java.util.Optional.ofNullable;

import java.util.HashMap;
import java.util.Map;

public class ConditionLike implements Condition {

    private static final String CONDITION_LIKE = "%s LIKE :%s";
    private final String source;
    private final Object value;
    private final String parameterPattern;

    ConditionLike(String suffix, String source, Object value) {
        this.source = source;
        this.value = value;
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
        return String.format(CONDITION_LIKE, source, parameterPattern);
    }
}
