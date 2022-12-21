package pro.sky.command.controller;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pro.sky.command.repository.PetRepository;
import pro.sky.command.service.PetService;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@WebMvcTest(PetController.class)
public class PetControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PetRepository repository;

    @Mock
    private PetService petServiceMock;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new PetController(petServiceMock)).build();
    }

    @Test
    public void ShouldReturnAddedPetEntry() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .post("/")
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"id\":1,\"name_pet\":\"Bob\",\"kind\":dog,\"owner_id\":1015168334}")
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk()).andReturn();

    }
    @Test
    public void findAll_PetsFound_ShouldReturnFoundPetEntries() throws Exception {
    }

}