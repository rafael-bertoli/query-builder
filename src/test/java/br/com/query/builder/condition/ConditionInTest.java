package br.com.query.builder.condition;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class ConditionInTest {
    
    @Test
    public void testGetParameters() {
        ConditionIn condition = new ConditionIn("1", "(test)", 5, false);
        Map<String, Object> expectedParameters =  new HashMap<String, Object>() {
            {
                put("test1", 5);
            }
        };
        assertThat(condition.getParameters(), is(equalTo(expectedParameters)));
    }

    @Test
    public void testConditionInToSqlString() {
        ConditionIn condition = new ConditionIn("1", "test", 5, false);
        String expectedSql = "test IN (:test1)";
        assertThat(condition.toSqlString(), is(equalTo(expectedSql)));
    }

    @Test
    public void testConditionNotInToSqlString() {
        ConditionIn condition = new ConditionIn("1", "test", 5, true);
        String expectedSql = "test NOT IN (:test1)";
        assertThat(condition.toSqlString(), is(equalTo(expectedSql)));
    }
    
}
