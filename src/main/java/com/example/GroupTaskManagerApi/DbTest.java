package com.example.GroupTaskManagerApi;

import jakarta.annotation.PostConstruct;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * FIXME このクラスは本番前に削除
 */
@Component
public class DbTest {

    private final JdbcTemplate jdbcTemplate;

    public DbTest (JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostConstruct
    public void testConnection () {
        try {
            Integer result = jdbcTemplate.queryForObject("SELECT 1", Integer.class);
            System.out.println("✅ DB connection test successful: " + result);
        } catch (Exception e) {
            System.err.println("❌ DB connection failed:");
            e.printStackTrace();
        }
    }
}
