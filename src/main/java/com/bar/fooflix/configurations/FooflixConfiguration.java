package com.bar.fooflix.configurations;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.io.fs.FileUtils;
import org.neo4j.kernel.GraphDatabaseAPI;
import org.neo4j.server.WrappingNeoServerBootstrapper;
import org.neo4j.server.configuration.Configurator;
import org.neo4j.server.configuration.ServerConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.rest.RepositoryRestMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.neo4j.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.PreDestroy;
import java.io.File;

@Configuration

@EnableNeo4jRepositories(basePackages = "com.bar.fooflix")
@Import(RepositoryRestMvcAutoConfiguration.class)
@EnableTransactionManagement
@EnableAutoConfiguration
public class FooflixConfiguration extends Neo4jConfiguration implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger(FooflixConfiguration.class);

    public FooflixConfiguration() {
            setBasePackage("com.bar.fooflix");
    }

    @Autowired
    GraphDatabaseService db;

    @Bean
    GraphDatabaseService graphDatabaseService() {
        return new GraphDatabaseFactory().newEmbeddedDatabase("accessingdataneo4j.db");
    }

    @Override
    public void run(String... strings) throws Exception {

        // used for Neo4j browser
        try {
            WrappingNeoServerBootstrapper neoServerBootstrapper;
            GraphDatabaseAPI api = (GraphDatabaseAPI) db;

            ServerConfigurator config = new ServerConfigurator(api);
            config.configuration()
                    .addProperty(Configurator.WEBSERVER_ADDRESS_PROPERTY_KEY, "127.0.0.1");
            config.configuration()
                    .addProperty(Configurator.WEBSERVER_PORT_PROPERTY_KEY, "8686");

            neoServerBootstrapper = new WrappingNeoServerBootstrapper(api, config);
            neoServerBootstrapper.start();
        } catch(Exception e) {
            //handle appropriately
        }
        // end of Neo4j browser config
    }

    @PreDestroy
    public void cleanUp() throws Exception {
        LOG.info("Spring Container is destroyed! clean me up ?");
        FileUtils.deleteRecursively(new File("accessingdataneo4j.db"));
    }
}
