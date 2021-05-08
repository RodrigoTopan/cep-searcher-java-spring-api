package cepsearcher.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class AddressCommand {
    private String id;
    private String street;
    private String district;
    private String city;
    private String state;
}
