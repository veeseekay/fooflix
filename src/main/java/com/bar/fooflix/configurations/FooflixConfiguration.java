package com.bar.fooflix.configurations;

import com.ryantenney.metrics.spring.config.annotation.EnableMetrics;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.io.fs.FileUtils;
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
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.PreDestroy;
import java.io.File;

@EnableMetrics
@Configuration
@EnableAutoConfiguration(exclude = {JacksonAutoConfiguration.class})
public class FooflixConfiguration {

    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(FooflixConfiguration.class);

    @Configuration
    @EnableTransactionManagement
    @EnableNeo4jRepositories(basePackages = "com.bar.fooflix")
    /*@ComponentScan({
            "com.bar.fooflix.entities",
            "com.bar.fooflix.services",
    })*/
    static class ApplicationConfig extends Neo4jConfiguration {

        public ApplicationConfig() {
            setBasePackage("com.bar.fooflix");
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

    @PreDestroy
    public void cleanUp() throws Exception {
        LOG.info("Spring Container is destroyed! clean me up ?");
        FileUtils.deleteRecursively(new File("accessingdataneo4j.db"));
    }
}
