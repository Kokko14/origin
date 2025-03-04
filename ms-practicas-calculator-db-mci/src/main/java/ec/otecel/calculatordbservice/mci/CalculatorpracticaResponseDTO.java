package ec.otecel.calculatordbservice.mci;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CalculatorpracticaResponseDTO {

	private Float result;
	private String message;
	private String code;

}
