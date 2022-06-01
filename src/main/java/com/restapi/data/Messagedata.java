package com.restapi.data;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.restapi.entity.Client;
import com.restapi.entity.Message;
import com.restapi.entity.Request;
import com.restapi.exceptions.SQLErrorExceptions;
import com.restapi.rowmappers.MessageMapper;



@Component
public class Messagedata {
	Logger logger = LoggerFactory.getLogger(Messagedata.class);
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public int save(Request requestBody,Client client) throws SQLErrorExceptions  {
		int result = 0;
		String query = "insert into message_details(message,scheduled_at,destination_phone_number,client_id,created_at,pending_status,scheduled_status) values (?,?,?,?,?,?,?)";
		try {
			result = jdbcTemplate.update(query, requestBody.getMessage(), requestBody.getScheduledTime(),
					requestBody.getPhonenumber(), client.getClient_id(), LocalDateTime.now(), true,true);
			return result;
		} catch (Exception e) {
			throw new SQLErrorExceptions("error while doing sql operation");
		}

	}
	
	public List<Message> getAllMessagesInOneMinute() throws SQLErrorExceptions {

      String query = "select * from message_details where pending_status = true and scheduled_at < now()";

      List<Message> messages = Collections.emptyList();
      logger.info("polling messages at " + LocalDateTime.now());
      try {
          messages = jdbcTemplate.query(query,  new MessageMapper());
          return messages;
      } catch (Exception e) {
          logger.warn(e.getMessage());
          throw new SQLErrorExceptions("error while doing polling messages from DB");
      }
  }
	public int updateMessageStatus(Boolean pending_status,Boolean submited_status, String whatsAppMessageId,LocalDateTime submitted_at,Integer message_id){

		String query ="UPDATE message_details set pending_status = ?, submitted_status=?, submitted_at=?,whatsapp_api_message_id=? where message_id = ?";
		System.out.println("updating message status for messageId " + message_id);
		int result = 0;
		try
		{
			result = jdbcTemplate.update(query,pending_status,submited_status,submitted_at,whatsAppMessageId,message_id);
			return result;
		}catch (Exception e){
			return 0;
		}
}
}
