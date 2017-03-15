package com.beers.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.beers.dao.BeerDAO;

/**
 * Servlet implementation class beerServlet
 */
@WebServlet({ "/beerServlet", "/beer" })
public class beerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public beerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String id = request.getParameter("id");
		
		if (StringUtils.isBlank(id)) {
			response.setStatus(404);
			response.getWriter().append("{\"error\": { \"status\": 404}}");	
			return;
		}
		
		BeerDAO beerDao = null;
		
		BeerDAO beerDAO = beerDao.getInstance();
		
		String beer = beerDAO.find(id);
		
		response.getWriter().append(beer);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
