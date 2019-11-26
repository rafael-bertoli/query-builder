package br.com.query.builder;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class QueryBuilder {

    public static final String LIKE_PATTERN = "%%%s%%";
    private static final String SELECT = "SELECT %s FROM %s ";
    private static final String ORDER_BY = "ORDER BY %s ";
    private static final String LIMIT = "LIMIT %d ";
    private static final String OFFSET = "OFFSET %d ";
    static final String COMMA_DELIMITER = ", ";
    public static final String EMPTY = "";
    public static final String SPECIAL_CHARS_REGEX = "[^a-zA-Z0-9_]";
    
    private final List<String> fields;
    private String table;
    private List<Join> joins;
    private Where where;
    private GroupBy groupBy;
    private List<String> orderBy;
    private Integer limit;
    private Integer offset;

    private QueryBuilder(List<String> fields) {
        this.fields = fields;
    }
    
    private List<Join> getJoins() {
        return isNull(joins) ? joins = new ArrayList<>() : joins;
    }

    private List<String> getOrderBy() {
        return isNull(orderBy) ? orderBy = new ArrayList<>() : orderBy;
    }

    public static QueryBuilder select(String... field) {
        if (isNull(field) || field.length < 1) {
            throw new IllegalArgumentException("To create the query, it is necessary to enter the fields");
        }
        return new QueryBuilder(Arrays.asList(field));
    }

    public QueryBuilder from(String tableSource) {
        this.table = tableSource;
        return this;
    }

    public QueryBuilder join(Join join) {
        getJoins().add(join);
        return this;
    }
    
    public QueryBuilder where(Where where){
        this.where = where;
        return this;
    }
    
    public QueryBuilder group(GroupBy groupBy){
        this.groupBy = groupBy;
        return this;
    }
    
    public QueryBuilder order(String... fields){
        if(nonNull(fields))
            getOrderBy().addAll(Arrays.asList(fields));
        return this;
    }
    
    public QueryBuilder limit(Integer limit){
        this.limit = limit;
        return this;
    }
    
    public QueryBuilder offset(Integer offset){
        this.offset = offset;
        return this;
    }
    
    public Query result(){
        Map<String, Object> parameters = new HashMap<>();
        String joins = buildJoins(parameters);
        String where = buildWhere(parameters);
        String group = buildGroupBy(parameters);
        String query = buildQuery(joins, where, group);
        
        return Query.of(query, parameters);
    }
    
    private String buildQuery(String joins, String where, String group) {
        String sql = String.format(SELECT, fields.stream().collect(Collectors.joining(COMMA_DELIMITER)), table)
                .concat(joins)
                .concat(where)
                .concat(group)
                .concat(buildOrder())
                .concat(buildLimit())
                .concat(buildOffset());
        return sql;
    }

    private String buildJoins(Map<String, Object> parameters) {
        return getJoins()
                .stream()
                .map(join -> {
                    Query joinResult = join.result();
                    parameters.putAll(joinResult.getParameters());
                    return joinResult.getSqlQuery();
                })
                .collect(Collectors.joining());
    }
    
    private String buildWhere(Map<String, Object> parameters) {
        if(isNull(where)) return EMPTY;
        Query whereResult = this.where.result();
        parameters.putAll(whereResult.getParameters());
        return whereResult.getSqlQuery();
    }
    
    private String buildGroupBy(Map<String, Object> parameters) {
        if(isNull(groupBy)) return EMPTY;
        Query groupResult = groupBy.result();
        parameters.putAll(groupResult.getParameters());
        return groupResult.getSqlQuery();
    }
    
    private String buildOrder() {
        String order = getOrderBy().stream().collect(Collectors.joining(COMMA_DELIMITER));
        return order.isEmpty() ? order : String.format(ORDER_BY, order);
    }
    
    private String buildLimit() {
        return isNull(limit) ? EMPTY : String.format(LIMIT, limit);
    }
    
    private String buildOffset() {
        return isNull(offset) ? EMPTY : String.format(OFFSET, offset);
    }
}
