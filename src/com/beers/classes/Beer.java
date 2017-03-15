package com.beers.classes;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.UrlValidator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

public class Beer {
	//Attribut générale de la bière
	@Expose String id;
	@Expose String name;
	@Expose String description;
	@Expose String img;
	@Expose double alcohol;
	
	//Concerne les detail de la biere
	String label;
	String serving;
	String availability;
	String brewery;
	String style;
	
	public static Gson gson = new Gson();
	
	//Nombre max de caractère pour certains attributs
	public static final double MAX_ALCOHOL = 20;
	public static final int MAX_NAME_LENGTH = 150;
	public static final int MAX_DESCRIPTION_LENGTH = 1500;
	public static final int MAX_PARAM_LENGTH = 300;
	
	private static String[] schemes = { "http", "https", "data:image/jpeg;" };
	private static UrlValidator urlvalidator = new UrlValidator(schemes);
	
	
	// Méthodes de controles des attribut de l'objet
	public static boolean isValidAlcoholParam(String param){
		double val;
		try {
			val = Double.parseDouble(param);
		} catch (Exception ex) {
			return false;
		}
		if (val < 0 || val > MAX_ALCOHOL) {
			return false;
		}
		return true;
	}
	
	public static boolean isValidNameParam(String param){
		if((StringUtils.isBlank(param)) || (param.length() > MAX_NAME_LENGTH)){
			return false;
		}
		
		
		return true;
	}
	
	public static boolean isValidImgParam(String param){
		
		if((StringUtils.isBlank(param)) || (!urlvalidator.isValid(param))){
			if(!param.endsWith(".jpg") && !param.endsWith(".png") && !param.endsWith(".gif")){
				return false;
			}
			return false;
		}
		return true;
	}
	
	public static boolean isValidGenericParam(String param){
		if(StringUtils.isBlank(param)){
			return false;
		}
		if(param.length() > MAX_PARAM_LENGTH){
			return false;
		}
		return true;
	}
	
	public static boolean isValidDescriptionParam(String param){
		if(StringUtils.isBlank(param)){
			return false;
		}
		if(param.length() > MAX_DESCRIPTION_LENGTH){
			return false;
		}
		return true;
	}
	
	
	//Constructeur pour construire une bière avec juste les informations contenue dans la collection beer de la bdd
	public Beer(String id, String n, String d, String i, double a){
		this.id = id;
		this.name = n;
		this.description = d;
		this.img = i;
		this.alcohol = a;
	}
	
	//Constructeur pour créer une bière avec les informations contenue dans la collection beerDetail de la bdd
	public Beer(
			String i, String name, String d, String img, double a, String label, String serving, String availability, 
			String brewery, String style
		){
		this.id = i;
		this.name = name;
		this.description = d;
		this.alcohol = a;
		this.img = img;
		this.label = label;
		this.serving = serving;
		this.availability = availability;
		this.brewery = brewery;
		this.style = style;
	}
	public Beer(){}
	

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
		this.id = StringUtils.capitalize(StringUtils.remove(StringUtils.stripAccents(name), " "));
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getimg() {
		return img;
	}
	public void setimg(String img) {
		this.img = img;
	}
	public double getAlcohol() {
		return alcohol;
	}
	public void setAlcohol(double alcohol) {
		this.alcohol = alcohol;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getServing() {
		return serving;
	}
	public void setServing(String serving) {
		this.serving = serving;
	}
	public String getAvailability() {
		return availability;
	}
	public void setAvailability(String availability) {
		this.availability = availability;
	}
	public String getBrewery() {
		return brewery;
	}
	public void setBrewery(String brewery) {
		this.brewery = brewery;
	}
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	
	public String toJSONDetail(){
		return gson.toJson(this);
	}
	
	public static Beer fromJson(String json){
		return gson.fromJson(json, Beer.class);
	}
	
	public String toJSON(){
		final GsonBuilder builder = new GsonBuilder();
		builder.excludeFieldsWithoutExposeAnnotation();
		final Gson gsonShort = builder.create();
		
		return gsonShort.toJson(this);
	}


}
