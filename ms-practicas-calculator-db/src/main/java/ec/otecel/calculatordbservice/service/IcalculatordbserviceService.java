/**
 * 
 */
package ec.otecel.calculatordbservice.service;

import ec.otecel.component.error.exception.ComponentException;
import ec.otecel.common.model.globalintegration.header.HeaderInType;
import ec.otecel.calculatordbservice.mci.CalculatorpracticaRequestDTO;
import ec.otecel.calculatordbservice.mci.CalculatorpracticaResponseDTO;



/**
 * Clase encargada de xxxxxxxxxxxxxxxxxxx
 *
 * @author Indra Colombia<br>
 *         xxxxx<br>
 *         Email: xxxxxx@indracompany.com<br>
 * 
 * @version 1.0, 07/11/2021
 *
 */
public interface IcalculatordbserviceService {

	public CalculatorpracticaResponseDTO Calculatorpractica(HeaderInType headers, CalculatorpracticaRequestDTO request) throws ComponentException;


	CalculatorpracticaResponseDTO calculator(HeaderInType headerReq, CalculatorpracticaRequestDTO request) throws ComponentException;
}
