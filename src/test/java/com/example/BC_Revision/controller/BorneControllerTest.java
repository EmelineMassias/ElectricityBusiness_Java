//package com.example.BC_Revision.controller;
//
//import com.example.BC_Revision.dto.BorneDto;
//import com.example.BC_Revision.dto.LieuDto;
//import com.example.BC_Revision.model.Borne;
//import com.example.BC_Revision.service.BorneService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//
//import java.util.List;
//
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//public class BorneControllerTest {
//    @Autowired
//    MockMvc mockMvc; // cet objet imite ce que fait Postman, Hoppscotch, Insomnia, Swagger ou le front Angular, React ou Vue
//
//    @Autowired
//    BorneService borneService;
//
//    @Autowired
//    ObjectMapper objectMapper; // cet objet va sérialiser des objets DTO
//
//    @BeforeEach
//    void beforeEach() {
//        List<BorneDto> bornes = borneService.getAllBornes();
//        for (BorneDto borneDto : bornes) {
//            borneService.deleteBorne(borneDto.getId());
//        }
//    }
//
//    @Test
//    void testPostBorne() throws Exception {
//
//        String nom = "test";
//        LieuDto lieuDto = new LieuDto(1L);
//        BorneDto borneDto = new BorneDto(nom, lieuDto);
//
//        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
//                .post("/api/bornes")
//                // On place dans le corps de la requête la version Json de l'objet borneDto
//                .content(objectMapper.writeValueAsString(borneDto))
//                .contentType(MediaType.APPLICATION_JSON);
//
//        mockMvc.perform(requestBuilder)
//                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.nom").value(nom))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.lieu.nom").value("Dijon"))
//                .andExpect(status().isCreated())
//                .andDo(MockMvcResultHandlers.print());
//    }andDo(MockMvcResultHandlers.print());
//    }
//}
