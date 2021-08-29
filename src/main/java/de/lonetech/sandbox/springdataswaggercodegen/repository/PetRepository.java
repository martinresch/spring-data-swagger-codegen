package de.lonetech.sandbox.springdataswaggercodegen.repository;

import de.lonetech.sandbox.springdataswaggercodegen.domain.pet.Pet;
import org.springframework.data.repository.CrudRepository;

public interface PetRepository extends CrudRepository<Pet, Integer> {

  Pet findByExternalId(Long externalId);
}
