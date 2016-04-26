package com.mowitnow.ws.mower;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Cordinates {
	
	/**
	 * Construit les chemins des diverses tondeuses sous forme d'un objet JSON. 
	 * on calcule  ansi les positins de fin de chaque tondeuse.
	 * 
	 * @return 	L'objet Json à retourner encapsulé dans la réponse http.
	 * 
	 */
	public JSONObject BuildJsonMap()
	 {
		
		//On lit le fichier desciptif et on stoke son contenu dans une list
		List<String> commands = new ArrayList<String>();
		
		try 
		{
		 	BufferedReader br = new BufferedReader(new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream("mower.txt")));

			String Currligne;

			while ((Currligne = br.readLine()) != null)
			{
				commands.add(Currligne);
			}

		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		
		JSONObject MowerListCordinates = new JSONObject();
		
		int Nbcolonnes = Integer.parseInt(commands.get(0).split(" ")[0]);
		
		int NbLignes = Integer.parseInt(commands.get(0).split(" ")[1]);
		
		JSONArray MowerList = new JSONArray();	
		JSONArray MowerStop = new JSONArray();
		
		for(int j = 1;j<commands.size();j+=2)
		{
			
			
			int startx =  Integer.parseInt(commands.get(j).split(" ")[0]);
				
			int starty =  Integer.parseInt(commands.get(j).split(" ")[1]);
			
			String direction =  commands.get(j).split("")[4];
			
			String [] tabcommand = commands.get(j+1).toString().split("");		
			
			JSONObject jsonobj = new JSONObject();
				
			jsonobj.put("X", startx);
			
			jsonobj.put("Y", starty);
			
			jsonobj.put("Direction", direction);			
		
			MowerList.add(jsonobj);
			
			int [] postn = {startx,starty};
			
			for(int  i= 0;i<tabcommand.length;i++)
			{	
				JSONObject jsonobject = new JSONObject();
				
				direction =  nextMove(tabcommand[i],postn,Nbcolonnes,NbLignes,direction);			
				
				
				jsonobject.put("X", postn[0]);
				
				jsonobject.put("Y", postn[1]);
				
				jsonobject.put("Direction", direction);			
			
				MowerList.add(jsonobject);
				
				if(i==(tabcommand.length-1))
				{
					JSONObject stopjsonobject = new JSONObject();
					
					stopjsonobject.put("X", postn[0]);
					
					stopjsonobject.put("Y", postn[1]);
					
					stopjsonobject.put("Direction", direction);			
				
					MowerStop.add(stopjsonobject);
				}
			}
			
			
		}
			MowerListCordinates.put("FieldWidth",Nbcolonnes);
			MowerListCordinates.put("FieldHeight",NbLignes);
			MowerListCordinates.put("Mowers",MowerList);
			MowerListCordinates.put("EndPosition",MowerStop);
		
		return MowerListCordinates;
	}
	 
 
	/**
	 * Execute la commande fournit à l'entrée et change soit la direction
	 * ou les valeurs de position x ou y. Chaque cas depend de la direction
	 * initiale. 
	 *
	 * @param   command		La commande à executer(un movement ou changement de direction)
	 * @param	pos			Un tableau contenant les cordonées x et y de la position de la tondeuse
	 * @param	Nbcolonnes	La valeur de x du coin haut à droite (fournit dans le fichier)
	 * @param	NbLignes	La valeur de y du coin haut à droite (fournit dans le fichier)
	 * @param	direction	La direction actuelle de la tondeuse
	 * @return 				La direction de la tondeuse après traitement de commande.
	 * 
	 */
	public String nextMove(String command,int [] pos,int Nbcolonnes,int NbLignes,String direction)
	{
		
		switch(direction)
		{
			case "N":
				switch(command)
				{
					case "G":
						direction = "W";
						break;
					case "D":
						direction = "E";
						break;
						
					case "A":
						if(pos[1]<NbLignes){
							pos[1]++;
							}
						
						break;
				
				}
				break;
				
			case "S":
				switch(command)
				{
				case "G":
					direction = "E";
					break;
				case "D":
					direction = "W";
					break;
					
				case "A":
					if(pos[1]>0){
						pos[1]--;
					}
					break;
				
				}
				break;
				
			case "E":
				switch(command)
				{
				case "G":
					direction = "N";
					break;
				case "D":
					direction = "S";
					break;
					
				case "A":
					if(pos[0]<Nbcolonnes){
						pos[0]++;
					}
					break;
				
				}
				break;
				
			case "W":
				switch(command)
				{
				case "G":
					direction = "S";
					break;
				case "D":
					direction = "N";
					break;
					
				case "A":
					if(pos[0]>0){
						pos[0]--;
					}
					break;
				
				}
				break;
				
			}
		return direction;
	}

}
