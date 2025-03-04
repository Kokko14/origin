
package ec.otecel.calculatordbservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Import;
import javax.annotation.PostConstruct;

import ec.otecel.component.error.ApplicationError;
import ec.otecel.component.logs.ApplicationLog;
import java.util.TimeZone;

/**
 *
 * Clase encargada de administrar el compoente
 *
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
@Import(value = { ApplicationLog.class, ApplicationError.class })
public class Application {

	/**
	 *
	 * Método que permite la ejecucion del compoente
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(Application.class);
	}

	/**
	 * Método que permite definir el timeZone por defecto en America/Guayaquil
	 *
	 * @author Indra Colombia<br>
	 *         Diego Diaz<br>
	 *         Email: dfdiaz@indracompany.com<br>
	 *
	 * @version 1.0, 09/11/2021
	 *
	 */
	@PostConstruct
	public void init() {
		TimeZone.setDefault(TimeZone.getTimeZone("America/Guayaquil"));
	}

}