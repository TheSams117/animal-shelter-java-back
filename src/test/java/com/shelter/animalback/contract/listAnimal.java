package com.shelter.animalback.contract;


import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.loader.PactBroker;
import au.com.dius.pact.provider.junitsupport.loader.PactBrokerAuth;
import com.shelter.animalback.domain.Animal;
import com.shelter.animalback.service.interfaces.AnimalService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Provider("AnimalShelterBack")
@PactBroker(url = "https://animal-shelter-ui.pactflow.io", authentication = @PactBrokerAuth(token = "MGI11RSqGXd8Cxhd7eRo3Q"))
public class listAnimal {
    @LocalServerPort
    private int port;

    @MockBean
    private AnimalService animalService;

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    void pactVerificationTestTemplate(PactVerificationContext context) {
        context.verifyInteraction();
    }

    @BeforeEach
    void setupPact(PactVerificationContext context) {
        context.setTarget(new HttpTestTarget("localhost", port));
    }

    /*
    @BeforeEach
    void setupCreateAnimal() {
        createAnimal(); // SI ESTÁ AQUÍ FUNCIONA
    }
    */


    private void createAnimal() {
        Animal animal = new Animal();
        animal.setName("Yesid");
        animal.setGender("Male");
        animal.setBreed("Bengali");
        animal.setVaccinated(true);
        animal.setVaccines(new String[]{"Leucemia"});
        ArrayList animals = new ArrayList();
        animals.add(animal);

        Mockito.when(animalService.getAll()).thenReturn(animals);
    }

    @State("there are animals")
    public void getAnimals() {
        var a = 1 + 1;
        // createAnimal(); //SI ESTÁ AQUÍ NO
    }
}