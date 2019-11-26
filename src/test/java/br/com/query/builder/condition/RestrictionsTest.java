package br.com.query.builder.condition;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

import br.com.query.builder.QueryBuilder;
import br.com.query.builder.Where;

public class RestrictionsTest {
    
    @Rule
    public ErrorCollector collector = new ErrorCollector();
    private final ExecutorService threadpool = Executors.newFixedThreadPool(20);
    
    @Test
    public void testAnd() throws InterruptedException, ExecutionException {
        Future<Condition> futureCondition = threadpool.submit(() -> {
            return Restrictions.and(Restrictions.eq("field1", 5), Restrictions.eq("field2", 10));
        });
        Condition condition = futureCondition.get();
        Condition conditionExpected = new Conjunction(
                new ConditionEquals("1", "field1", 5, false, false),
                new ConditionEquals("2", "field2", 10, false, false)
        );

        collector.checkThat(condition.getParameters(), is(equalTo(conditionExpected.getParameters())));
        collector.checkThat(condition.toSqlString(), is(equalTo(conditionExpected.toSqlString())));
    }

    @Test
    public void testOr() throws InterruptedException, ExecutionException {
        Future<Condition> futureCondition = threadpool.submit(() -> {
            return Restrictions.or(Restrictions.eq("field1", 5), Restrictions.eq("field2", 10));
        });
        Condition condition = futureCondition.get();
        Condition conditionExpected = new Disjunction(
                new ConditionEquals("1", "field1", 5, false, false),
                new ConditionEquals("2", "field2", 10, false, false)
        );

        collector.checkThat(condition.getParameters(), is(equalTo(conditionExpected.getParameters())));
        collector.checkThat(condition.toSqlString(), is(equalTo(conditionExpected.toSqlString())));
    }

    @Test
    public void testExists() throws InterruptedException, ExecutionException {
        Future<Condition> futureCondition = threadpool.submit(() -> {
            return Restrictions.exists(QueryBuilder.select("fieldTest")
                    .from("shipment_order")
                    .where(Where.from(Restrictions.eq("field1", 10))));
        });
        Condition condition = futureCondition.get();
        Condition conditionExpected = new ConditionExists(
                QueryBuilder.select("fieldTest")
                    .from("shipment_order")
                    .where(Where.from(new ConditionEquals("1", "field1", 10, false, false))),
                false
        );

        collector.checkThat(condition.getParameters(), is(equalTo(conditionExpected.getParameters())));
        collector.checkThat(condition.toSqlString(), is(equalTo(conditionExpected.toSqlString())));
    }

    @Test
    public void testNotExists() throws ExecutionException, InterruptedException {
        Future<Condition> futureCondition = threadpool.submit(() -> {
            return Restrictions.notExists(QueryBuilder.select("fieldTest")
                    .from("shipment_order")
                    .where(Where.from(Restrictions.eq("field1", 10))));
        });
        Condition condition = futureCondition.get();
        Condition conditionExpected = new ConditionExists(
                QueryBuilder.select("fieldTest")
                    .from("shipment_order")
                    .where(Where.from(new ConditionEquals("1", "field1", 10, false, false))),
                true
        );

        collector.checkThat(condition.getParameters(), is(equalTo(conditionExpected.getParameters())));
        collector.checkThat(condition.toSqlString(), is(equalTo(conditionExpected.toSqlString())));
    }

    @Test
    public void testBetween() throws InterruptedException, ExecutionException {
        Future<Condition> futureCondition = threadpool.submit(() -> {
            return Restrictions.between("field1", 5, 10);
        });
        Condition condition = futureCondition.get();
        Condition conditionExpected = new ConditionBetween("1", "field1", 5, 10);

        collector.checkThat(condition.getParameters(), is(equalTo(conditionExpected.getParameters())));
        collector.checkThat(condition.toSqlString(), is(equalTo(conditionExpected.toSqlString())));
    }

