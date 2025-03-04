package ec.otecel.calculatordbservice.mci;

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
public class ParameterDTO {

	private String key;

	private String value;

	private String description;

	private String isPublic;

	private String group;


}
