package de.lonetech.sandbox.springdataswaggercodegen.service;

import de.lonetech.sandbox.springdataswaggercodegen.repository.PetRepository;
import de.lonetech.sandbox.springdataswaggercodegen.adapter.petstore.PetstoreAdapter;
import de.lonetech.sandbox.springdataswaggercodegen.domain.pet.Pet;
import de.lonetech.sandbox.springdataswaggercodegen.foundation.ApplicationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PetDataLoad {

  @Autowired
  PetRepository petRepository;

  public void loadAndStore() throws ApplicationException {
    List<Pet> pets = new PetstoreAdapter().loadAllPets();
    pets.forEach(p -> p.getTags().forEach(t -> t.setPet(p)));

    pets.forEach(pet -> {
      Pet existingPet = petRepository.findByExternalId(pet.getExternalId());
      if (existingPet != null) {
        pet.setId(existingPet.getId());
      }
    });

    petRepository.saveAll(pets);
  }

}