    @Test
    public void testEq() throws InterruptedException, ExecutionException {
        Future<Condition> futureCondition = threadpool.submit(() -> {
            return Restrictions.eq("field1", 5);
        });
        Condition condition = futureCondition.get();
        Condition conditionExpected = new ConditionEquals("1", "field1", 5, false, false);

        collector.checkThat(condition.getParameters(), is(equalTo(conditionExpected.getParameters())));
        collector.checkThat(condition.toSqlString(), is(equalTo(conditionExpected.toSqlString())));
    }

    @Test
    public void testEqProperty() throws InterruptedException, ExecutionException {
        Future<Condition> futureCondition = threadpool.submit(() -> {
            return Restrictions.eqProperty("field1", "field2");
        });
        Condition condition = futureCondition.get();
        Condition conditionExpected = new ConditionEquals("1", "field1", "field2", false, true);

        collector.checkThat(condition.getParameters(), is(equalTo(conditionExpected.getParameters())));
        collector.checkThat(condition.toSqlString(), is(equalTo(conditionExpected.toSqlString())));
    }

    @Test
    public void testNe() throws InterruptedException, ExecutionException {
        Future<Condition> futureCondition = threadpool.submit(() -> {
            return Restrictions.ne("field1", 5);
        });
        Condition condition = futureCondition.get();
        Condition conditionExpected = new ConditionEquals("1", "field1", 5, true, false);

        collector.checkThat(condition.getParameters(), is(equalTo(conditionExpected.getParameters())));
        collector.checkThat(condition.toSqlString(), is(equalTo(conditionExpected.toSqlString())));
    }

    @Test
    public void testNeProperty() throws InterruptedException, ExecutionException {
        Future<Condition> futureCondition = threadpool.submit(() -> {
            return Restrictions.neProperty("field1", "field2");
        });
        Condition condition = futureCondition.get();
        Condition conditionExpected = new ConditionEquals("1", "field1", "field2", true, true);

        collector.checkThat(condition.getParameters(), is(equalTo(conditionExpected.getParameters())));
        collector.checkThat(condition.toSqlString(), is(equalTo(conditionExpected.toSqlString())));
    }

    @Test
    public void testLike() throws InterruptedException, ExecutionException {
        Future<Condition> futureCondition = threadpool.submit(() -> {
            return Restrictions.like("field1", "123");
        });
        Condition condition = futureCondition.get();
        Condition conditionExpected = new ConditionLike("1", "field1", "123");

        collector.checkThat(condition.getParameters(), is(equalTo(conditionExpected.getParameters())));
        collector.checkThat(condition.toSqlString(), is(equalTo(conditionExpected.toSqlString())));
    }

    @Test
    public void testGt() throws InterruptedException, ExecutionException {
        Future<Condition> futureCondition = threadpool.submit(() -> {
            return Restrictions.gt("field1", 10);
        });
        Condition condition = futureCondition.get();
        Condition conditionExpected = new ConditionGreater("1", "field1", 10, false);

        collector.checkThat(condition.getParameters(), is(equalTo(conditionExpected.getParameters())));
        collector.checkThat(condition.toSqlString(), is(equalTo(conditionExpected.toSqlString())));
    }

    @Test
    public void testGe() throws InterruptedException, ExecutionException {
        Future<Condition> futureCondition = threadpool.submit(() -> {
            return Restrictions.ge("field1", 10);
        });
        Condition condition = futureCondition.get();
        Condition conditionExpected = new ConditionGreater("1", "field1", 10, true);

        collector.checkThat(condition.getParameters(), is(equalTo(conditionExpected.getParameters())));
        collector.checkThat(condition.toSqlString(), is(equalTo(conditionExpected.toSqlString())));
    }

    @Test
    public void testLt() throws InterruptedException, ExecutionException {
        Future<Condition> futureCondition = threadpool.submit(() -> {
            return Restrictions.lt("field1", 111);
        });
        Condition condition = futureCondition.get();
        Condition conditionExpected = new ConditionLess("1", "field1", 111, false);

        collector.checkThat(condition.getParameters(), is(equalTo(conditionExpected.getParameters())));
        collector.checkThat(condition.toSqlString(), is(equalTo(conditionExpected.toSqlString())));
    }

