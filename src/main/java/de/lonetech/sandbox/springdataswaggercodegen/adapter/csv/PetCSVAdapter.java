package de.lonetech.sandbox.springdataswaggercodegen.adapter.csv;

import de.lonetech.sandbox.springdataswaggercodegen.domain.pet.Pet;
import de.lonetech.sandbox.springdataswaggercodegen.domain.pet.Tag;
import de.lonetech.sandbox.springdataswaggercodegen.foundation.ApplicationException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;

@Component
public class PetCSVAdapter {

  public void export(String fileName, Iterable<Pet> pets) throws ApplicationException {
    try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(fileName));
         CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
                 .withHeader("name", "category", "status", "photo url 1", "photo url 2", "photo url 3", "tag name 1", "tag name 2").withDelimiter('|'))) {

      for (Pet pet : pets) {
        csvPrinter.printRecord(toCSVStructure(pet));
      }

      csvPrinter.flush();
    }
    catch (IOException e) {
      throw new ApplicationException("export file cannot be created", e);
    }
  }


  private ArrayList<String> toCSVStructure(Pet pet) {
    ArrayList<String> l = new ArrayList<>();

    l.add(pet.getName());
    l.add(pet.getCategory() != null ? pet.getCategory().getName() : StringUtils.EMPTY);
    l.add(pet.getStatus() != null ? pet.getStatus().name() : StringUtils.EMPTY);

    int photoUrlSize = pet.getPhotoUrls() != null ? pet.getPhotoUrls().size() : 0;
    l.add(photoUrlSize > 0 ? pet.getPhotoUrls().get(0) : StringUtils.EMPTY);
    l.add(photoUrlSize > 1 ? pet.getPhotoUrls().get(1) : StringUtils.EMPTY);
    l.add(photoUrlSize > 2 ? pet.getPhotoUrls().get(2) : StringUtils.EMPTY);

    int tagSize = pet.getTags() != null ? pet.getTags().size() : 0;
    if (tagSize > 0) {
      Iterator<Tag> tagIt = pet.getTags().iterator();
      l.add(tagIt.next().getName());
      l.add(tagSize > 1 ? tagIt.next().getName() : StringUtils.EMPTY);
      l.add(tagSize > 2 ? tagIt.next().getName() : StringUtils.EMPTY);
    }

    return l;
  }

}
