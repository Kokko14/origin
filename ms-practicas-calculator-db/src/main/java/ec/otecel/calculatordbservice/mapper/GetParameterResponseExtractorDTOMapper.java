package ec.otecel.calculatordbservice.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import ec.otecel.calculatordbservice.dto.GetParameterDBResponseDTO;
import ec.otecel.calculatorservice.dto.ParameterDBDTO;

/**
 * Clase adaptadora que permite crear un objeto de tipo GetParameterResponseExtractorDTO
 *
 * @author xxxxx@xxxxxx.com
 *
 */
public class GetParameterResponseExtractorDTOMapper implements ResultSetExtractor<GetParameterDBResponseDTO> {

    @Override
    public GetParameterDBResponseDTO extractData(ResultSet rs) throws SQLException, DataAccessException {

        GetParameterDBResponseDTO response = new GetParameterDBResponseDTO();

        boolean firstRow = true;
        ParameterDBDTO row = null;
        List<ParameterDBDTO> results = new ArrayList<>();

        while (rs.next()) {

            if (rs.getString("p_key") != null) {
                row = new ParameterDBDTO();
                row.setKey(rs.getString("p_key"));
                row.setValue(rs.getString("p_value"));
                row.setDescription(rs.getString("p_description"));
                row.setIsPublic(rs.getString("p_is_public"));
                row.setGroup(rs.getString("p_group"));
                results.add(row);
                if (firstRow) {
                }
                firstRow = false;

            }
        }

        response.setParameters(results);

        return response;
    }

}

