package com.michael_delivery.backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;
import java.sql.Connection;

@Configuration
public class DataInitializerConfig {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Bean
    public CommandLineRunner initializeData() {
        return args -> {
            try {
                String tableName = "Sso_Providers";
                Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM " + tableName, Integer.class);

                if (count == null || count == 0) {
                    ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
                    populator.addScript(new ClassPathResource("procedure.sql"));
                    populator.setContinueOnError(false);

                    try (Connection connection = dataSource.getConnection()) {
                        System.out.println("Table " + tableName + " is empty. Executing initialization script...");
                        populator.populate(connection);
                        System.out.println("Data initialization completed successfully");
                    }
                } else {
                    System.out.println("Table already has data. Skipping initialization.");
                }
            } catch (Exception e) {
                System.err.println("Error checking table or initializing data: " + e.getMessage());
                e.printStackTrace();
            }
        };
    }
}
