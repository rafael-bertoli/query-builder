package br.com.query.builder.condition;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class ConditionGreaterTest {
    
    @Test
    public void testGetParameters() {
        ConditionGreater condition = new ConditionGreater("1", "(test)", 5, false);
        Map<String, Object> expectedParameters =  new HashMap<String, Object>() {
            {
                put("test1", 5);
            }
        };
        assertThat(condition.getParameters(), is(equalTo(expectedParameters)));
    }

    @Test
    public void testConditionGreaterToSqlString() {
        ConditionGreater condition = new ConditionGreater("1", "test", 5, false);
        String expectedSql = "test > :test1";
        assertThat(condition.toSqlString(), is(equalTo(expectedSql)));
    }
    
    @Test
    public void testConditionGreaterOrEqualToSqlString() {
        ConditionGreater condition = new ConditionGreater("1", "test", 5, true);
        String expectedSql = "test >= :test1";
        assertThat(condition.toSqlString(), is(equalTo(expectedSql)));
    }
    
}
