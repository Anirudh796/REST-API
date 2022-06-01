package com.restapi.serviceTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.restapi.data.Messagedata;
import com.restapi.entity.Client;
import com.restapi.entity.Message;
import com.restapi.entity.Request;
import com.restapi.exceptions.SQLErrorExceptions;
import com.restapi.service.MessageService;

@SpringBootTest
public class MessageServiceTest {
     
	@MockBean
    Messagedata msgdata;

    @Autowired
    MessageService msgService;
    
    @Test
    void saveMessage() throws SQLErrorExceptions {
        Client testClient = new Client(1, "anirudh", "jnkjnkjnk");
        Request request = new Request("9493894489", "2022-05-31T16:00:00", "Testclient Message");
        when(msgdata.save(request, testClient)).thenReturn(1);
        int result = msgService.saveMessage(request, testClient);
        assertThat(result).isEqualTo(1);

    }
    
    @Test
    void updateMessageStatus() throws SQLErrorExceptions {
        when(msgdata.updateMessageStatus(any(), any(), any(), any(), any())).thenReturn(1);
        int result = msgService.updateMessageStatus(any(), any(), any(), any(), any());
        assertEquals(1, result);
    }
    
    @Test
    void pollMessagesFromDatabase() throws SQLErrorExceptions {
        List<Message> messages = Collections.emptyList();
        when(msgdata.getAllPendingMessages()).thenReturn(messages);
        List<Message> resultList = msgService.pollMessagesFromDatabase();
        assertEquals(messages.size(), resultList.size());
    }
    
    @Test
    void saveMessageAsNull() {
        String expectedmsg = "sql error while inserting message";
        String resultmsg = "";

      
        try {
            when(msgdata.save(any(), any())).thenThrow(new SQLErrorExceptions("sql error while inserting message"));
            int result = msgService.saveMessage(null, null);
            assertEquals(1, result);
        } catch (SQLErrorExceptions e) {
        	resultmsg = e.getMessage();

        }
        assertThat(resultmsg).isEqualTo(expectedmsg);

    }

    
}
