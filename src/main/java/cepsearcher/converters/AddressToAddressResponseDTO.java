package cepsearcher.converters;

import cepsearcher.dtos.AddressResponseDTO;
import cepsearcher.domains.Address;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class AddressToAddressResponseDTO implements Converter<Address, AddressResponseDTO> {

    @Synchronized
    @Nullable
    @Override
    public AddressResponseDTO convert(Address source) {
        if (source == null) {
            return null;
        }

        final AddressResponseDTO addressResponseDTO = new AddressResponseDTO();
        return addressResponseDTO;
    }
}
