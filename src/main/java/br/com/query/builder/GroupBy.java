package br.com.query.builder;

import static br.com.query.builder.QueryBuilder.COMMA_DELIMITER;
import static br.com.query.builder.QueryBuilder.EMPTY;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GroupBy {
    
    private static final String GROUP_BY = "GROUP BY %s ";
    
    private final List<String> fields;
    private Having having;

    private GroupBy(List<String> fields) {
        this.fields = fields;
    }
    
    public static GroupBy of(String... fields){
        if(isNull(fields)) throw new IllegalArgumentException("At least one field is required to create a group by clause.");
        return new GroupBy(Arrays.asList(fields));
    }
    
    public GroupBy having(Having having){
        this.having = having;
        return this;
    }
    
    Query result(){
        Map<String, Object> parameters = new HashMap<>();
        String having = nonNull(this.having) ? this.having.buildHaving(parameters) : EMPTY;
        String sql = String.format(GROUP_BY, fields.stream().collect(Collectors.joining(COMMA_DELIMITER))).concat(having);
        
        return Query.of(sql, parameters);
    }

}
