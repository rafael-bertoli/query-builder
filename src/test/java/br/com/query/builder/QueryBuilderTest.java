package br.com.query.builder;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

import java.util.Arrays;
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


public class QueryBuilderTest {
    
    @Rule
    public ErrorCollector collector = new ErrorCollector();
    
    private final ExecutorService threadpool = Executors.newFixedThreadPool(1);

    @Test
    public void testQueryBuilder() throws InterruptedException, ExecutionException {
        Future<Query> futureQuery = threadpool.submit(() -> {
            return QueryBuilder
                    .select("so_client_id as cliente", "count(1) as total", "max(so_created)")
                    .from("shipment_order")
                    .join(Join.from(JoinType.JOIN, "shipment_order_volume", Restrictions.eqProperty("so_id", "sov_shipment_order_id")))
                    .where(
                            Where.from(Restrictions.eq("so_client_id", 1))
                                    .add(
                                        Restrictions.or(
                                            Restrictions.eq("so_order_number", "12345678"),
                                            Restrictions.eq("sov_number", 1578)
                                        )
                                    )
                                    .add(Restrictions.in("sov_state", Arrays.asList(7, 8, 9, 10, 12)))
                                    .add(Restrictions.between("so_delivery_method_id", 5, 190))
                                    .add(Restrictions.ge("so_delivery_method_id", 5))
                                    .add(Restrictions.le("so_delivery_method_id", 100))
                                    .add(Restrictions.isEmpty("so_external_delivery_method_id"))
                                    .add(Restrictions.isNotNull("so_id"))
                                    .add(Restrictions.like("so_order_number", "%08235%"))
                                    .add(Restrictions.exists(
                                            QueryBuilder
                                                    .select("1")
                                                    .from("shipment_order_volume_state_history")
                                                    .where(Where.from(Restrictions.eqProperty("sovsh_shipment_order_volume_id", "sov_id")))
                                                    .limit(1)
                                    ))
                    )
                    .group(
                            GroupBy.of("so_client_id")
                                    .having(
                                            Having.of(Restrictions.gt("count(1)", 5))
                                    )
                    )
                    .order("so_id DESC")
                    .limit(100)
                    .offset(10)
                    .result();
        });
        Query query = futureQuery.get();
        String expectedSql = "SELECT so_client_id as cliente, count(1) as total, max(so_created) FROM shipment_order JOIN shipment_order_volume ON so_id = sov_shipment_order_id WHERE so_client_id = :so_client_id1 AND (so_order_number = :so_order_number2 OR sov_number = :sov_number3) AND sov_state IN (:sov_state4) AND so_delivery_method_id BETWEEN :startso_delivery_method_id5 AND :stopso_delivery_method_id5 AND so_delivery_method_id >= :so_delivery_method_id6 AND so_delivery_method_id <= :so_delivery_method_id7 AND so_external_delivery_method_id = '' AND so_id IS NOT NULL AND so_order_number LIKE :so_order_number8 AND EXISTS (SELECT 1 FROM shipment_order_volume_state_history WHERE sovsh_shipment_order_volume_id = sov_id LIMIT 1 ) GROUP BY so_client_id HAVING count(1) > :count19 ORDER BY so_id DESC LIMIT 100 OFFSET 10 ";
        Map<String, Object> expectedParameters =  new HashMap<String, Object>() {
            {
                put("so_client_id1", 1);
                put("so_order_number2", "12345678");
                put("sov_number3", 1578);
                put("sov_state4", Arrays.asList(7, 8, 9, 10, 12));
                put("startso_delivery_method_id5", 5);
                put("stopso_delivery_method_id5", 190);
                put("so_delivery_method_id6", 5);
                put("so_delivery_method_id7", 100);
                put("so_order_number8", "%08235%");
                put("count19", 5);
            }
        };
        collector.checkThat(query.getParameters(), is(equalTo(expectedParameters)));
        collector.checkThat(query.getSqlQuery(), is(equalTo(expectedSql)));
    }
    
}
