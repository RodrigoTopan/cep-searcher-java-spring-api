package cepsearcher.services;

import cepsearcher.domain.Address;
import reactor.core.publisher.Mono;

public interface AddressService {
    Mono<Address> findByCEP(String cep);
}
