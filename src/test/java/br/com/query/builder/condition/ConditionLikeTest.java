package br.com.query.builder.condition;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class ConditionLikeTest {

    @Test
    public void testGetParameters() {
        ConditionLike condition = new ConditionLike("1", "(test)", 5);
        Map<String, Object> expectedParameters =  new HashMap<String, Object>() {
            {
                put("test1", 5);
            }
        };
        assertThat(condition.getParameters(), is(equalTo(expectedParameters)));
    }

    @Test
    public void testToSqlString() {
        ConditionLike condition = new ConditionLike("1", "test", 5);
        String expectedSql = "test LIKE :test1";
        assertThat(condition.toSqlString(), is(equalTo(expectedSql)));
    }
    
}
