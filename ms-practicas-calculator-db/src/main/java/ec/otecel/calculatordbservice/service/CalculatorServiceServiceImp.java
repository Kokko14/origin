package ec.otecel.calculatordbservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ec.otecel.common.model.globalintegration.header.HeaderInType;
import ec.otecel.component.error.exception.ComponentException;
import ec.otecel.component.logs.config.LoggerService;
import ec.otecel.calculatordbservice.adapter.PostgressTuentiDBAdapter;
import ec.otecel.calculatordbservice.constants.MsConstants;
import ec.otecel.calculatordbservice.util.BasicOperationService;

import ec.otecel.calculatordbservice.mci.CalculatorpracticaRequestDTO;
import ec.otecel.calculatordbservice.mci.CalculatorpracticaResponseDTO;



/**
 * xxxxxxx
 *
 * @author Indra Colombia<br>
 *         xxxxx<br>
 *         Email: xxxxxxxxxx@indracompany.com<br>
 *
 * @version 1.0, 09/11/2021
 *
 */
@Service
public class CalculatorServiceServiceImp implements IcalculatordbserviceService {

    PostgressTuentiDBAdapter postgressTuentiDBAdapter;

    private LoggerService loggerService;

    @Autowired
    public CalculatorServiceServiceImp(LoggerService loggerService, PostgressTuentiDBAdapter postgressTuentiDBAdapter) {
        this.loggerService = loggerService;
        this.postgressTuentiDBAdapter = postgressTuentiDBAdapter;
    }


    //TODO describir metodo

    @Override
    public CalculatorpracticaResponseDTO Calculatorpractica(HeaderInType headers, CalculatorpracticaRequestDTO request) throws ComponentException {
        return null;
    }

    /**
     * xxxxxxx
     *
     * @author Indra Colombia<br>
     *         xxxxx<br>
     *         Email: xxxxxxxxxx@indracompany.com<br>
     *
     * @version 1.0, 09/11/2021
     *
     */
    @Override
    public CalculatorpracticaResponseDTO calculator(HeaderInType headerReq, CalculatorpracticaRequestDTO request) throws ComponentException
    {


        return new BasicOperationService<CalculatorpracticaResponseDTO, CalculatorpracticaRequestDTO>(headerReq,
                new CalculatorpracticaResponseDTO(), request) {

            @Override
            public CalculatorpracticaResponseDTO process(HeaderInType headerIn, CalculatorpracticaRequestDTO request)
                    throws ComponentException {

                switch (request.getOperation()) {
                    case "+":
                        return sum(request);
                    case "-":
                        return diff(request);
                    case "/":
                        return div(request);
                    case "*":
                        return mult(request);
                    default:
                        return new CalculatorpracticaResponseDTO();

                }
            }

        }.initializer(loggerService).setCommonInfo(MsConstants.SERVICE, MsConstants.METHOD1,
                this.getClass().getSimpleName()).run();

    }

    private CalculatorpracticaResponseDTO sum(CalculatorpracticaRequestDTO request){
        CalculatorpracticaResponseDTO response = new CalculatorpracticaResponseDTO();
        response.setResult(request.getNum1() + request.getNum2());
        response.setCode("0000");
        response.setMessage("Proceso exitoso");
        return response;
    }

    private CalculatorpracticaResponseDTO diff(CalculatorpracticaRequestDTO request){
        CalculatorpracticaResponseDTO response = new CalculatorpracticaResponseDTO();
        response.setResult(request.getNum1() - request.getNum2());
        response.setCode("0000");
        response.setMessage("Proceso exitoso");
        return response;
    }

    private CalculatorpracticaResponseDTO div(CalculatorpracticaRequestDTO request){
        CalculatorpracticaResponseDTO response = new CalculatorpracticaResponseDTO();
        response.setResult(request.getNum1() / request.getNum2());
        response.setCode("0000");
        response.setMessage("Proceso exitoso");
        return response;
    }

    private CalculatorpracticaResponseDTO mult(CalculatorpracticaRequestDTO request){
        CalculatorpracticaResponseDTO response = new CalculatorpracticaResponseDTO();
        response.setResult(request.getNum1() * request.getNum2());
        response.setCode("0000");
        response.setMessage("Proceso exitoso");
        return response;
    }
}
