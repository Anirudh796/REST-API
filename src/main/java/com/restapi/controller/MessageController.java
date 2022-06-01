package com.restapi.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restapi.entity.Client;
import com.restapi.entity.Request;
import com.restapi.entity.Response;
import com.restapi.exceptions.SQLErrorExceptions;
import com.restapi.service.MessageService;

@RestController
@RequestMapping("/schedule/")
public class MessageController {
	
	Logger logger = LoggerFactory.getLogger(MessageController.class);
	
	@Autowired
    MessageService msgservice;
	
	@GetMapping("/test")
    public Response testRoute() {
        logger.info("in tesing route");
        return new Response(201, "test successful..");
    }
	
	@PostMapping("/messages")
	public Response MessageHandler(@RequestBody @Validated Request requestBody, HttpServletRequest request) {
		Response response = null;
		 try {
	            Client client = (Client) request.getAttribute("client");
	            logger.info("client: " + client);
	            int result = msgservice.saveMessage(requestBody, client);
	            logger.info("result here..." + result);
	            response = new Response(200, "Message scheduled successfully");

	        } catch (SQLErrorExceptions e) {
	            logger.warn("sql exception occured");
	            response = new Response(e.getErrorCode(), e.getErrorMessage());
	        } catch (Exception e) {
	            logger.warn("exception here " + e.getMessage());
	            response = new Response(405, "something went wrong!!");
	        }
	        return response;

	    }
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	Response onMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		
		FieldError fieldError = e.getBindingResult().getFieldErrors().get(0);
		String errorMessage =  fieldError.getDefaultMessage();
		Response response = new Response(406,errorMessage);
		return response;
	}


}
