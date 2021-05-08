package cepsearcher.converters;

import cepsearcher.commands.AddressCommand;
import cepsearcher.domain.Address;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class AddressToAddressCommand implements Converter<Address, AddressCommand> {

    @Synchronized
    @Nullable
    @Override
    public AddressCommand convert(Address source) {
        if (source == null) {
            return null;
        }

        final AddressCommand addressCommand = new AddressCommand();
        return addressCommand;
    }
}
