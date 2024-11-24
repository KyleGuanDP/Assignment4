package org.example;

import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class JooqConfig {

    @Bean
    public DataSource dataSource() {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setURL("jdbc:postgresql://aws-0-us-west-1.pooler.supabase.com:6543/postgres?prepareThreshold=0");
        dataSource.setUser("postgres.jjxrfxvoadgbjxhpxavd");
        dataSource.setPassword("kotzyc-4peBka-viqnuv");
        return dataSource;
    }

    @Bean
    public DSLContext dsl(DataSource dataSource) {
        return DSL.using(dataSource, org.jooq.SQLDialect.POSTGRES);
    }
}
