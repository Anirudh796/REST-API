package com.restapi.controllerTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.http.MediaType;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restapi.entity.Client;
import com.restapi.entity.Request;
import com.restapi.entity.Response;
import com.restapi.service.ClientService;
import com.restapi.service.MessageService;

@SpringBootTest
@AutoConfigureMockMvc
public class ControllerTest {
	
	@MockBean
    MessageService msgService;
    @MockBean
    ClientService clientService;

    @Autowired
    private MockMvc mvc;
    
    public static ObjectMapper objectMapper = new ObjectMapper();
    
    @Test
    void authentication() throws Exception {
        Request request = new Request("9493894489", "2022-05-31T18:00:00", "Client Message");
        String jsonString = objectMapper.writeValueAsString(request);
        MvcResult result = mvc.perform(post("/schedule/message").contentType(MediaType.APPLICATION_JSON_VALUE).content(jsonString).accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isBadRequest()).andReturn();
        System.out.println("result" + result.toString());

        String actualResponseString = result.getResponse().getContentAsString();
        
        System.out.println("actualResponseString" + actualResponseString);
        
        Response response = objectMapper.readValue(actualResponseString, Response.class);
        
        System.out.println(response.toString()+ "test contoler");
        
        assertThat(response.getCode()).isEqualTo(500);
    }
    
    @Test
    void validation() throws Exception {
        Client testClient = new Client(1, "Anirudh", "e1lcbgd8fodepvxflnjcdroykywan8tk");
        when(clientService.validateToken("e1lcbgd8fodepvxflnjcdroykywan8tk")).thenReturn(testClient);
        
        //empty message will be sent.........
        Request request = new Request("9493894489", "2022-05-31T18:50:00", "");
        
        String jsonString = objectMapper.writeValueAsString(request);
        MvcResult result = mvc.perform(post("/schedule/messages").contentType(MediaType.APPLICATION_JSON_VALUE).header("token", "e1lcbgd8fodepvxflnjcdroykywan8tk").content(jsonString).accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk()).andReturn();

        String actualResponseString = result.getResponse().getContentAsString();
        Response response = objectMapper.readValue(actualResponseString, Response.class);

        //response code for non-valid request body - 406
        //expected message = "message should not be empty"
//        assertThat(response.getCode()).isEqualTo(406);
//        assertThat(response.getMessage() == "message should not be empty");
    }


}
