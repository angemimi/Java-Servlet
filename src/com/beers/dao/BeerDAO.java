package com.beers.dao;

import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;
import org.bson.Document;

import com.beers.classes.Beer; //Appel de la classe Beer pour construire les objets rechercher dans la base de données
import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

public class BeerDAO {
	
	private MongoClient mongoC;
	private Gson gson;
	private MongoDatabase database;
	private MongoCollection<Document> beers;
	
	// Constructeur vide pour appeler les méthode contenue dans cette classe
	private BeerDAO(){
		gson = new Gson();
		mongoC = new MongoClient();
		database = mongoC.getDatabase("beers");
		beers = database.getCollection("beerDetail");
	}
	
	private static BeerDAO instance;
	
	public static BeerDAO getInstance(){
		if(null != instance){
			return instance;
		}
		instance = new BeerDAO();
		return instance;
	}
	
	
	public String findBeerList(){
		// Récupération des bière dans la base mongoDb		
		StringBuilder sb = new StringBuilder("[");
		for (Document beer : beers.find()) {
			
			Beer myBeer = gson.fromJson(beer.toJson(), Beer.class);
			sb.append(myBeer.toJSON()).append(",");
		}
		sb.deleteCharAt(sb.length()-1).append("]");
		
		return sb.toString();
	}
	
	public String find(String id){	
		
		Document beerDocument = beers.find(Filters.eq("id",id)).first();
		
		if(null == beerDocument){
			return "{}";
		}
		
		Beer beer = gson.fromJson(beerDocument.toJson(), Beer.class);
		
		beer.setDescription(StringUtils.stripAccents(beer.getDescription()));
		
		beer.setBrewery(StringUtils.stripAccents(beer.getBrewery()));
		
		return beer.toJSONDetail();
	}
	
	public Document generateBeerDocument(Beer beer){
		Document doc = new Document()
				.append("name", beer.getName())
				.append("id", beer.getId())
				.append("description", beer.getDescription())
				.append("alcohol", beer.getAlcohol())
				.append("label", beer.getLabel())
				.append("serving", beer.getServing())
				.append("availability", beer.getAvailability())
				.append("brewery", beer.getBrewery())
				.append("style", beer.getStyle())
				.append("img", beer.getimg());
		return doc;
	}
	//Add one beer in data base
	public void create(Beer beer){
		beers.insertOne(generateBeerDocument(beer));
	}
	//Edit one beer in data base
	public void update(Beer beer, String id){
		Document beerOld = beers.find(Filters.eq("id",id)).first();
		beers.replaceOne(beerOld, generateBeerDocument(beer));
	}
	//remove one beer in dataBase
	public void delete(String id){
		Document beerOld = beers.find(Filters.eq("id",id)).first();
		beers.deleteOne(beerOld);
	}
}
