package br.com.query.builder.condition;

import java.util.Map;

import br.com.query.builder.Query;
import br.com.query.builder.QueryBuilder;

public class ConditionExists implements Condition {

    private static final String CONDITION_EXISTS = "EXISTS (%s)";
    private static final String CONDITION_NOT_EXISTS = "NOT EXISTS (%s)";
    
    private final String sql;
    private final Map<String, Object> parameters;
    private final boolean not;

    ConditionExists(QueryBuilder query, boolean not) {
        Query result = query.result();
        this.sql = result.getSqlQuery();
        this.parameters = result.getParameters();
        this.not = not;
    }
    
    @Override
    public Map<String, Object> getParameters() {
        return parameters;
    }

    @Override
    public String toSqlString() {
        return String.format(not ? CONDITION_NOT_EXISTS : CONDITION_EXISTS, sql);
    }
}
