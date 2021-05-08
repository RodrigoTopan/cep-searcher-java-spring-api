package cepsearcher.domains;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Address {
    @Id
    private String id;

    private String street;
    private String district;
    private String city;
    private String state;
}
