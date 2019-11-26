package br.com.query.builder;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

public class GroupByTest {
    
    @Test
    public void testGroupBy() {
        Query query = GroupBy.of("field1", "field2").result();
        String expectedSql = "GROUP BY field1, field2 ";
        assertThat(query.getSqlQuery(), is(equalTo(expectedSql)));
    }
    
}
