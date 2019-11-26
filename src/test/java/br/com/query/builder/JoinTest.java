package br.com.query.builder;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

import br.com.query.builder.condition.Restrictions;

public class JoinTest {

    @Rule
    public ErrorCollector collector = new ErrorCollector();
    
    private final ExecutorService threadpool = Executors.newFixedThreadPool(1);
    
    @Test
    public void testJoin() throws InterruptedException, ExecutionException {
        Future<Query> futureQuery = threadpool.submit(() -> {
            return Join.from(JoinType.JOIN, "shipment_order", Restrictions.eqProperty("field", "field1"))
                .add(Restrictions.eq("field2", 50)).result();
        });
        Query query = futureQuery.get();
        String expectedSql = "JOIN shipment_order ON field = field1 AND field2 = :field21 ";
        Map<String, Object> expectedParameters =  new HashMap<String, Object>() {
            {
                put("field21", 50);
            }
        };
        collector.checkThat(query.getParameters(), is(equalTo(expectedParameters)));
        collector.checkThat(query.getSqlQuery(), is(equalTo(expectedSql)));
    }
    
}
