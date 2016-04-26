package com.mowitnow.ws.mower;

import java.io.IOException;
import java.io.StringWriter;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.json.simple.JSONObject;

/**
 * @author felix Mwaka
 *
 */
@Path("/Mower")
public class Mower {
 
	
 @GET
 @Path("/cordinates")
 @Produces("application/json")
 public Response getCordinates() throws IOException {
	 
	 	Cordinates codinates = new Cordinates();
	 	
	 	JSONObject Mowercodinates = new JSONObject();	 	
		
		Mowercodinates = codinates.BuildJsonMap();
		
		StringWriter out = new StringWriter();
		
		Mowercodinates.writeJSONString(out);
	      
	      String jsonText = out.toString();
	      
	      //configuration d'entête de réponses http (Cross-origin resource sharing (CORS))
	      return Response
	              .status(200)
	              .header("Access-Control-Allow-Origin", "*")
	              .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
	              .header("Access-Control-Allow-Credentials", "true")
	              .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
	              .header("Access-Control-Max-Age", "1209600")
	              .entity(jsonText)
	              .build();
  
 }
}

