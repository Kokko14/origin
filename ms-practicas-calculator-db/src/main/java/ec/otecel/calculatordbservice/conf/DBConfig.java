package ec.otecel.calculatordbservice.conf;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.zaxxer.hikari.HikariDataSource;

/**
 * Clase encargada de configurar el datasource del componente
 * Configuraciones en el properties.
 * 
 * @author Diego Diaz
 * 		   dfdiaz@indracompany.com
 *
 */
@Configuration
public class DBConfig {

	
	@Bean
	@ConfigurationProperties("telefonica.datasource.paymentez")
	@Primary
	public DataSourceProperties paymentezDataSourceProperties() {
		return new DataSourceProperties();
	}

	@Bean
	@ConfigurationProperties("telefonica.datasource.paymentez.configuration")
	@Primary
	public HikariDataSource paymentezDataSource() {
		return paymentezDataSourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();
	}

	@Bean("jdbcTemplatePaymentez")
	@Primary
	public NamedParameterJdbcTemplate jdbcTemplate(@Qualifier("paymentezDataSource") DataSource ds) {
		return new NamedParameterJdbcTemplate(ds);
	}
}
