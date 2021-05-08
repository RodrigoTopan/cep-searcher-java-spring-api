package cepsearcher.services;

import cepsearcher.converters.AddressReponseDTOToAddress;
import cepsearcher.converters.AddressToAddressResponseDTO;
import cepsearcher.dtos.ViaCEPAddressDTO;
import cepsearcher.repositories.reactive.AddressReactiveRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class AddressServiceImpl implements AddressService {
    private final RestTemplate restTemplate;
    private final AddressReactiveRepository addressReactiveRepository;
    private final AddressToAddressResponseDTO addressToAddressResponseDTO;
    private final AddressReponseDTOToAddress addressReponseDTOToAddress;

    public AddressServiceImpl(RestTemplate restTemplate, AddressReactiveRepository addressReactiveRepository, AddressToAddressResponseDTO addressToAddressResponseDTO, AddressReponseDTOToAddress addressReponseDTOToAddress) {
        this.restTemplate = restTemplate;
        this.addressReactiveRepository = addressReactiveRepository;
        this.addressToAddressResponseDTO = addressToAddressResponseDTO;
        this.addressReponseDTOToAddress = addressReponseDTOToAddress;
    }

    @Override
    public Mono<ViaCEPAddressDTO> findByCEP(String cep) {
        ViaCEPAddressDTO viaCEPAddressDTO = restTemplate.getForObject(
                "https://viacep.com.br/ws/"+ cep +"/json/", ViaCEPAddressDTO.class);

        return Mono.just(viaCEPAddressDTO);
    }
}
