package controllers;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;

import models.BeanUser;
import mysql.DAO;

/**
 * Servlet implementation class ProfileController
 */
@WebServlet("/ProfileController")
public class ProfileController extends HttpServlet{
	private static final long serialVersionUID = 1L;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProfileController(){
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = null;
		String userProfileUsername = (String) request.getParameter("userProfileUsername");
		HttpSession session = request.getSession();
		BeanUser user = (BeanUser) session.getAttribute("user");
		
		if(userProfileUsername != null || user != null){
			System.out.println("loading user");
			BeanUser userProfile = null;
			if(userProfileUsername != null){
				userProfile = new BeanUser();
				if(!userProfile.loadFromDatabase(userProfileUsername)){
					response.getWriter().print("User "+userProfileUsername+" doesn't exist in the database");
					System.out.println("User "+userProfileUsername+" doesn't exist in the database");
				}
			}else{
				userProfile = user;
			}
			
			request.setAttribute("userProfile", userProfile);
			request.setAttribute("listTweets", userProfile.getUserTweets());
			System.out.println("Correctly loaded user profile ");
		}else{
			System.out.println("Username param is mandatory if you arent loged in");
		}
		
		
		if(user != null)
			request.setAttribute("user",user);
		dispatcher = request.getRequestDispatcher("ViewProfile.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		RequestDispatcher dispatcher = null;
		String userProfileUsername = (String) request.getParameter("profileDelete");
		HttpSession session = request.getSession();
		BeanUser user = (BeanUser) session.getAttribute("user");
		if(user != null && user.isAdmin()){
			user.deleteUser(userProfileUsername);
		}else{
			System.out.println("The user is not allowed to delete other users");
		}
		
		dispatcher = request.getRequestDispatcher("UserListController");
		dispatcher.forward(request, response);
	}

}
