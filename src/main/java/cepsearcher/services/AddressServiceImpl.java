package cepsearcher.services;

import cepsearcher.converters.AddressDTOToAddress;
import cepsearcher.converters.AddressToAddressDTO;
import cepsearcher.converters.ViaCEPAddressDTOToAddress;
import cepsearcher.domains.Address;
import cepsearcher.dtos.AddressDTO;
import cepsearcher.dtos.ViaCEPAddressDTO;
import cepsearcher.exceptions.BadRequestException;
import cepsearcher.exceptions.InternalServerException;
import cepsearcher.exceptions.NotFoundException;
import cepsearcher.repositories.imperative.AddressRepository;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;


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
        log.info("[ADDRESS SERVICE][FIND BY CEP] {}", cep);
        String verifiedCEP = formatCEP(verifyCEP(cep));

        AddressDTO addressResponseDTO = this.findAddressWithDB(verifiedCEP);
        if(addressResponseDTO != null){
            log.info("[ADDRESS SERVICE][FIND BY CEP] RETURNING FOUND DB ADDRESS");
            return addressResponseDTO;
        }

        log.info("[ADDRESS SERVICE][FIND BY CEP] EXECUTING FALLBACK TO VIACEP CEP WEBSERVICE");
        return this.findAddressWithWebService(verifiedCEP);
    }

    private String verifyCEP(String cep){
        if(cep.isEmpty() || cep.length() > 8) throw new BadRequestException("CEP inválido");
        return cep;
    }

    private String formatCEP(String cep){
        //Remove special, whitespaces digits
        String formattedCEP = cep.replaceAll("[^a-zA-Z0-9]", "").trim();

        if(formattedCEP.length() < 8) {
            //Add right zeros to fill 8 digits
            formattedCEP = String.format("%-8s", formattedCEP).replace(' ', '0');;
        }
        log.debug("FORMATTED CEP: {}",cep);
        
        return formattedCEP;
    }

    private String getNewRetryCEP(String cep){
        String cepWithoutRightZeros = cep.replaceAll("0*$", "").trim();
        Integer cepLength = cepWithoutRightZeros.length();

        if(cepLength <= 0) throw new NotFoundException("CEP não encontrado");

        String cepWithoutLastDigit = cepWithoutRightZeros.substring(0,cepLength - 1);

        String fullfilledCEP = this.formatCEP(cepWithoutLastDigit);

        log.debug("RETRY CEP FULFILLED: {}", fullfilledCEP);

        return fullfilledCEP;
    }

    private AddressDTO findAddressWithWebService(String cep) {
       try {
           if(cep == null || cep.equals("00000000")) throw new NotFoundException("CEP não encontrado");

           log.info("[ADDRESS SERVICE][FIND BY CEP] SEARCHING WITH CEP: {}", cep);

           final String viaCepUri = this.viaCepHost + cep + this.viaCepFormat;
           ViaCEPAddressDTO viaCEPAddressDTO = this.restTemplate.getForObject(viaCepUri, ViaCEPAddressDTO.class);

           // IF CEP NOT FOUND, RETRY WITH NEW FORMAT
           if (viaCEPAddressDTO == null || viaCEPAddressDTO.getCep() == null)
               return this.findAddressWithWebService(getNewRetryCEP(cep));

           Address address = this.viaCEPAddressDTOToAddress.convert(viaCEPAddressDTO);
           //save if not exists or update registry
           this.addressRepository.save(address);

           AddressDTO addressDTO = this.addressToAddressDTO.convert(address);
           return addressDTO;
       }catch (HttpStatusCodeException apiError) {
           if(apiError.getStatusCode() == HttpStatus.BAD_REQUEST && cep.length() < 8) {
               // IF CEP NOT FOUND, RETRY WITH NEW FORMAT
               return this.findAddressWithWebService(getNewRetryCEP(cep));
           }
           else throw new NotFoundException("CEP não encontrado");
       }
    }

    private AddressDTO findAddressWithDB(String cep) {
        Address address = this.addressRepository.findByCep(cep);
        AddressDTO addressDTO = this.addressToAddressDTO.convert(address);
        return addressDTO;
    }
}
