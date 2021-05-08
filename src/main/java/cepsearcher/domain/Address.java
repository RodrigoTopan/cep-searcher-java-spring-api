package cepsearcher.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

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
