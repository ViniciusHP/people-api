package one.digitalinnovation.peopleapi.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MessageResponseDTO {

    @JsonIgnore
    private Long id;
    private String message;
}
