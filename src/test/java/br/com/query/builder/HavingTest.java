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

public class HavingTest {
    
    @Rule
    public ErrorCollector collector = new ErrorCollector();
    
    private final ExecutorService threadpool = Executors.newFixedThreadPool(1);
    
    @Test
    public void testHaving() throws InterruptedException, ExecutionException {
        Future<Query> futureQuery = threadpool.submit(() -> {
            Map<String, Object> parameters = new HashMap<>();
            String having = Having.of(Restrictions.eq("field1", "ABC"))
                    .add(Restrictions.ne("field2", "QQQ"))
                    .buildHaving(parameters);
            return Query.of(having, parameters);
        });
        Query query = futureQuery.get();
        String expectedSql = "HAVING field1 = :field11 AND field2 != :field22 ";
        Map<String, Object> expectedParameters =  new HashMap<String, Object>() {
            {
                put("field11", "ABC");
                put("field22", "QQQ");
            }
        };
        collector.checkThat(query.getParameters(), is(equalTo(expectedParameters)));
        collector.checkThat(query.getSqlQuery(), is(equalTo(expectedSql)));
    }

}
