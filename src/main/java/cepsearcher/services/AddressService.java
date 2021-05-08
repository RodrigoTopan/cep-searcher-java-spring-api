package cepsearcher.services;

import cepsearcher.dtos.ViaCEPAddressDTO;
import reactor.core.publisher.Mono;

public interface AddressService {
    Mono<ViaCEPAddressDTO> findByCEP(String cep);
}
