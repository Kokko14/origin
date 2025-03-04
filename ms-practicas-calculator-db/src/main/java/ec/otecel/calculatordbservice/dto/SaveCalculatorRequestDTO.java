package ec.otecel.calculatordbservice.dto;

import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class SaveCalculatorRequestDTO {
    private Float num1;
    private Float num2;
    private String operation;
    private String result;
}
