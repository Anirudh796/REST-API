package com.restapi.timertask;

import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.restapi.entity.Message;
import com.restapi.exceptions.SQLErrorExceptions;
import com.restapi.service.MessageService;

@Component
public class CustomTimertask extends TimerTask{
	
	@Autowired
	MessageService messageService;
	
	

	@SuppressWarnings("deprecation")
	@Override
	public void run() {
		System.out.println("Timer Task is called every half minute");
		List<Message> messages = null;
		try {
			messages = messageService.pollMessagesFromDatabase();
		} catch (SQLErrorExceptions e) {
			e.printStackTrace();
		}
		if (messages.isEmpty()) {
            System.out.println("No messages scheduled");
            return;
        }
		System.out.println("The following message will be send on specified time");
		GsonBuilder builder = new GsonBuilder();
	    builder.setPrettyPrinting();
	    Gson gson = builder.create();
	    URL url = null;
	    HttpURLConnection connection = null;
	    for(Message msg : messages) {
	    	System.out.println("Running for message_id- " + msg.getMessage_id());
	    	try {
	    		url = new URL("https://api.gupshup.io/sm/api/v1/msg");
	    		connection = (HttpURLConnection) url.openConnection();
				connection.setRequestMethod("POST");
				connection.setUseCaches(false);
				connection.setDoOutput(true);
				connection.setDoInput(true);
				
				connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
				connection.setRequestProperty("apikey", "e1lcbgd8fodepvxflnjcdroykywan8tk");
				connection.setRequestProperty("Accept", "application/json");
				
				OutputStream os = connection.getOutputStream();
				
				HashMap<String, String> message = new HashMap<String, String>();
				message.put("type", "text");
				message.put("text", msg.getMessage());
				
				String jsonString = gson.toJson(message);
				JSONObject jsonObject = new JSONObject(jsonString);   
				
				Map<String, Object> body = new HashMap<>();
				body.put("channel", "whatsapp");
				body.put("source", "917834811114");
				body.put("destination", "91"+msg.getDestination_phone_number());
				body.put("message", jsonObject);
				body.put("src.name", "WhatsappAPIScheduler");
				
				//convert to bytes
				byte[] byteArray=null;
				StringBuilder postBody = new StringBuilder();
				for (Map.Entry<String, Object> entry : body.entrySet()) {
					if (postBody.length() != 0) {
						postBody.append('&');
					}
					postBody.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
					postBody.append('=');
					postBody.append(URLEncoder.encode(String.valueOf(entry.getValue()), "UTF-8"));
				}
				try {
				byteArray= postBody.toString().getBytes("UTF-8");
				}
				catch(UnsupportedEncodingException e){
					e.printStackTrace();
				}
				
				os.write(byteArray);
				System.out.println("outputstream here... " + os.toString());
				System.out.println(" response code here--> " + connection.getResponseCode());
				if (connection.getResponseCode() == HttpURLConnection.HTTP_ACCEPTED) {
					ObjectMapper objectMapper = new ObjectMapper();
					@SuppressWarnings("unchecked")
					Map<String, String> response = objectMapper.readValue(connection.getInputStream(), Map.class);
					System.out.println("MessageID is -->  " + response.get("messageId"));
					System.out.println(response.toString());
					int result = messageService.updateMessageStatus(false,true, response.get("messageId"),LocalDateTime.now(),msg.getMessage_id());
					if(result <1){
						System.out.println("Error occured while updating status....");
					}
					else 
						System.out.println("Status of meesages is updated--> "+ result);
					
				}
				else {
					
					int result = messageService.updateMessageStatus(false,false, null,null,msg.getMessage_id());
					System.out.println("Message sending failed for mesageID " + msg.getMessage_id());
				}
	    	}
	    	catch(Exception e) {
	    		System.out.println("exception occured during sending messages through gupshup API");
				e.printStackTrace();
	        }
	}

}
}

