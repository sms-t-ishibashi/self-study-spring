package io.github.sms.t.ishibashi.sss;

import com.ninja_squad.dbsetup.destination.DataSourceDestination;
import com.ninja_squad.dbsetup.destination.Destination;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author yo1000
 */
@Configuration
public class TestConfiguration {
    @Bean
    public Destination destination(DataSource dataSource) {
        return new DataSourceDestination(dataSource);
    }
}
