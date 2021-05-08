package cepsearcher.converters;


import cepsearcher.commands.AddressCommand;
import cepsearcher.domain.Address;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class AddressCommandToCategory implements Converter<AddressCommand, Address>{

    @Synchronized
    @Nullable
    @Override
    public Address convert(AddressCommand source) {
        if (source == null) {
            return null;
        }

        final Address address = new Address();
        return address;
    }
}
