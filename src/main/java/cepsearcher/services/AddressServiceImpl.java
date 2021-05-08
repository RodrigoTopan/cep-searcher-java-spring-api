package cepsearcher.services;

import cepsearcher.converters.AddressDTOToAddress;
import cepsearcher.converters.AddressToAddressDTO;
import cepsearcher.converters.ViaCEPAddressDTOToAddress;
import cepsearcher.domains.Address;
import cepsearcher.dtos.AddressDTO;
import cepsearcher.dtos.ViaCEPAddressDTO;
import cepsearcher.repositories.reactive.AddressReactiveRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class AddressServiceImpl implements AddressService {
    private String viaCepUri;
    private String viaCepFormat;
    private final RestTemplate restTemplate;
    private final AddressReactiveRepository addressReactiveRepository;
    private final AddressToAddressDTO addressToAddressDTO;
    private final AddressDTOToAddress addressDTOToAddress;
    private final ViaCEPAddressDTOToAddress viaCEPAddressDTOToAddress;

    public AddressServiceImpl(
            @Value("${viacep.api.host}") String viaCepUri,
            @Value("${viacep.api.responseFormat}") String viaCepFormat,
            RestTemplate restTemplate,
            AddressReactiveRepository addressReactiveRepository,
            AddressToAddressDTO addressToAddressDTO,
            AddressDTOToAddress addressDTOToAddress,
            ViaCEPAddressDTOToAddress viaCEPAddressDTOToAddress
    ) {
        this.viaCepUri = viaCepUri;
        this.viaCepFormat = viaCepFormat;
        this.restTemplate = restTemplate;
        this.addressReactiveRepository = addressReactiveRepository;
        this.addressToAddressDTO = addressToAddressDTO;
        this.addressDTOToAddress = addressDTOToAddress;
        this.viaCEPAddressDTOToAddress = viaCEPAddressDTOToAddress;
    }

    @Override
    public Mono<AddressDTO> findByCEP(String cep) {
        log.debug("[ADDRESS SERVICE][FIND BY CEP]", cep);
        ViaCEPAddressDTO viaCEPAddressDTO = restTemplate.getForObject(this.viaCepUri + cep + this.viaCepFormat, ViaCEPAddressDTO.class);
        Address address = viaCEPAddressDTOToAddress.convert(viaCEPAddressDTO);
        AddressDTO addressDTO = addressToAddressDTO.convert(address);
        return Mono.just(addressDTO);
    }
}
