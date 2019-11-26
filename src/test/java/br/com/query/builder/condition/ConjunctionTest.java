package br.com.query.builder.condition;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class ConjunctionTest {
    
    @Test
    public void testGetParameters() {
        Conjunction condition = new Conjunction(
                    new ConditionEquals("1", "so_id", 5, false, false),
                    new ConditionEquals("2", "so_number", 15, false, false)
        );
        Map<String, Object> expectedParameters =  new HashMap<String, Object>() {
            {
                put("so_id1", 5);
                put("so_number2", 15);
            }
        };
        assertThat(condition.getParameters(), is(equalTo(expectedParameters)));
    }

    @Test
    public void testToSqlString() {
        Conjunction condition = new Conjunction(
                    new ConditionEquals("1", "so_id", 5, false, false),
                    new ConditionEquals("2", "so_number", 15, false, false)
        );
        String expectedSql = "(so_id = :so_id1 AND so_number = :so_number2)";
        assertThat(condition.toSqlString(), is(equalTo(expectedSql)));
    }
    
}
