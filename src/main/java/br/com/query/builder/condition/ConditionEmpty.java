package br.com.query.builder.condition;

import java.util.Map;

public class ConditionEmpty implements Condition {

    private static final String CONDITION_NOT_EMPTY = "%s != ''";
    private static final String CONDITION_EMPTY = "%s = ''";
    private final String source;
    private final boolean not;

    ConditionEmpty(String source, boolean not) {
        this.source = source;
        this.not = not;
    }
    
    @Override
    public Map<String, Object> getParameters() {
        return null;
    }

    @Override
    public String toSqlString() {
        return String.format(not ? CONDITION_NOT_EMPTY : CONDITION_EMPTY, source);
    }
}
