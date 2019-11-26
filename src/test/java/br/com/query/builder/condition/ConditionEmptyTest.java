package br.com.query.builder.condition;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class ConditionEmptyTest {
    
    @Test
    public void testGetParameters() {
        ConditionEmpty condition = new ConditionEmpty("test", true);
        assertNull(condition.getParameters());
    }

    @Test
    public void testToSqlStringEmpty() {
        ConditionEmpty condition = new ConditionEmpty("test", false);
        String expectedSql = "test = ''";
        assertThat(condition.toSqlString(), is(equalTo(expectedSql)));
    }
    
    @Test
    public void testToSqlStringNotEmpty() {
        ConditionEmpty condition = new ConditionEmpty("test", true);
        String expectedSql = "test != ''";
        assertThat(condition.toSqlString(), is(equalTo(expectedSql)));
    }
    
}
