
package ec.otecel.calculatordbservice.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import ec.otecel.common.model.fault.ErrorMappingDto;

/**
 * 
 * Clase encargada de cargar los errores de mapeo de un archivo de propiedades y
 * transformarlos a una lista de objetos
 *
 * @author Arquitectura TME-INDRA<br>
 *         Arquitectura<br>
 *         Email: arquitectura@indracompany.com<br>
 * @version 1.0, 13/01/2021
 *
 */
@Component
@PropertySource("classpath:ErrorMapping.properties")
@ConfigurationProperties("map")
public class ErrorMappingProperties {

	/**
	 * Lista de errores transformados
	 */
	private List<ErrorMappingDto> errors = new ArrayList<>();

	/**
	 * Metodo que retorna el valor del atributo errors
	 * 
	 * @author Indra Colombia<br>
	 *         Arquitectura<br>
	 *         Email: Arquitectura@indracompany.com<br>
	 *
	 * @version 1.0, 4/03/2020
	 * 
	 * @return errors
	 */
	public List<ErrorMappingDto> getErrors() {
		return errors;
	}

	/**
	 * Metodo que permite almacenar el valor errors en el atributo errors
	 * 
	 * @author Indra Colombia<br>
	 *         Arquitectura<br>
	 *         Email: Arquitectura@indracompany.com<br>
	 *
	 * @version 1.0, 4/03/2020
	 * 
	 * @param errors el valor a asignar al atributo errors
	 */
	public void setErrors(List<ErrorMappingDto> errors) {
		this.errors = errors;
	}

}
