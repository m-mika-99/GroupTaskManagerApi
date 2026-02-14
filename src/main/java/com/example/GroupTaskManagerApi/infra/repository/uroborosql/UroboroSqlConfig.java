package com.example.GroupTaskManagerApi.infra.repository.uroborosql;

import jp.co.future.uroborosql.SqlAgent;
import jp.co.future.uroborosql.UroboroSQL;
import jp.co.future.uroborosql.config.SqlConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class UroboroSqlConfig {

    @Bean
    public SqlAgent sqlAgent (DataSource dataSource) {
        SqlConfig conf = UroboroSQL.builder(dataSource).build();
        return conf.agent();
    }
}
