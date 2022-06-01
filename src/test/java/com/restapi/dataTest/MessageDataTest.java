package com.restapi.dataTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.restapi.data.Messagedata;
import com.restapi.entity.Client;
import com.restapi.entity.Request;
import com.restapi.exceptions.SQLErrorExceptions;

@SpringBootTest
public class MessageDataTest {
	@Autowired
    Messagedata msgdata;
	
	 @Test
	 void insertMessage() throws SQLErrorExceptions {
	        Client  testClient = new Client(1,"Testclient","TestclientToken");
	        Request request = new Request("9493894489","2022-05-31T13:00:00","Message for testing purpose");
	        int result = msgdata.save(request,testClient);
	        assertEquals(1,result);
	   }
	 
	 @Test
	   void updateMessage() throws SQLErrorExceptions {
	       int result =  msgdata.updateMessageStatus(false,true,"Test_whatsapp_Id", LocalDateTime.now(),4);
	       assertEquals(1,result);
	    }

}
