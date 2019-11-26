package br.com.query.builder.condition;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class ConditionEqualsTest {

    @Test
    public void testGetParametersNotProperty() {
        ConditionEquals condition = new ConditionEquals("1", "(test)", 5, false, false);
        Map<String, Object> expectedParameters =  new HashMap<String, Object>() {
            {
                put("test1", 5);
            }
        };
        assertThat(condition.getParameters(), is(equalTo(expectedParameters)));
    }
    
    @Test
    public void testGetParametersProperty() {
        ConditionEquals condition = new ConditionEquals("1", "test", "sov_number", false, true);
        assertNull(condition.getParameters());
    }

    @Test
    public void testConditionEqualsToSqlString() {
        ConditionEquals condition = new ConditionEquals("1", "test", 5, false, false);
        String expectedSql = "test = :test1";
        assertThat(condition.toSqlString(), is(equalTo(expectedSql)));
    }
    
    @Test
    public void testConditionNotEqualsToSqlString() {
        ConditionEquals condition = new ConditionEquals("1", "test", 5, true, false);
        String expectedSql = "test != :test1";
        assertThat(condition.toSqlString(), is(equalTo(expectedSql)));
    }
    
    @Test
    public void testPropertyEqualsToSqlString() {
        ConditionEquals condition = new ConditionEquals("1", "test", "sov_id", false, true);
        String expectedSql = "test = sov_id";
        assertThat(condition.toSqlString(), is(equalTo(expectedSql)));
    }
    
    @Test
    public void testPropertyNotEqualsToSqlString() {
        ConditionEquals condition = new ConditionEquals("1", "test", "sov_id", true, true);
        String expectedSql = "test != sov_id";
        assertThat(condition.toSqlString(), is(equalTo(expectedSql)));
    }
}
