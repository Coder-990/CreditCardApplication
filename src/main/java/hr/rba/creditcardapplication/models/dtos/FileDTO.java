package hr.rba.creditcardapplication.models.dtos;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
public class FileDTO {

    Long id;
    String status;
}
