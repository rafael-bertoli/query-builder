# Query-builder
Library to help create jdbc sql query

> Usage:
  ```java
Query query = QueryBuilder
        .select("p_product_name", "count(1) as total", "max(p_created)")
        .from("product")
        .join(Join.from(JoinType.JOIN, "product_item", Restrictions.eqProperty("p_id", "pi_product_id")))
        .where(
                Where.from(Restrictions.eq("p_type_id", 1))
                        .add(
                            Restrictions.or(
                                Restrictions.eq("p_reference", "12345678"),
                                Restrictions.eq("pi_code", 1578)
                            )
                        )
                        .add(Restrictions.in("p_state", Arrays.asList(7, 8, 9, 10, 12)))
                        .add(Restrictions.between("p_id", 5, 190))
                        .add(Restrictions.ge("p_id", 5))
                        .add(Restrictions.le("p_id", 100))
                        .add(Restrictions.isEmpty("p_reference"))
                        .add(Restrictions.isNotNull("p_id"))
                        .add(Restrictions.like("p_referente", "%08235%"))
                        .add(Restrictions.exists(
                                QueryBuilder
                                        .select("1")
                                        .from("product_history")
                                        .where(Where.from(Restrictions.eqProperty("ph_product_id", "p_id")))
                                        .limit(1)
                        ))
        )
        .group(
                GroupBy.of("p_type_id")
                        .having(
                                Having.of(Restrictions.gt("count(1)", 5))
                        )
        )
        .order("p_id DESC")
        .limit(100)
        .offset(10)
        .result();

query.getSqlQuery();
query.getParameters();
  ```
