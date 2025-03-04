package ec.otecel.calculatorservice.dto;

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
public class ParameterDBDTO {

    private String key;

    private String value;

    private String description;

    private String isPublic;

    private String group;


}