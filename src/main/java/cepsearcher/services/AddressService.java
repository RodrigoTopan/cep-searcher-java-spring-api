package cepsearcher.services;

import cepsearcher.dtos.AddressDTO;
import reactor.core.publisher.Mono;

public interface AddressService {
    Mono<AddressDTO> findByCEP(String cep);
}
