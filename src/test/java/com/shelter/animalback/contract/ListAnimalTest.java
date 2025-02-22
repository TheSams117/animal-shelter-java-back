package com.shelter.animalback.contract;


import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.loader.PactBroker;
import au.com.dius.pact.provider.junitsupport.loader.PactBrokerAuth;
import au.com.dius.pact.provider.spring.junit5.MockMvcTestTarget;
import com.shelter.animalback.controller.AnimalController;
import com.shelter.animalback.domain.Animal;
import com.shelter.animalback.service.interfaces.AnimalService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

@ExtendWith(MockitoExtension.class)
@Provider("AnimalShelterBack")
@PactBroker(host = "${PACT_BROKER_BASE_URL}", scheme = "${PACT_BROKER_SCHEMA}"
        , authentication = @PactBrokerAuth(token = "${PACT_BROKER_TOKEN}"))
public class ListAnimalTest {

    @Mock
    private AnimalService animalService;

    @InjectMocks
    private AnimalController animalController;

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    void pactVerificationTestTemplate(PactVerificationContext context) {
        context.verifyInteraction();
    }

    @BeforeEach
    void before(PactVerificationContext context) {
        System.setProperty("pact.verifier.publishResults", "true");
        System.setProperty("pact.provider.version", "1.0");

        MockMvcTestTarget testTarget = new MockMvcTestTarget();
        testTarget.setControllers(animalController);
        context.setTarget(testTarget);
    }

    @State("has animals")
    public void noAnimals() {
        Animal animal = new Animal();
        animal.setName("Yesid");
        animal.setGender("Male");
        animal.setBreed("Bengali");
        animal.setVaccinated(false);

        ArrayList animals = new ArrayList();
        animals.add(animal);
        Mockito.when(animalService.getAll()).thenReturn(animals);
    }
}
