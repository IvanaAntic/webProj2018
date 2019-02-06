package dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Article;
import beans.Restaurant;
import beans.User;
import beans.Vechicle;

/**
 * Servlet implementation class Servlet
 */
@WebServlet("/Servlet")
public class Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public Servlet() {
        // TODO Auto-generated constructor stub
    	// HashMap<Long, Article> articles = ArticleDao.SeedData();
    	HashMap<Long,Restaurant> restaurants = RestaurantDao.seedData();
    	// ArticleDao.saveArticleMap(articles);
    	// System.out.println("all Users size"+restaurants.size());
    	 RestaurantDao.saveRestaurantMap(restaurants);
    	
    	//HashMap<Long,Vechicle> vechicle=VechicleDao.SeedData();
    	//VechicleDao.saveVechicleMap(vechicle);
    }
	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
		System.out.println("DAO STARTED UP! >> " + getServletContext().getRealPath(""));
		ArrayList<User> allUsers=UserDao.loadUsers();
		System.out.println("all Users size"+allUsers.size());
	}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
