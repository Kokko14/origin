package ec.otecel.calculatordbservice.util;


import java.net.MalformedURLException;
import java.util.Date;
import java.util.UUID;
import org.apache.logging.log4j.util.Strings;
import ec.otecel.common.fs.service.serviceordermanagement.serviceorderorchestration.notifications.v1.ErrorTrace;
import ec.otecel.common.model.commontypes.ErrorCodeType;
import ec.otecel.common.model.commontypes.LayerType;
import ec.otecel.common.model.commontypes.LoggerAppType;
import ec.otecel.common.model.globalintegration.header.HeaderInType;
import ec.otecel.common.model.log.ExecutionInfoRuntimeDto;
import ec.otecel.common.model.log.ExecutionInfoToLogging;
import ec.otecel.component.error.exception.ComponentException;
import ec.otecel.component.error.util.ErrorUtil;
import ec.otecel.component.logs.config.LoggerService;
import ec.otecel.component.logs.util.LoggerUtil;

public abstract class BasicOperationAdapter<R, P> {

	/**
	 * Componente para manejo de log
	 */
	private LoggerService loggerService;

	private String serviceName;
	private String operationName;

	private HeaderInType headerIn;

	private R response;
	private P request;

	protected String uUidTransaction = null;

	private String className;

	protected BasicOperationAdapter(HeaderInType headerIn, R response, P request) {
		this.headerIn = headerIn;
		this.request = request;
		this.response = response;
	}

	public abstract R process(HeaderInType headerIn, P request)
			throws ComponentException, ErrorTrace, MalformedURLException;

	public R preRun() throws ComponentException {
		return run();
	}

	public R run() throws ComponentException {

		// DTO de respuesta homologada
		ComponentException error = null;

		processHeader();

		// Se almacena la informacion de ejecucion del servicio.
		ExecutionInfoRuntimeDto execRuntimeInfo = new ExecutionInfoRuntimeDto(new Date());

		try {

			// Registro Peticion EXP_HEADER_AUDIT_REQUEST
			loggerService.logApp(uUidTransaction, headerIn.getExecId(), uUidTransaction,
					LoggerAppType.DSI_MCI_HEADERS_AUDIT_REQUEST.toString(), headerIn.toString(), className);

			// Registro Peticion MCI_BODY_AUDIT_REQUEST
			loggerService.logApp(uUidTransaction, headerIn.getExecId(), uUidTransaction,
					LoggerAppType.DSI_MCI_BODY_AUDIT_REQUEST.toString(), request.toString(), className);

			// Se registra la fecha de inicio de invocacion al legado.
			execRuntimeInfo.setLegacyInitialDate(new Date());

			response = process(headerIn, request);

			// Se registra la fecha de fin de invocacion al legado
			execRuntimeInfo.setLegacyEndDate(new Date());

			// Se registra la respuesta a retornar.
			loggerService.logApp(uUidTransaction, headerIn.getExecId(), uUidTransaction,
					LoggerAppType.DSI_MCI_BODY_AUDIT_RESPONSE.toString(), response.toString(), className);

			// Se codifica y asigna la respuesta del servicio.
		} catch (ErrorTrace e) {
			// Se captura y encasula el error
			error = new ComponentException(ErrorUtil.getExceptionMessage(e), e,
					ErrorCodeType.getEnumByCode(e.getFaultInfo().getCode().getError()), false, null,
					ErrorUtil.createErrorLocation(this.getClass().getSimpleName(), LayerType.INTEGRATION));

			// Se registra en log de aplicacion error
			loggerService.logApp(uUidTransaction, headerIn != null ? headerIn.getExecId() : Strings.EMPTY,
					uUidTransaction, LoggerAppType.DSI_AUDIT_BODY_ERROR_HANDLER.toString(), error.toString(),
					BasicOperationAdapter.class.getSimpleName());

			throw error;

		} catch (ComponentException e) {
			error = e;
			// Se registra en log de aplicacion error
			loggerService.logApp(uUidTransaction, Strings.EMPTY, uUidTransaction,
					LoggerAppType.DSI_AUDIT_BODY_ERROR_HANDLER.toString(), error.toString(), className);
			throw error;
		} catch (Exception e) {

			// Se captura y encasula el error
			error = new ComponentException(e.getMessage(), e, ErrorCodeType.ERROR_INESPERADO, true,
					new String[] { new StringBuilder(request.toString()).toString() },
					ErrorUtil.createErrorLocation(className, LayerType.INTEGRATION));

			// Se registra en log de aplicacion error
			loggerService.logApp(uUidTransaction, Strings.EMPTY, uUidTransaction,
					LoggerAppType.DSI_AUDIT_BODY_ERROR_HANDLER.toString(), error.toString(), className);
			throw error;
		} finally {


			LoggerUtil.createExecutionLog(request.toString(), execRuntimeInfo,
					headerIn != null ? headerIn : new HeaderInType(),
					error != null ? error.getErrorCode().getCode() : null, error != null ? error.toString() : null,
					new ExecutionInfoToLogging(serviceName, operationName, LayerType.INTEGRATION.value(),
							uUidTransaction),
					loggerService);
		}
		return response;

	}

	private void processHeader() {
		// Se define el id de transaccion unico.
		uUidTransaction = UUID.randomUUID().toString();
		// header.execId=550e8400-e29b-41d4-a716-446655440001
	}

	public BasicOperationAdapter<R, P> setCommonInfo(String serviceName, String operationName, String className) {
		this.serviceName = serviceName;
		this.operationName = operationName;
		this.className = className;
		return this;
	}

	public BasicOperationAdapter<R, P> initializer(LoggerService loggerService) {
		this.loggerService = loggerService;
		return this;
	}

}
