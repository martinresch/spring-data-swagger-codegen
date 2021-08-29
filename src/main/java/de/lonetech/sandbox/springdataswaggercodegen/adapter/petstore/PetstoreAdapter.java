package de.lonetech.sandbox.springdataswaggercodegen.adapter.petstore;

import de.lonetech.sandbox.springdataswaggercodegen.domain.pet.Pet;
import de.lonetech.sandbox.springdataswaggercodegen.domain.pet.PetStatus;
import de.lonetech.sandbox.springdataswaggercodegen.foundation.ApplicationException;
import io.swagger.client.ApiException;
import io.swagger.client.api.PetApi;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PetstoreAdapter {

  // no component, but needs to be mocked for tests
  private PetApi petApi;

  public List<Pet> loadAllPets()
          throws ApplicationException {

    List<String> statusList = Stream.of(PetStatus.values())
            .map(s -> s.name().toLowerCase())
            .collect(Collectors.toList());

    List<io.swagger.client.model.Pet> externalPets;
    try {
      externalPets = getPetApi().findPetsByStatus(statusList);
    }
    catch (ApiException e) {
      throw new ApplicationException("pets cannot be loaded", e);
    }

    return PetMapper.INSTANCE.mapPatList(externalPets);
  }

  public PetApi getPetApi() {
    if (petApi == null) {
      petApi = new PetApi();
    }
    return petApi;
  }

  void setPetApi(PetApi petApi) {
    this.petApi = petApi;
  }
}
