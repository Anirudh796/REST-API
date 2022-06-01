package com.restapi.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restapi.data.Messagedata;
import com.restapi.entity.Client;
import com.restapi.entity.Message;
import com.restapi.entity.Request;
import com.restapi.exceptions.SQLErrorExceptions;

@Service
public class MessageService {
	
	@Autowired
	Messagedata msgdata;
	
	public MessageService(Messagedata msgdata) {
        this.msgdata = msgdata;
    }
	
	public int saveMessage(Request requestBody, Client client) throws SQLErrorExceptions {
        return msgdata.save(requestBody, client);
    }
	
	public int updateMessageStatus(Boolean pending_status, Boolean submited_status, String whatsAppMessageId, LocalDateTime submitted_at, Integer message_id) throws SQLErrorExceptions {

        return msgdata.updateMessageStatus(pending_status, submited_status, whatsAppMessageId, submitted_at, message_id);
    }
	public List<Message> pollMessagesFromDatabase() throws SQLErrorExceptions {
        return msgdata.getAllMessagesInOneMinute();
    }

}
