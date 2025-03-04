

package ec.otecel.calculatordbservice.dto;

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
public class GetParameterDBResponseDTO {


    private List<ec.otecel.calculatorservice.dto.ParameterDBDTO> parameters;



}
