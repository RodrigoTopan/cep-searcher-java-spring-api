package cepsearcher.repositories.reactive;

import cepsearcher.domain.Address;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface AddressReactiveRepository extends ReactiveMongoRepository<Address, String> {
}
