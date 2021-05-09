package cepsearcher.controllers;


import cepsearcher.dtos.AddressDTO;
import cepsearcher.services.AddressService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @GetMapping("/api/address/{cep}")
    AddressDTO find(@PathVariable String cep) {
        return this.addressService.findByCEP(cep);
    }
}
