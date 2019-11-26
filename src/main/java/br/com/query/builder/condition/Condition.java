package br.com.query.builder.condition;

import java.util.Map;

public interface Condition {

    String toSqlString();
    
    Map<String, Object> getParameters();
    
}
