package br.com.query.builder.condition;

import java.util.Map;

public class ConditionNull implements Condition {

    private static final String CONDITION_NOT_NULL = "%s IS NOT NULL";
    private static final String CONDITION_NULL = "%s IS NULL";
    private final String source;
    private final boolean not;

    ConditionNull(String source, boolean not) {
        this.source = source;
        this.not = not;
    }
    
    @Override
    public Map<String, Object> getParameters() {
        return null;
    }

    @Override
    public String toSqlString() {
        return String.format(not ? CONDITION_NOT_NULL : CONDITION_NULL, source);
    }
}
