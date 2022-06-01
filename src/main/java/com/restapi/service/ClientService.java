package com.restapi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restapi.data.Clientdata;
import com.restapi.entity.Client;
import com.restapi.exceptions.SQLErrorExceptions;

@Service
public class ClientService {
	Logger logger = LoggerFactory.getLogger(ClientService.class);
	 @Autowired
	 Clientdata clientdata;
	 public Client validateToken(String token) throws SQLErrorExceptions {
	        System.out.println("in validate token service");
	        Client client = clientdata.getClientUsingToken(token);
	        return client;
	    }
}
