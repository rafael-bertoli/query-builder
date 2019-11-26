package br.com.query.builder.condition;

import static br.com.query.builder.QueryBuilder.EMPTY;
import static br.com.query.builder.QueryBuilder.SPECIAL_CHARS_REGEX;
import static java.util.Optional.ofNullable;

import java.util.HashMap;
import java.util.Map;

public class ConditionIn implements Condition {

    private static final String CONDITION_NOT_IN = "%s NOT IN (:%s)";
    private static final String CONDITION_IN = "%s IN (:%s)";
    private final String source;
    private final Object value;
    private final boolean not;
    private final String parameterPattern;

    ConditionIn(String suffix, String source, Object value, boolean not) {
        this.source = source;
        this.value = value;
        this.not = not;
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
        return String.format(not ? CONDITION_NOT_IN : CONDITION_IN, source, parameterPattern);
    }
}
