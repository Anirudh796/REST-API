package com.restapi.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.restapi.entity.Client;
import com.restapi.exceptions.SQLErrorExceptions;

@Repository
public class Clientdata {
	
	Logger logger = LoggerFactory.getLogger(Clientdata.class);
	
	 @Autowired
	 JdbcTemplate jdbcTemplate;
	 public Client getClientUsingToken(String token) throws SQLErrorExceptions {
	        String query = "select * from client_details where auth_token= ?";
	        Client client = null;
	        try {
	            client = jdbcTemplate.queryForObject(query, new BeanPropertyRowMapper<Client>(Client.class), token);
	            System.out.println("query--> " + query);
	            System.out.println("query result--> " + client.toString());
	            return client;
	        } catch (Exception e) {
	            logger.warn(e.getMessage());
	            return null;
	        }

	    }

}
