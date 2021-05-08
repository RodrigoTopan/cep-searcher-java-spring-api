package cepsearcher.converters;


import cepsearcher.dtos.AddressResponseDTO;
import cepsearcher.domains.Address;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class AddressReponseDTOToAddress implements Converter<AddressResponseDTO, Address>{

    @Synchronized
    @Nullable
    @Override
    public Address convert(AddressResponseDTO source) {
        if (source == null) {
            return null;
        }

        final Address address = new Address();
        return address;
    }
}
