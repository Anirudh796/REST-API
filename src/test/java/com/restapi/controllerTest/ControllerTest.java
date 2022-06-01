package com.restapi.controllerTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        Response response = new Response(500, "Authentication failed");
        
        String jsonString = objectMapper.writeValueAsString(request);
        String expectedJson = objectMapper.writeValueAsString(response);
        
        
        MvcResult result = mvc.perform(post("/schedule/message").contentType(MediaType.APPLICATION_JSON_VALUE).content(jsonString).accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isBadRequest()).andReturn();
        String actualJson = result.getResponse().getContentAsString();
        
        assertEquals(actualJson,expectedJson );
    }
    
    @Test
    void validationEmptyMessage() throws Exception {
        Client testClient = new Client(1, "Anirudh", "e1lcbgd8fodepvxflnjcdroykywan8tk");
        when(clientService.validateToken("e1lcbgd8fodepvxflnjcdroykywan8tk")).thenReturn(testClient);
        
        //empty message 
        Request request = new Request("9493894489", "2022-06-30T16:00:00", "");
        Response response = new Response(406, "Message should not be empty");
        
        String jsonString = objectMapper.writeValueAsString(request);
        String expectedJson = objectMapper.writeValueAsString(response);
        
        MvcResult result = mvc.perform(post("/schedule/messages").contentType(MediaType.APPLICATION_JSON_VALUE).header("token", "e1lcbgd8fodepvxflnjcdroykywan8tk").content(jsonString).accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk()).andReturn();
        String actualJson = result.getResponse().getContentAsString();
       
        
        assertEquals(actualJson,expectedJson );
       
    }
    @Test
    void validationInvalidphonenumber() throws Exception {
        Client testClient = new Client(1, "Anirudh", "e1lcbgd8fodepvxflnjcdroykywan8tk");
        when(clientService.validateToken("e1lcbgd8fodepvxflnjcdroykywan8tk")).thenReturn(testClient);
        
        //Invalid Phone Number
        Request request = new Request("93894489", "2022-06-30T12:00:00", "Hello!");
        Response response = new Response(406, "Invalid Phone number");
        
        String jsonString = objectMapper.writeValueAsString(request);
        String expectedJson = objectMapper.writeValueAsString(response);
        
        MvcResult result = mvc.perform(post("/schedule/messages").contentType(MediaType.APPLICATION_JSON_VALUE).header("token", "e1lcbgd8fodepvxflnjcdroykywan8tk").content(jsonString).accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk()).andReturn();
        String actualJson = result.getResponse().getContentAsString();
       
        
        assertEquals(actualJson,expectedJson );
       
    }
    @Test
    void validationInvalidDateFormat() throws Exception {
        Client testClient = new Client(1, "Anirudh", "e1lcbgd8fodepvxflnjcdroykywan8tk");
        when(clientService.validateToken("e1lcbgd8fodepvxflnjcdroykywan8tk")).thenReturn(testClient);
        
        //Invalid Date format
        Request request = new Request("9493894489", "2022-06-30Txyz", "Hello!");
        Response response = new Response(406, "Invalid date format");
        
        String jsonString = objectMapper.writeValueAsString(request);
        String expectedJson = objectMapper.writeValueAsString(response);
        
        MvcResult result = mvc.perform(post("/schedule/messages").contentType(MediaType.APPLICATION_JSON_VALUE).header("token", "e1lcbgd8fodepvxflnjcdroykywan8tk").content(jsonString).accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk()).andReturn();
        String actualJson = result.getResponse().getContentAsString();
        
        
        assertEquals(actualJson,expectedJson );
       
    }
    


}
