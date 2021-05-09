package cepsearcher.repositories.imperative;

import cepsearcher.domains.Address;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AddressRepository extends MongoRepository<Address, String> {
    Address findByCep(String cepRegex);
}
