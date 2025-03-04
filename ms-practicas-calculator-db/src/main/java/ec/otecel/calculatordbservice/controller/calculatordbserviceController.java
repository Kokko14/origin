
package ec.otecel.calculatordbservice.controller;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ec.otecel.component.error.adapter.ServiceErrorAdapter;
import ec.otecel.component.error.exception.ComponentException;
import ec.otecel.component.logs.config.LoggerService;
import ec.otecel.common.model.globalintegration.header.HeaderInType;
import ec.otecel.calculatordbservice.constants.MsConstants;
import ec.otecel.calculatordbservice.util.BasicOperationController;
import ec.otecel.calculatordbservice.util.ErrorMappingProperties;
import ec.otecel.calculatordbservice.util.ErrorTOpenApiProperties;
import ec.otecel.calculatordbservice.service.IcalculatordbserviceService;
import ec.otecel.calculatordbservice.mci.CalculatorpracticaRequestDTO;
import ec.otecel.calculatordbservice.mci.CalculatorpracticaResponseDTO;



/**
 * Class controladora que expone los metodos correspondientes al servicio xxxx
 *
 * @author nombre apellido correo@indracompany.com
 */
@RestController
@RequestMapping(value = "/calculatordbservice")
public class calculatordbserviceController{


	/**
	 * Componente para realizar la redireccion hacia las operaciones del back.
	 */
	private IcalculatordbserviceService service;

	private LoggerService loggerService;

	private SmartValidator validator;

	private HttpServletRequest httpServletRequest;

	private ServiceErrorAdapter adapterError;

	/**
	 * Errores fmw
	 */

	private ErrorTOpenApiProperties fmwErrors;

	/**
	 * Mapeo de errores legado contra fmw
	 */

	private ErrorMappingProperties mappingErrors;

	/**
	 * Constructor controlador inyector de dependecias
	 *
	 * @param service
	 */
	@Autowired
	public calculatordbserviceController(IcalculatordbserviceService service, LoggerService loggerService,
	SmartValidator validator, HttpServletRequest httpServletRequest,
	ServiceErrorAdapter adapterError, ErrorTOpenApiProperties fmwErrors,
	ErrorMappingProperties mappingErrors)
	{
		this.service = service;
		this.loggerService = loggerService;
		this.validator = validator;
		this.httpServletRequest = httpServletRequest;
		this.adapterError = adapterError;
		this.fmwErrors = fmwErrors;
		this.mappingErrors = mappingErrors;

	}

	// TODO: AGREGAR DESCRIPCION DEL METODO
	/**@Metodo que permite XXXXX
	 * @param headerReq
	 * @param calculatordbserviceAddRequestDTO request
	 * @return calculatordbserviceAddResponseDTO
	 * @throws ComponentException
	 * @author "cambiar autor"
	 */
	@SuppressWarnings("rawtypes")
	@PostMapping(path = "Calculatorpractica", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity Calculatorpractica(@RequestHeader Map<String, String> headerReq,
			@RequestBody CalculatorpracticaRequestDTO request)throws ComponentException
	{

			return new BasicOperationController<CalculatorpracticaResponseDTO, CalculatorpracticaRequestDTO>(headerReq,
					new CalculatorpracticaResponseDTO(), request) {
			  @Override
		 	  public CalculatorpracticaResponseDTO process(HeaderInType headers, CalculatorpracticaRequestDTO request)throws ComponentException
		    	{
					return service.Calculatorpractica(headers, request);
			    }

			}.errors(adapterError,mappingErrors,fmwErrors).initializer(loggerService,validator,httpServletRequest).setCommonInfo(MsConstants.SERVICE,MsConstants.METHOD1,this.getClass().getSimpleName()).run();

	}

}
