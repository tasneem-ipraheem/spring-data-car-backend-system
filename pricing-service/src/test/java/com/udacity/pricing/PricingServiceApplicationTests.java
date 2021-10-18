package com.udacity.pricing;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URI;
import java.net.URISyntaxException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.web.client.RestTemplate;

/**
 * upgraded from junit-4 to junit-5
 * */

//@RunWith(SpringRunner.class)  junit-4

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class PricingServiceApplicationTests {

	@LocalServerPort
	private int port;

	@Autowired
	private MockMvc mvc;
	
	
	@Autowired
    private TestRestTemplate restTemplate;
	
	String url;
	int id=1;
	
	
	@Test
	public void contextLoads() {
//		Assertions.assertNotNull(testRestTemplate);
	}

	
    @BeforeEach
    public void setUp() {
        url = String.format("http://localhost:%d/", port);
    }

	@Test
	public void getAllPricesUsingTestRestTemplate() throws Exception{
		
		ResponseEntity<String> response = this.restTemplate.getForEntity(url+"/prices", String.class);
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
	}
	
	
	@Test
	public void getPriceById() throws URISyntaxException{

		 final String baseUrl = "http://localhost:"+port+"/prices/"+id;
	        
			ResponseEntity<String> response = this.restTemplate.getForEntity(baseUrl, String.class);
			Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
	        
	        Assertions.assertEquals(200, response.getStatusCodeValue());
	        Assertions.assertTrue(response.getBody().contains("\"currency\" : \"USD\""));
	        Assertions.assertTrue(response.getBody().contains("\"price\" : 1000.00"));
	    }
	
	
	/*
	 * 
	 //learning space 
	 
	@Test
	public void getPricesUsingMvc() throws Exception{
		mvc.perform(MockMvcRequestBuilders.get("/prices"))
		.andDo(MockMvcResultHandlers.print())
		.andExpect(status().isOk());

	}

	@Test
	public void getPriceById_Junit5_trial() throws URISyntaxException{

		RestTemplate restTemplate = new RestTemplate();
		 final String baseUrl = "http://localhost:"+port+"/prices/"+id;
	        URI uri = new URI(baseUrl);
	        

	        ResponseEntity<String> response = restTemplate.
	        		  getForEntity(uri , String.class);
	        
	        String jsonResponce = response.getBody();
//	        System.out.println("");
//	        System.out.println("#		Testing Json Responce 		#");
//	        System.out.println(jsonResponce);
	        
	        Assertions.assertEquals(200, response.getStatusCodeValue());
	        Assertions.assertTrue(jsonResponce.contains("\"currency\" : \"USD\""));
	        Assertions.assertTrue(jsonResponce.contains("\"price\" : 1000.00"));
	    }*/
	
	
	
	
	}


