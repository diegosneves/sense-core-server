package br.com.diegosneves.util;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import org.testcontainers.containers.MySQLContainer;

import java.util.HashMap;
import java.util.Map;

public class MySQLTestResource implements QuarkusTestResourceLifecycleManager {

	private static final MySQLContainer<?> MYSQL_CONTAINER = new MySQLContainer<>("mysql:8.2.0")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test")
            .withReuse(true);  // Reutiliza o container entre execuções de teste

    @Override
    public Map<String, String> start() {
        MYSQL_CONTAINER.start();

        Map<String, String> config = new HashMap<>();
        config.put("quarkus.datasource.jdbc.url", MYSQL_CONTAINER.getJdbcUrl());
        config.put("quarkus.datasource.username", MYSQL_CONTAINER.getUsername());
        config.put("quarkus.datasource.password", MYSQL_CONTAINER.getPassword());

        return config;
    }

    @Override
    public void stop() {
        // O container será parado automaticamente pelo Testcontainers
        // devido ao reuse=true, ele pode ser mantido entre execuções
    }

}