    @Test
    public void testLe() throws InterruptedException, ExecutionException {
        Future<Condition> futureCondition = threadpool.submit(() -> {
            return Restrictions.le("field1", 111);
        });
        Condition condition = futureCondition.get();
        Condition conditionExpected = new ConditionLess("1", "field1", 111, true);

        collector.checkThat(condition.getParameters(), is(equalTo(conditionExpected.getParameters())));
        collector.checkThat(condition.toSqlString(), is(equalTo(conditionExpected.toSqlString())));
    }

    @Test
    public void testIn() throws InterruptedException, ExecutionException {
        Future<Condition> futureCondition = threadpool.submit(() -> {
            return Restrictions.in("field1", Arrays.asList(1,2,3,4));
        });
        Condition condition = futureCondition.get();
        Condition conditionExpected = new ConditionIn("1", "field1", Arrays.asList(1,2,3,4), false);

        collector.checkThat(condition.getParameters(), is(equalTo(conditionExpected.getParameters())));
        collector.checkThat(condition.toSqlString(), is(equalTo(conditionExpected.toSqlString())));
    }

    @Test
    public void testNotIn() throws InterruptedException, ExecutionException {
        Future<Condition> futureCondition = threadpool.submit(() -> {
            return Restrictions.notIn("field1", Arrays.asList(1,2,3,4));
        });
        Condition condition = futureCondition.get();
        Condition conditionExpected = new ConditionIn("1", "field1", Arrays.asList(1,2,3,4), true);

        collector.checkThat(condition.getParameters(), is(equalTo(conditionExpected.getParameters())));
        collector.checkThat(condition.toSqlString(), is(equalTo(conditionExpected.toSqlString())));
    }

    @Test
    public void testIsNull() throws InterruptedException, ExecutionException {
        Future<Condition> futureCondition = threadpool.submit(() -> {
            return Restrictions.isNull("field1");
        });
        Condition condition = futureCondition.get();
        Condition conditionExpected = new ConditionNull("field1", false);

        collector.checkThat(condition.getParameters(), is(equalTo(conditionExpected.getParameters())));
        collector.checkThat(condition.toSqlString(), is(equalTo(conditionExpected.toSqlString())));
    }

    @Test
    public void testIsNotNull() throws InterruptedException, ExecutionException {
        Future<Condition> futureCondition = threadpool.submit(() -> {
            return Restrictions.isNotNull("field1");
        });
        Condition condition = futureCondition.get();
        Condition conditionExpected = new ConditionNull("field1", true);

        collector.checkThat(condition.getParameters(), is(equalTo(conditionExpected.getParameters())));
        collector.checkThat(condition.toSqlString(), is(equalTo(conditionExpected.toSqlString())));
    }

    @Test
    public void testIsEmpty() throws InterruptedException, ExecutionException {
        Future<Condition> futureCondition = threadpool.submit(() -> {
            return Restrictions.isEmpty("field1");
        });
        Condition condition = futureCondition.get();
        Condition conditionExpected = new ConditionEmpty("field1", false);

        collector.checkThat(condition.getParameters(), is(equalTo(conditionExpected.getParameters())));
        collector.checkThat(condition.toSqlString(), is(equalTo(conditionExpected.toSqlString())));
    }

    @Test
    public void testIsNotEmpty() throws InterruptedException, ExecutionException {
        Future<Condition> futureCondition = threadpool.submit(() -> {
            return Restrictions.isNotEmpty("field1");
        });
        Condition condition = futureCondition.get();
        Condition conditionExpected = new ConditionEmpty("field1", true);

        collector.checkThat(condition.getParameters(), is(equalTo(conditionExpected.getParameters())));
        collector.checkThat(condition.toSqlString(), is(equalTo(conditionExpected.toSqlString())));
    }
    
}
