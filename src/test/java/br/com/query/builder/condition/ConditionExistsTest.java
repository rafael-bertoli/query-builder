package br.com.query.builder.condition;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import br.com.query.builder.QueryBuilder;
import br.com.query.builder.Where;

public class ConditionExistsTest {
    
    @Test
    public void testGetParameters() {
        ConditionExists condition = new ConditionExists(
                QueryBuilder.select("fieldTest")
                    .from("shipment_order")
                    .where(Where.from(new ConditionEquals("1", "test", 123, false, false)))
                , true);
        Map<String, Object> expectedParameters =  new HashMap<String, Object>() {
            {
                put("test1", 123);
            }
        };
        assertThat(condition.getParameters(), is(equalTo(expectedParameters)));
    }

    @Test
    public void testConditionExistsToSqlString() {
        ConditionExists condition = new ConditionExists(
                QueryBuilder.select("fieldTest")
                    .from("shipment_order")
                , false);
        String expectedSql = "EXISTS (SELECT fieldTest FROM shipment_order )";
        assertThat(condition.toSqlString(), is(equalTo(expectedSql)));
    }

    @Test
    public void testConditionNotExistsToSqlString() {
        ConditionExists condition = new ConditionExists(
                QueryBuilder.select("fieldTest")
                    .from("shipment_order")
                , true);
        String expectedSql = "NOT EXISTS (SELECT fieldTest FROM shipment_order )";
        assertThat(condition.toSqlString(), is(equalTo(expectedSql)));
    }
    
}
