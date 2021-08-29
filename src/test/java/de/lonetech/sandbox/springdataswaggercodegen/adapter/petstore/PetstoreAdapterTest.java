package de.lonetech.sandbox.springdataswaggercodegen.adapter.petstore;

import de.lonetech.sandbox.springdataswaggercodegen.domain.pet.Pet;
import de.lonetech.sandbox.springdataswaggercodegen.domain.pet.PetStatus;
import de.lonetech.sandbox.springdataswaggercodegen.domain.pet.Tag;
import io.swagger.client.api.PetApi;
import io.swagger.client.model.Category;
import io.swagger.client.model.Pet.StatusEnum;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class PetstoreAdapterTest {

  @MockBean
  private PetApi petApiMock;

  @Test
  void loadAndTransform() throws Exception {
    PetstoreAdapter adapter = new PetstoreAdapter();
    adapter.setPetApi(petApiMock);

    // prepare
    List<io.swagger.client.model.Pet> mockedResult = new ArrayList<>();
    mockedResult.add(createExternalPet(1L, "PET_1", StatusEnum.AVAILABLE, "CATEGORY_1", Arrays.asList("PHOTO_URL_1_1", "PHOTO_URL_1_2"), Collections.singletonList("TAG_1")));
    mockedResult.add(createExternalPet(2L, "PET_2", StatusEnum.SOLD, "CATEGORY_2", Collections.singletonList("PHOTO_URL_1"), Arrays.asList("TAG_2_1", "TAG_2_2")));
    Mockito.when(petApiMock.findPetsByStatus(Mockito.any())).thenReturn(mockedResult);

    // execute
    List<Pet> result = adapter.loadAllPets();

    // verify
    assertNotNull(result);
    assertEquals(2, result.size());
    result = result.stream().sorted(Comparator.comparing(Pet::getExternalId)).collect(Collectors.toList());
    assertEquals(Long.valueOf(1), result.get(0).getExternalId());
    assertEquals("PET_1", result.get(0).getName());
    assertEquals(PetStatus.AVAILABLE, result.get(0).getStatus());
    assertEquals(Long.valueOf(1), result.get(0).getCategory().getExternalId());
    assertEquals("CATEGORY_1", result.get(0).getCategory().getName());
    assertEquals(2, result.get(0).getPhotoUrls().size());
    assertTrue(result.get(0).getPhotoUrls().containsAll(Arrays.asList("PHOTO_URL_1_1", "PHOTO_URL_1_2")));
    assertEquals(1, result.get(0).getTags().size());
    assertEquals(Long.valueOf(1), result.get(0).getTags().iterator().next().getExternalId());
    assertEquals("TAG_1", result.get(0).getTags().iterator().next().getName());

    assertEquals(Long.valueOf(2), result.get(1).getExternalId());
    assertEquals("PET_2", result.get(1).getName());
    assertEquals(PetStatus.SOLD, result.get(1).getStatus());
    assertEquals(Long.valueOf(2), result.get(1).getCategory().getExternalId());
    assertEquals("CATEGORY_2", result.get(1).getCategory().getName());
    assertEquals(1, result.get(1).getPhotoUrls().size());
    assertTrue(result.get(1).getPhotoUrls().contains("PHOTO_URL_1"));
    List<Tag> tags = new ArrayList<>(result.get(1).getTags()).stream().sorted(Comparator.comparing(Tag::getName)).collect(Collectors.toList());
    assertEquals(2, tags.size());
    assertEquals(Long.valueOf(2), tags.get(0).getExternalId());
    assertEquals("TAG_2_1", tags.get(0).getName());
    assertEquals(Long.valueOf(2), tags.get(1).getExternalId());
    assertEquals("TAG_2_2", tags.get(1).getName());
  }

  private io.swagger.client.model.Pet createExternalPet(Long id, String name, StatusEnum status, String categoryName, List<String> photoUrls, List<String> tags) {
    io.swagger.client.model.Pet pet = new io.swagger.client.model.Pet();
    pet.setId(id);
    pet.setName(name);
    pet.setStatus(status);

    Category category = new Category();
    category.setId(id);
    category.setName(categoryName);
    pet.setCategory(category);

    pet.setPhotoUrls(photoUrls);

    pet.setTags(tags.stream().map(s -> {
      io.swagger.client.model.Tag tag = new io.swagger.client.model.Tag();
      tag.setId(id);
      tag.setName(s);
      return tag;
    }).collect(Collectors.toList()));

    return pet;
  }
}

