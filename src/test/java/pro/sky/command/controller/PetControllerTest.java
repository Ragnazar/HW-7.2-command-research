package pro.sky.command.controller;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

@OpenAPIDefinition
public class PetControllerTest {



    @Test
    public void ShouldReturnAddedPetEntry(){
        RequestBuilder request = MockMvcRequestBuilders
                .post("/")
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"id\":1,\"name_pet\":\"Bob\",\"kind\":dog,\"owner_id\":1015168334}")
                .contentType(MediaType.APPLICATION_JSON);


    }
    @Test
    public void findAll_PetsFound_ShouldReturnFoundPetEntries() throws Exception {
    }

}