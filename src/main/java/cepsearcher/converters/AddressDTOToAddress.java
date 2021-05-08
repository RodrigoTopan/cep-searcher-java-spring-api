package cepsearcher.converters;


import cepsearcher.dtos.AddressDTO;
import cepsearcher.domains.Address;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class AddressDTOToAddress implements Converter<AddressDTO, Address>{

    @Synchronized
    @Nullable
    @Override
    public Address convert(AddressDTO source) {
        if (source == null) {
            return null;
        }

        final Address address = new Address();
        return address;
    }
}
