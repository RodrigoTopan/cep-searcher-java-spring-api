package cepsearcher.controllers;


import cepsearcher.dtos.AddressDTO;
import cepsearcher.exceptions.BadRequestException;
import cepsearcher.services.AddressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping("/api/address/{cep}")
    Mono<AddressDTO> find(@PathVariable String cep) {
        if(cep.isEmpty() || cep.length() > 8) throw new BadRequestException("CEP inválido");

        return this.addressService.findByCEP(cep);
    }
}
