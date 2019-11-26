package br.com.query.builder;

import java.util.Map;

public class Query {

    private final String sqlQuery;
    private final Map<String, Object> parameters;

    private Query(String sqlQuery, Map<String, Object> parameters) {
        this.sqlQuery = sqlQuery;
        this.parameters = parameters;
    }

    public String getSqlQuery() {
        return sqlQuery;
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }
    
    public static Query of(String sqlQuery, Map<String, Object> parameters){
        return new Query(sqlQuery, parameters);
    }
}
