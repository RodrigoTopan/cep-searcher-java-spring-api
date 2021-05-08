package cepsearcher.converters;

import cepsearcher.dtos.AddressDTO;
import cepsearcher.domains.Address;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class AddressToAddressDTO implements Converter<Address, AddressDTO> {

    @Synchronized
    @Nullable
    @Override
    public AddressDTO convert(Address source) {
        if (source == null) {
            return null;
        }

        final AddressDTO addressDTO = new AddressDTO();

        addressDTO.setCep(source.getCep());
        addressDTO.setCity(source.getCity());
        addressDTO.setStreet(source.getStreet());
        addressDTO.setState(source.getState());
        addressDTO.setDistrict(source.getDistrict());
        return addressDTO;
    }
}
