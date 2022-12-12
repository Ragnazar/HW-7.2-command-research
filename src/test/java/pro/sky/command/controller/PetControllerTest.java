package pro.sky.command.controller;

import org.mockito.Mock;
import org.mortbay.jetty.webapp.WebAppContext;
import org.springframework.test.context.TestContext;
import org.testng.annotations.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import pro.sky.command.model.Pet;
import pro.sky.command.service.PetService;

import java.util.Arrays;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContext.class, WebAppContext.class})
@WebAppConfiguration
public class PetControllerTest {

    private MockMvc mockMvc;

@Mock
    private PetService petServiceMock;


    @Test
    public void findAll_PetsFound_ShouldReturnFoundPetEntries() throws Exception {
        Pet first = new Pet();
        first.setNamePet("Bob");
        first.setId(111L);
        first.setKindOfAnimal("dog");

        Pet second = new Pet();
        first.setNamePet("Ric");
        first.setId(222L);
        first.setKindOfAnimal("dog");

        when(petServiceMock.getAll()).thenReturn(Arrays.asList(first, second));

        mockMvc.perform(get("/pet/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(111L)))
                .andExpect(jsonPath("$[0].namePet", is("Bob")))
                .andExpect(jsonPath("$[0].kindOfAnimal", is("dog")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].namePet", is("Ric")))
                .andExpect(jsonPath("$[1].kindOfAnimal", is("dog")));

        verify(petServiceMock, times(1)).getAll();
        verifyNoMoreInteractions(petServiceMock);
    }
}