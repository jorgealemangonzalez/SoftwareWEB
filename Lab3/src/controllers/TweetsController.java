package controllers;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;

import models.BeanTweet;
import models.BeanUser;
import mysql.DAO;

/**
 * Servlet implementation class UserInformationController
 */
@WebServlet("/TweetsController")

public class TweetsController extends HttpServlet{
	private static final long serialVersionUID = 1L;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TweetsController(){
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
			HttpSession session = request.getSession();
			RequestDispatcher dispatcher = null;
			String action = (String)request.getParameter("action");
			int status = 200; 
			String printResponse = "";
			BeanUser user = new BeanUser();
			user = (BeanUser) session.getAttribute("user");
			dispatcher = request.getRequestDispatcher("ViewTweetList.jsp");
			List<BeanTweet> tmp = new ArrayList<BeanTweet>();
			if(user != null ){	
				if(action.equals("getUserTweets")){
					System.out.println("get User tweets");
					tmp = user.loadUserTweetsFromDB();
				}else if(action.equals("getAllTweets")){
					System.out.println("get All tweets");
					tmp = BeanTweet.loadAllTweetsFromDB();
				}else if(action.equals("getMyFollowers")){
					System.out.println("get Followers Tweets");
					tmp = user.loadFollowersTweets();
				}else{
					System.out.println("need to specify action + - " + action);
					status = 401;
					dispatcher = request.getRequestDispatcher("ViewLoginForm.jsp");
				}
			}else{
				System.out.println("get All tweets not loged");
				tmp = BeanTweet.loadAllTweetsFromDB();
				user = new BeanUser();
				dispatcher = request.getRequestDispatcher("ViewTweetList.jsp");
			}
			if(printResponse != "")
				response.getWriter().print(printResponse);
			request.setAttribute("user", user);
			request.setAttribute("action", action);
			request.setAttribute("listTweets", tmp);
			request.setAttribute("lastAction", action);
			response.setStatus(status);
			dispatcher.forward(request, response);
			

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		RequestDispatcher dispatcher = null;
		String action = (String)request.getParameter("action");
		BeanUser user = new BeanUser();
		int status = 200; 
		user = (BeanUser) session.getAttribute("user");
		if(user.getUsername() != null){
			if(action.equals("commentTweet") && request.getParameter("id") != null && request.getParameter("comment") != null){
				user.commentTweet(Integer.parseInt(request.getParameter("id")),request.getParameter("comment"));
			}else if(action.equals("unlikeTweet") && request.getParameter("id") != null){
				user.unlikeTweet(Integer.parseInt(request.getParameter("id")));
			}else if(action.equals("likeTweet") && request.getParameter("id") != null){
				user.likeTweet(Integer.parseInt(request.getParameter("id")));
			}else if(action.equals("unlikeComment") && request.getParameter("id") != null){
				user.unlikeComment(Integer.parseInt(request.getParameter("id")));
			}else if(action.equals("likeComment") && request.getParameter("id") != null){
				user.likeComment(Integer.parseInt(request.getParameter("id")));
			}else if(action.equals("retweetTweet") && request.getParameter("id") != null){
				user.retweetTweet(Integer.parseInt(request.getParameter("id")));

			}else if(action.equals("publishTweet")){
				System.out.println("publish TweeT");
				if(!user.publishTweet(request.getParameter("tweet_text"))){
					status = 401;
				}
				dispatcher = request.getRequestDispatcher("ContentController");
			}else if(action.equals("editTweet")){
				System.out.println("edit Tweet");
				BeanTweet tmp = new BeanTweet();
				tmp.setId((int) Integer.parseInt(request.getParameter("id"))); 
				tmp.setTweet_text((String)request.getParameter("input"));
				if(tmp.editTweet()){
					System.out.println("tweet edited");
				}else{
					System.out.println("An error has ocurred updating tweet");
				}
			}else if(action.equals("deleteTweet")){
				System.out.println("deleting tweet");
				BeanTweet tmp = new BeanTweet();
				tmp.setId((int) Integer.parseInt(request.getParameter("id"))); 
				if(tmp.removeTweet()){
					System.out.println("tweet deleted!");
				}else{
					System.out.println("error deleting tweet");
				}
			}else{
				status = 401;
				dispatcher = request.getRequestDispatcher("LoginController");
			}
		}else{
			status = 401;
			dispatcher = request.getRequestDispatcher("LoginController");
		}
		request.setAttribute("lastAction", action);
		request.setAttribute("user", user);

	}

}