package ec.otecel.calculatordbservice.util;

import java.io.StringWriter;
import java.text.MessageFormat;
import java.util.List;

import org.springframework.stereotype.Component;

import ec.otecel.common.model.constant.GeneralConstants;
import ec.otecel.common.model.fault.ErrorFmwDto;
import ec.otecel.common.model.fault.ErrorMappingDto;
import ec.otecel.common.model.rest.fault.AppDetailDTO;
import ec.otecel.common.model.rest.fault.ExceptionProtocolDTO;
import ec.otecel.common.model.rest.fault.MessageFaultDTO;
import ec.otecel.component.error.exception.ComponentException;
import ec.otecel.component.error.exception.ServiceFaultException;

/**
 * 
 * Clase encargada de exponer la funcionalidad asociada al mapeo de errores en
 * los diferentes micro servicios.
 *
 * @author Arquitectura TME-INDRA<br>
 *         Arquitectura<br>
 *         Email: arquitectura@indracompany.com<br>
 * 
 * @version 1.0, 13/01/2021
 *
 */
@Component
public class ServiceErrorMapping {

	/**
	 * 
	 * Metodo que permite realizar el mapeo de errores a errores definidos en el
	 * framework de global y TopenAPI.
	 * 
	 * @author Arquitectura TME-INDRA<br>
	 *         Arquitectura<br>
	 *         Email: arquitectura@indracompany.com<br>
	 * 
	 * @version 1.0, 4/03/2020
	 *
	 * @param exception,     excepcion generada en las capas inferiores, y en la
	 *                       cual se define el error a mapear.
	 * @param fmwErrors,     errores de fmk parametrizados en el servicio
	 * @param mappingErrors, lista de errores a mapear, en los cuales se determina
	 *                       que error de legado representa a un eror del fmw.
	 * @return {@link ServiceFaultException}, representa una excepcion que contiene
	 *         un messageFault con el error final a retornar por el servicio.
	 */
	public MessageFaultDTO errorHandlerRest(ComponentException exception, List<ErrorFmwDto> fmwErrors,
			List<ErrorMappingDto> mappingErrors) {
		// Se obtiene el error mapeado desde las capas de integracion.
		ErrorMappingDto errorMapping = mappingErrors.stream()
				.filter(e -> exception.getErrorCode().getCode().equals(e.getCode())).findAny().orElse(null);

		// Se obtiene el error del fmw asociado al error de negocio o tecnico mapeado.
		ErrorFmwDto errorFmwDto = fmwErrors.stream().filter(e -> errorMapping.getCodeFmk().equals(e.getCode()))
				.findAny().orElse(null);

		// Se crea el messageFault a retornar.
		MessageFaultDTO messageFault = new MessageFaultDTO();

		if (errorFmwDto != null) {
			messageFault.setExceptionCategory(errorFmwDto.getCategory());
			messageFault.setExceptionCode(errorFmwDto.getCode());

			// Se valida que se quiere mostrar el texto del mensaje del fmw.
			if (exception.isShowErrorText()) {
				MessageFormat mf = new MessageFormat(errorFmwDto.getText());
				messageFault.setExceptionDetail(mf.format(exception.getReplaceMesages()));
			}
			messageFault.setExceptionMessage(errorFmwDto.getException());

			ExceptionProtocolDTO protocolType = new ExceptionProtocolDTO();
			protocolType.setCode(errorFmwDto.getProtocolCode());
			protocolType.setDescription(errorFmwDto.getProtocolDescription());

			messageFault.setExceptionProtocol(protocolType);
		}

		messageFault.setExceptionSeverity(GeneralConstants.ERROR_SEVERITY);
		messageFault.setExceptionType(GeneralConstants.ERROR_TYPE);

		if (errorMapping != null) {
			AppDetailDTO detailType = new AppDetailDTO();
			detailType.setExceptionAppCode(errorMapping.getCode());

			// Se valida que no sea un mensaje generado por una exception runtime
			if (exception.getMessage() == null) {
				// Se valida que exista un mensaje personalizado.
				if (exception.getCauseMsg() != null && !exception.getCauseMsg().isEmpty()) {
					detailType.setExceptionAppMessage(errorMapping.getCause());
					detailType.setExceptionAppCause(exception.getCauseMsg());

					// Se toma el mensaje generico asociado en el mapeo de error
				} else {
					detailType.setExceptionAppMessage(errorMapping.getCause());
					detailType.setExceptionAppCause(errorMapping.getCause());
				}

				// Se toma el mensaje de la excepcion.
			} else {
				detailType.setExceptionAppMessage(exception.getMessage());

				StringWriter sw = new StringWriter();
				detailType.setExceptionAppCause(sw.toString());
			}
			detailType.setExceptionAppLocation(exception.getLocation());
			messageFault.setAppDetail(detailType);

		}

		return messageFault;
	}
}
