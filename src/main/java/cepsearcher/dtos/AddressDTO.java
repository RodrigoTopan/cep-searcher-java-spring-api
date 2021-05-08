package cepsearcher.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class AddressDTO {
    private String cep;
    private String street;
    private String district;
    private String city;
    private String state;
}
