package com.fooflix.configurations;

import com.ryantenney.metrics.spring.config.annotation.EnableMetrics;
import com.ryantenney.metrics.spring.config.annotation.MetricsConfigurerAdapter;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.kernel.GraphDatabaseAPI;
import org.neo4j.server.WrappingNeoServerBootstrapper;
import org.neo4j.server.configuration.Configurator;
import org.neo4j.server.configuration.ServerConfigurator;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.config.Neo4jConfiguration;

@EnableMetrics
@Configuration
@EnableAutoConfiguration(exclude = {JacksonAutoConfiguration.class})
public class FooflixConfiguration extends MetricsConfigurerAdapter {

    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(FooflixConfiguration.class);

    @Configuration
    @EnableNeo4jRepositories(basePackages = "com.fooflix")
    static class ApplicationConfig extends Neo4jConfiguration {

        public ApplicationConfig() {
            setBasePackage("com.fooflix");
        }

        @Bean
        GraphDatabaseService graphDatabaseService() {
            GraphDatabaseService db = new GraphDatabaseFactory().newEmbeddedDatabase("accessingdataneo4j.db");

            GraphDatabaseAPI api = (GraphDatabaseAPI) db;

            ServerConfigurator config = new ServerConfigurator(api);
            config.configuration()
                    .addProperty(Configurator.WEBSERVER_ADDRESS_PROPERTY_KEY, "127.0.0.1");
            config.configuration()
                    .addProperty(Configurator.WEBSERVER_PORT_PROPERTY_KEY, "7575");

            WrappingNeoServerBootstrapper neoServerBootstrapper = new WrappingNeoServerBootstrapper(api, config);
            neoServerBootstrapper.start();

            return db;
        }
    }
}
