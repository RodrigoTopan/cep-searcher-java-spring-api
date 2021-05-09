package cepsearcher.services;

import cepsearcher.converters.AddressDTOToAddress;
import cepsearcher.converters.AddressToAddressDTO;
import cepsearcher.converters.ViaCEPAddressDTOToAddress;
import cepsearcher.domains.Address;
import cepsearcher.dtos.AddressDTO;
import cepsearcher.dtos.ViaCEPAddressDTO;
import cepsearcher.exceptions.BadRequestException;
import cepsearcher.exceptions.NotFoundException;
import cepsearcher.repositories.imperative.AddressRepository;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Slf4j
@Service
public class AddressServiceImpl implements AddressService {
    private String viaCepHost;
    private String viaCepFormat;
    private RestTemplate restTemplate;
    private AddressRepository addressRepository;
    private AddressToAddressDTO addressToAddressDTO;
    private AddressDTOToAddress addressDTOToAddress;
    private ViaCEPAddressDTOToAddress viaCEPAddressDTOToAddress;

    public AddressServiceImpl(
            @Value("${viacep.api.host}") String viaCepHost,
            @Value("${viacep.api.responseFormat}") String viaCepFormat,
            RestTemplate restTemplate,
            AddressRepository addressRepository,
            AddressToAddressDTO addressToAddressDTO,
            AddressDTOToAddress addressDTOToAddress,
            ViaCEPAddressDTOToAddress viaCEPAddressDTOToAddress
    ) {
        this.viaCepHost = viaCepHost;
        this.viaCepFormat = viaCepFormat;
        this.restTemplate = restTemplate;
        this.addressRepository = addressRepository;
        this.addressToAddressDTO = addressToAddressDTO;
        this.addressDTOToAddress = addressDTOToAddress;
        this.viaCEPAddressDTOToAddress = viaCEPAddressDTOToAddress;
    }

    @Override
    public AddressDTO findByCEP(String cep) {
        log.debug("[ADDRESS SERVICE][FIND BY CEP]", cep);
        String verifiedCEP = this.verifyCEP(cep);

        AddressDTO addressResponseDTO = this.findAddressWithDB(verifiedCEP);
        if(addressResponseDTO != null){
            return addressResponseDTO;
        }
        return null;
        //execute fallback to viacep
        //return this.findAddressWithWebservice(cep);
    }

    private String verifyCEP(String cep){
        String verifiedCEP;
        if(cep.isEmpty() || cep.length() > 8) throw new BadRequestException("CEP inválido");
        verifiedCEP = cep.replaceAll("[^a-zA-Z0-9]", "");
        if(cep.length() < 8) {
            verifiedCEP = String.format("%-" + cep + "s", 8);
        }
        return verifiedCEP;
    }

    private AddressDTO findAddressWithWebservice(String cep) {
        try {
            final String viaCepUri = this.viaCepHost + cep + this.viaCepFormat;
            ViaCEPAddressDTO viaCEPAddressDTO = this.restTemplate.getForObject(viaCepUri, ViaCEPAddressDTO.class);

            if(viaCEPAddressDTO.getCep() == null) throw new NotFoundException("CEP não encontrado");

            Address address = this.viaCEPAddressDTOToAddress.convert(viaCEPAddressDTO);

            //if(this.addressRepository.findByCep(cep) == null){
                this.addressRepository.save(address);
            //}

            AddressDTO addressDTO = this.addressToAddressDTO.convert(address);
            return addressDTO;
        }catch (HttpStatusCodeException apiError) {
            if(apiError.getStatusCode() == HttpStatus.BAD_REQUEST && cep.length()  < 8) return this.findByCEP(cep + "0");
        }

        throw new NotFoundException("CEP não encontrado");
    }

    private AddressDTO findAddressWithDB(String cep) {
        Address address = this.addressRepository.findByCep(cep);
        AddressDTO addressDTO = this.addressToAddressDTO.convert(address);
        return addressDTO;
    }
}
