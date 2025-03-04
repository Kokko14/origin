package ec.otecel.calculatordbservice.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import ec.otecel.common.model.globalintegration.header.HeaderInType;
import ec.otecel.component.error.exception.ComponentException;
import ec.otecel.component.logs.config.LoggerService;
import ec.otecel.calculatordbservice.constants.MsConstants;
import ec.otecel.calculatordbservice.mapper.GetParameterResponseExtractorDTOMapper;
import ec.otecel.calculatordbservice.util.BasicOperationAdapter;
import ec.otecel.calculatordbservice.dto.GetParameterDBRequestDTO;
import ec.otecel.calculatordbservice.dto.GetParameterDBResponseDTO;

@Repository
public class PostgressTuentiDBAdapter {

	@Autowired
	private LoggerService loggerService;

	private JdbcTemplate jdbcTemplate;

	@Value("${telefonica.db.getParameter:null}")
	private String getParameterQuery;

	@Autowired
	public PostgressTuentiDBAdapter(LoggerService loggerService, @Qualifier("jdbcTemplate") JdbcTemplate jdbcTemplate) {

		this.loggerService = loggerService;
		this.jdbcTemplate = jdbcTemplate;
	}

	public GetParameterDBResponseDTO getParameters(HeaderInType headerIn, @RequestBody GetParameterDBRequestDTO request)
			throws ComponentException {


		return new BasicOperationAdapter<GetParameterDBResponseDTO, GetParameterDBRequestDTO>(headerIn,
				new GetParameterDBResponseDTO(), request) {

			public GetParameterDBResponseDTO process(HeaderInType headerIn, GetParameterDBRequestDTO request)
					throws ComponentException {
				Object[] obj = { request.getKey(), request.getGroup() };
				return jdbcTemplate.query(getParameterQuery, new GetParameterResponseExtractorDTOMapper(), obj);

			}

		}.initializer(loggerService).setCommonInfo(MsConstants.SERVICE_POSTGRESS_REPOSITORY,
				MsConstants.OPERATION_NAME_GET_PARAMETERS, PostgressTuentiDBAdapter.class.getSimpleName()).run();

	}

}
