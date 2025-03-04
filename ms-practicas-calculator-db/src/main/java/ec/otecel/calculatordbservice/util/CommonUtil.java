package ec.otecel.calculatordbservice.util;

import java.util.Map;

import org.springframework.stereotype.Component;

import ec.otecel.common.model.commontypes.ErrorCodeType;
import ec.otecel.common.model.commontypes.LayerType;
import ec.otecel.common.model.globalintegration.header.HeaderInType;
import ec.otecel.component.error.exception.ComponentExceptionFS;
import ec.otecel.component.requester.util.RestUtil;

/**
 * 
 * Clase encargada de mantener utilidades comunes al gateway.
 *
 * @author Arquitectura TME-INDRA<br>
 *         Arquitectura<br>
 *         Email: arquitectura@indracompany.com<br>
 * 
 * @version 1.0, 13/01/2021
 *
 */
@Component
public class CommonUtil {

	private CommonUtil() {
	}

	/**
	 * 
	 * 
	 * @author Indra Colombia<br>
	 *         Arquitectura<br>
	 *         Email: Arquitectura@indracompany.com<br>
	 * 
	 * @version 1.0, 27/03/2020
	 *
	 * @param headers   Mapa que contiene los transport header enviados al servicio
	 *                  rest.
	 * @param service   Servicio donde se invoca la utilidad.
	 * @param operation Operacion donde se invoca la utilidad.
	 * @throws ComponentExceptionFS Excepcion que indica el resultado de la
	 *                              validacion
	 */
	public static HeaderInType getHeaderIn(Map<String, String> headers, String service, String operation)
			throws ComponentExceptionFS {
		try {
			HeaderInType headerIn = RestUtil.getHeaderIn(headers, service, operation);
			headerIn.setOperation(operation);
			return headerIn;
		} catch (Exception ex) {
			throw new ComponentExceptionFS(ex.getMessage(), ex, ErrorCodeType.ERROR_PARAMETROS_HI, LayerType.EXPOSITION,
					service, operation);
		}
	}

}
