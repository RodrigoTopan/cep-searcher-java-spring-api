package cepsearcher.services;

import cepsearcher.dtos.AddressDTO;

public interface AddressService {
    AddressDTO findByCEP(String cep);
}
