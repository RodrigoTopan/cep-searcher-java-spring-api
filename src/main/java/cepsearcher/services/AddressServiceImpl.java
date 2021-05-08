package cepsearcher.services;

import cepsearcher.converters.AddressCommandToAddress;
import cepsearcher.converters.AddressToAddressCommand;
import cepsearcher.domain.Address;
import cepsearcher.repositories.reactive.AddressReactiveRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class AddressServiceImpl implements AddressService {
    private final AddressReactiveRepository addressReactiveRepository;
    private final AddressCommandToAddress addressCommandToAddress;
    private final AddressToAddressCommand addressToAddressCommand;

    public AddressServiceImpl(AddressReactiveRepository addressReactiveRepository, AddressCommandToAddress addressCommandToAddress, AddressToAddressCommand addressToAddressCommand) {
        this.addressReactiveRepository = addressReactiveRepository;
        this.addressCommandToAddress = addressCommandToAddress;
        this.addressToAddressCommand = addressToAddressCommand;
    }

    @Override
    public Mono<Address> findByCEP(String cep) {
        return addressReactiveRepository.findById(cep);
    }
}
