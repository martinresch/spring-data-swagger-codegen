package de.lonetech.sandbox.springdataswaggercodegen.service;

import de.lonetech.sandbox.springdataswaggercodegen.adapter.csv.PetCSVAdapter;
import de.lonetech.sandbox.springdataswaggercodegen.repository.PetRepository;
import de.lonetech.sandbox.springdataswaggercodegen.domain.pet.Pet;
import de.lonetech.sandbox.springdataswaggercodegen.foundation.ApplicationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PetExport {

  @Autowired
  PetRepository petRepository;

  @Autowired
  PetCSVAdapter petCSVAdapter;

  Logger logger = LoggerFactory.getLogger(PetExport.class);

  public void export(String fileName) throws ApplicationException {
    if (petRepository.count() < 1) {
      logger.error("no Pet found to export");
      return;
    }

    Iterable<Pet> pets = petRepository.findAll();
    petCSVAdapter.export(fileName, pets);
  }

}
