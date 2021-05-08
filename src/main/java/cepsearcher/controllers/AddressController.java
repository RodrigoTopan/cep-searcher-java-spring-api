package cepsearcher.controllers;


import cepsearcher.domain.Address;
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

    @GetMapping("/api/v1/address/{cep}")
    Mono<Address> find(@PathVariable String cep) {
        return this.addressService.findByCEP(cep);
    }
}
