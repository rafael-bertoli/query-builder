package br.com.query.builder;

public enum JoinType {

    JOIN("JOIN %s ON %s "),
    LEFT_JOIN("LEFT JOIN %s ON %s "),
    RIGHT_JOIN("RIGHT JOIN %s ON %s "),
    INNER_JOIN("INNER JOIN %s ON %s ");
    
    private final String format;

    private JoinType(String format) {
        this.format = format;
    }

    public String getFormat() {
        return format;
    }
}
