package br.com.query.builder.condition;

import static br.com.query.builder.QueryBuilder.EMPTY;
import static br.com.query.builder.QueryBuilder.SPECIAL_CHARS_REGEX;
import static java.util.Objects.nonNull;
import static java.util.Optional.ofNullable;

import java.util.HashMap;
import java.util.Map;

import br.com.query.builder.QueryBuilder;


public class ConditionEquals implements Condition {

    private static final String CONDITION_EQUALS = "%s = :%s";
    private static final String CONDITION_NOT_EQUALS = "%s != :%s";
    private static final String CONDITION_EQUALS_PROPERTY = "%s = %s";
    private static final String CONDITION_NOT_EQUALS_PROPERTY = "%s != %s";
    private final String source;
    private Object value;
    private QueryBuilder queryValue;
    private final boolean isProperty;
    private final boolean not;
    private final String parameterPattern;

    ConditionEquals(String suffix, String source, Object value, boolean not, boolean isProperty) {
        this.source = source;
        this.value = value;
        this.isProperty = isProperty;
        this.not = not;
        this.parameterPattern = source.replaceAll(SPECIAL_CHARS_REGEX, EMPTY).concat(ofNullable(suffix).orElse(EMPTY));
    }
    
    ConditionEquals(String suffix, String source, QueryBuilder queryValue, boolean not, boolean isProperty) {
        this.source = source;
        this.queryValue = queryValue;
        this.isProperty = isProperty;
        this.not = not;
        this.parameterPattern = source.replaceAll(SPECIAL_CHARS_REGEX, EMPTY).concat(ofNullable(suffix).orElse(EMPTY));
    }
    
    @Override
    public Map<String, Object> getParameters() {
        if(isProperty){
            return null;
        } else {
            Map<String, Object> parameters = new HashMap<>();
            if(nonNull(queryValue)){
                parameters.putAll(queryValue.result().getParameters());
            } else {
                parameters.put(parameterPattern, value);
            }
            return parameters;
        }
    }

    @Override
    public String toSqlString() {
        if(isProperty){
            return String.format(not ? CONDITION_NOT_EQUALS_PROPERTY : CONDITION_EQUALS_PROPERTY, source, value);
        } else {
            return String.format(not ? CONDITION_NOT_EQUALS : CONDITION_EQUALS, source, nonNull(value) ? parameterPattern : "("+queryValue.result().getSqlQuery()+")");
        }
    }
}
