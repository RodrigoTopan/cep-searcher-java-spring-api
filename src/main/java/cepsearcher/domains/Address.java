package cepsearcher.domains;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Address {
    @Id
    private String id;

    //@Indexed(unique = true)
    private String cep;

    @NotBlank(message = "Nome da rua é obrigatório")
    private String street;

    @NotBlank(message = "Nome do bairro é obrigatório")
    private String district;

    @NotBlank(message = "Nome da cidade é obrigatório")
    private String city;

    @NotBlank(message = "Nome do Estado é obrigatório")
    private String state;
}
