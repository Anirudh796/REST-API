package com.restapi.serviceTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.restapi.data.Clientdata;
import com.restapi.entity.Client;
import com.restapi.exceptions.SQLErrorExceptions;
import com.restapi.service.ClientService;

@SpringBootTest
public class ClientServiceTest {
	
	@Autowired
    ClientService clientService;
	
	@MockBean
    Clientdata clientdata;
	
	@Test
    void validToken() throws SQLErrorExceptions {
        Client testClient = new Client(1001, "TestClient", "TestToken");
        when(clientdata.getClientUsingToken("TestToken")).thenReturn(testClient);
        assertThat(clientService.validateToken("TestToken")).isEqualTo(testClient);
    }
	
	 @Test
	    void InvalidToken() throws SQLErrorExceptions {
	        when(clientdata.getClientUsingToken("Nonexistent Token")).thenReturn(null);
	        assertThat(clientService.validateToken("Nonexistent Token")).isNull();
	    }
	 
	 
}
