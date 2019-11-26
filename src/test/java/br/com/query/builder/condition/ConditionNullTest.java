package br.com.query.builder.condition;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class ConditionNullTest {

    @Test
    public void testGetParameters() {
        ConditionNull condition = new ConditionNull("test", false);
        assertNull(condition.getParameters());
    }

    @Test
    public void testConditionNullToSqlString() {
        ConditionNull condition = new ConditionNull("test", false);
        String expectedSql = "test IS NULL";
        assertThat(condition.toSqlString(), is(equalTo(expectedSql)));
    }
    
    @Test
    public void testConditionNotNullToSqlString() {
        ConditionNull condition = new ConditionNull("test", true);
        String expectedSql = "test IS NOT NULL";
        assertThat(condition.toSqlString(), is(equalTo(expectedSql)));
    }
    
}
