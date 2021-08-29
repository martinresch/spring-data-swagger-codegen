package de.lonetech.sandbox.springdataswaggercodegen.adapter.petstore;


import de.lonetech.sandbox.springdataswaggercodegen.domain.pet.Pet;
import de.lonetech.sandbox.springdataswaggercodegen.domain.pet.PetCategory;
import de.lonetech.sandbox.springdataswaggercodegen.domain.pet.Tag;
import io.swagger.client.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface PetMapper {

  PetMapper INSTANCE = Mappers.getMapper(PetMapper.class);

  @Mapping(source = "id", target = "externalId")
  List<Pet> mapPatList(List<io.swagger.client.model.Pet> petList);

  @Mapping(source = "id", target = "externalId")
  Pet mapPet(io.swagger.client.model.Pet pet);

  @Mapping(source = "id", target = "externalId")
  PetCategory mapPetCategory(Category value);

  @Mapping(source = "id", target = "externalId")
  Tag mapTag(io.swagger.client.model.Tag tag);
}
