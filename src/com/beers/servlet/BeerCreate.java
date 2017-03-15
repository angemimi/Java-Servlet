package com.beers.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.beers.classes.Beer;
import com.beers.dao.BeerDAO;

/**
 * Servlet implementation class BeerCreate
 */
@WebServlet({"/BeerCreate", "/create"})
public class BeerCreate extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BeerCreate() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setStatus(400);
		response.getWriter().append("Only POST method allowed on BeerCreate");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		boolean validParameter = true;
		StringBuilder validateMsg = new StringBuilder();
		
		String name = request.getParameter("name");
		String alcohol = request.getParameter("alcohol");
		String img = request.getParameter("img");
		String description = request.getParameter("description");
		
		String label = request.getParameter("label");
		String serving = request.getParameter("serving");
		String availability = request.getParameter("availability");
		String brewery = request.getParameter("brewery");
		String style = request.getParameter("style");
		
		
		if(!Beer.isValidNameParam(name)){
			validateMsg.append("Invalid parameter : name \n");
			validParameter = false;
		}
		if(!Beer.isValidAlcoholParam(alcohol)){
			validateMsg.append("Invalid parameter : alcohol \n");
			validParameter = false;
		}
		if(!Beer.isValidImgParam(img)){
			validateMsg.append("Invalid parameter : img -> "+img+"\n");
			validParameter = false;
		}
		if(!Beer.isValidDescriptionParam(description)){
			validateMsg.append("Invalid parameter : description \n");
			validParameter = false;
		}
		
		if(!Beer.isValidImgParam(label)){
			validateMsg.append("Invalid parameter : label -> "+label+" \n");
			validParameter = false;
		}
		
		if(validParameter){
			//Parse alcohol value in double
			double alc = Double.parseDouble(alcohol);
			//Instance of Beer
			Beer beer = new Beer();
			beer.setName(name);
			beer.setAlcohol(alc);
			beer.setDescription(description);
			beer.setimg(img);
			
			beer.setLabel(label);
			beer.setServing(serving);
			beer.setAvailability(availability);
			beer.setBrewery(brewery);
			beer.setStyle(style);
			
			BeerDAO.getInstance().create(beer);
		}
		else {
			response.setStatus(400);
			response.getWriter().append(validateMsg);
		}
		response.sendRedirect("#/beers");
	}
}
