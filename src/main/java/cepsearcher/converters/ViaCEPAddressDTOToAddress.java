package cepsearcher.converters;

import cepsearcher.domains.Address;
import cepsearcher.dtos.AddressDTO;
import cepsearcher.dtos.ViaCEPAddressDTO;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class ViaCEPAddressDTOToAddress implements Converter<ViaCEPAddressDTO, Address> {

    @Synchronized
    @Nullable
    @Override
    public Address convert(ViaCEPAddressDTO source) {
        if (source == null) {
            return null;
        }

        final Address address = new Address();
        address.setCity(source.getLocalidade());
        address.setDistrict(source.getBairro());
        address.setState(source.getUf());
        address.setCep(source.getCep());
        address.setStreet(source.getLogradouro());
        return address;
    }
}
