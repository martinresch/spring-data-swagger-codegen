package de.lonetech.sandbox.springdataswaggercodegen.repository;

import de.lonetech.sandbox.springdataswaggercodegen.Application;
import de.lonetech.sandbox.springdataswaggercodegen.domain.pet.Pet;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class PetRepositoryTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private PetRepository petRepository;

  @MockBean
  Application app;

  @Test
  void testFindByExternalId() {

    // given
    Pet pet1 = new Pet();
    pet1.setExternalId(1L);
    pet1.setName("PET_1");
    entityManager.persist(pet1);

    Pet pet2 = new Pet();
    pet2.setExternalId(2L);
    pet2.setName("PET_2");
    entityManager.persist(pet2);

    entityManager.flush();

    assertEquals("PET_1", petRepository.findByExternalId(1L).getName());
    assertEquals("PET_2", petRepository.findByExternalId(2L).getName());
  }
}
