package br.com.query.builder.condition;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;


public class ConditionBetweenTest {

    @Test
    public void testGetParameters() { 
        ConditionBetween condition = new ConditionBetween("1", "test", 1, 2);
        Map<String, Object> expectedParameters = new HashMap<String, Object>() {
            {
                put("starttest1", 1);
                put("stoptest1", 2);
            }
        };
        assertThat(condition.getParameters(), is(equalTo(expectedParameters)));
    }

    @Test
    public void testToSqlString() {
        ConditionBetween condition = new ConditionBetween("1", "test", 1, 2);
        String expectedSql = "test BETWEEN :starttest1 AND :stoptest1";
        assertThat(condition.toSqlString(), is(equalTo(expectedSql)));
    }
    
}
