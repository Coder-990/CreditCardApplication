package hr.rba.creditcardapplication.models.dtos;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
public class PersonDTO {


    Long id;
    String oib;
    String name;
    String lastName;
    String creditCardStatus;
}
