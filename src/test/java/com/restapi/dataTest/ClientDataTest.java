package com.restapi.dataTest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

import com.restapi.data.Clientdata;
import com.restapi.entity.Client;
import com.restapi.exceptions.SQLErrorExceptions;

@SpringBootTest
public class ClientDataTest{
	@Autowired
    Clientdata clientdata;
	
	@Test
	void getValidClientUsingToken() throws SQLErrorExceptions{
		String authtoken = "e1lcbgd8fodepvxflnjcdroykywan8tk";
		Client OriginalClient = new Client(1 , "Anirudh", "e1lcbgd8fodepvxflnjcdroykywan8tk");
	    Client ResultClient = clientdata.getClientUsingToken(authtoken);
	    
	    assertThat(ResultClient).usingRecursiveComparison().isEqualTo(OriginalClient);
	}
	
	@Test
	void getInValidClientUsingToken() throws SQLErrorExceptions{
		 Client ResultClient = null;
		 try {
			    ResultClient = clientdata.getClientUsingToken("Nonexisting token");
	            System.out.println("ResultClient " + ResultClient);
	            assertThat(ResultClient).isEqualTo(null);
	        } catch (SQLErrorExceptions e) {
	            System.out.println(e.getMessage());
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	}

}
