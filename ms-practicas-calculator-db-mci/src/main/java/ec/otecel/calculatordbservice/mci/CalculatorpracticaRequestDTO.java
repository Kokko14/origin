package ec.otecel.calculatordbservice.mci;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CalculatorpracticaRequestDTO {
	@NotNull
	private Float num1;
	@NotNull
	private Float num2;
	@NotNull
	@Pattern(regexp = "^[\\+\\-\\*/]$", message = "Operación inválida. Debe ser uno de los siguientes caracteres: +, -, /, *")
	private String operation;
}
