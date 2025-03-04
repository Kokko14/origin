/**
 * 
 */
package ec.otecel.calculatordbservice.util;

import java.util.Date;

import ec.otecel.common.model.commontypes.ResponseServiceType;
import ec.otecel.common.model.constant.GeneralConstants;
import ec.otecel.common.model.globalintegration.header.HeaderInType;
import ec.otecel.common.model.log.ExecutionInfoRuntimeDto;
import ec.otecel.common.model.log.ExecutionInfoToLogging;
import ec.otecel.common.model.rest.fault.MessageFaultDTO;
import ec.otecel.component.error.exception.ComponentException;
import ec.otecel.component.logs.config.LoggerService;

/**
 * Clase encargada de ofrecer utilidades para el componente de logging.
 *
 * @author Arquitectura TME-INDRA<br>
 *         Arquitectura<br>
 *         Email: arquitectura@indracompany.com<br>
 * 
 * @version 1.0, 13/01/2021
 *
 */
public class LoggingUtilities {

	/**
	 * Metodo constructor de la clase en cuestion.
	 * 
	 * @author Indra Colombia<br>
	 *         Arquitectura<br>
	 *         Email: Arquitectura@indracompany.com<br>
	 *
	 * @version 1.0, 20/08/2020
	 *
	 */
	private LoggingUtilities() {
		// Se deja vacoo intencionalmente
	}

	/**
	 * Metodo que permite realizar el logging del log de ejecucion.
	 * 
	 * @author Indra Colombia<br>
	 *         Arquitectura<br>
	 *         Email: Arquitectura@indracompany.com<br>
	 * 
	 * @version 1.0, 20/08/2020
	 *
	 * @param request         Request de la peticion.
	 * @param headerIn        Header de la peticion.
	 * @param execRuntimeInfo Objeto que contiene las fechas de inicio y
	 *                        finalizacion para colocar en el log de ejecucion.
	 * @param error           Objeto que contiene el error que sucede.
	 * @param logExecInfo     Objeto que contiene la informacion a colocar en el log
	 *                        de ejecucion.
	 */
	public static void logExec(Object request, HeaderInType headerIn, ExecutionInfoRuntimeDto execRuntimeInfo,
			ComponentException error, ExecutionInfoToLogging logExecInfo, LoggerService loggerService) {
		String requestString = "request null";
		String errorCodeString = "error code null";
		if (request != null) {
			requestString = request.toString();
		}

		// Si viene error es un KO
		if (error != null) {
			if (error.getErrorCode() != null && error.getErrorCode().getCode() != null) {
				errorCodeString = error.getErrorCode().getCode();
			}
			logExecInfo.setResultCode(ResponseServiceType.KO.value());
			logExecInfo.setErrorReturnCode(errorCodeString);
			logExecInfo.setNormalizedErrorDescription(requestString + GeneralConstants.SPACE_DASH + error.toString());
		} else {
			logExecInfo.setResultCode(ResponseServiceType.OK.value());
			logExecInfo.setNormalizedErrorDescription(requestString);

		}

		// Se registra en el log de ejecucion la informacion.
		execRuntimeInfo.setServiceEndDate(new Date());
		loggerService.logExec(headerIn, logExecInfo, execRuntimeInfo);
	}

	/**
	 * Metodo que permite realizar el logging del log de ejecucion.
	 * 
	 * @author Indra Colombia<br>
	 *         Arquitectura<br>
	 *         Email: Arquitectura@indracompany.com<br>
	 * 
	 * @version 1.0, 27/08/2020
	 *
	 * @param request         Request de la peticion.
	 * @param headerIn        Header de la peticion.
	 * @param execRuntimeInfo Objeto que contiene las fechas de inicio y
	 *                        finalizacion para colocar en el log de ejecucion.
	 * @param error           Objeto que contiene el error que sucede.
	 * @param logExecInfo     Objeto que contiene la informacion a colocar en el log
	 *                        de ejecucion.
	 */
	public static void logExec(Object request, HeaderInType headerIn, ExecutionInfoRuntimeDto execRuntimeInfo,
			MessageFaultDTO error, ExecutionInfoToLogging logExecInfo, LoggerService loggerService) {
		String requestString = "request null";
		String errorCodeString = "error code null";
		if (request != null) {
			requestString = request.toString();
		}

		// Si viene error es un KO
		if (error != null) {
			if (error.getExceptionCode() != null) {
				errorCodeString = error.getExceptionCode();
			}
			logExecInfo.setResultCode(ResponseServiceType.KO.value());
			logExecInfo.setErrorReturnCode(errorCodeString);
			logExecInfo.setNormalizedErrorDescription(requestString + GeneralConstants.SPACE_DASH + error.toString());
		} else {
			logExecInfo.setResultCode(ResponseServiceType.OK.value());
			logExecInfo.setNormalizedErrorDescription(requestString);

		}

		// Se registra en el log de ejecucion la informacion.
		execRuntimeInfo.setServiceEndDate(new Date());
		loggerService.logExec(headerIn, logExecInfo, execRuntimeInfo);
	}

}