package com.GrandCircus.MyControllers;

import java.text.DateFormat;


import java.util.Date;
import java.util.Locale;

import javax.annotation.Resource;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import java.util.*;

import java.sql.*;

import javax.sql.*;



/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	
	@RequestMapping(value="main", method = RequestMethod.GET)
	public String mainMethod(Model model)
	{   
		// adding data to our model 
		
		String[] ar = {"Hello Class1", "SO EASY", "Google IT INC." };
		model.addAttribute("message", ar);
		
		return "mainPage";
		
	}
	
	@RequestMapping(value="formPage", method = RequestMethod.GET)
	public String processForm(HttpServletRequest request,Model model)
	{
	
		String name = request.getParameter("name");
		
		String coffeeType = request.getParameter("coffeetype");
		
		model.addAttribute("name", name);
		
		model.addAttribute("coffeetype", coffeeType);
		
		return "success";
		
	}
	
	@RequestMapping(value="myForm", method = RequestMethod.GET)
	public String processForm()
	{
		return "myForm";
	}
	
	
	
	@RequestMapping(value="listCustomers", method= RequestMethod.GET)
	public String listAllCustomers(Model model)
	{    try
	   {
		Class.forName("com.mysql.jdbc.Driver");
		//Connection cnn = DriverManager.getConnection("jdbc:mysql://localhost:3306/northwind","root","admin"); 
		Session session = (new Configuration().configure().buildSessionFactory()).openSession();
		
		Connection cnn =session.connection();
		
		
		String selectCommand = "select customerid,companyname from customers";
		
		Statement selectStatement = cnn.createStatement();
		
		ResultSet rs =selectStatement.executeQuery(selectCommand); 
		
		ArrayList<Customer> lst = new ArrayList<Customer>();
		String output = "<table border=1>";
		
		while(rs.next()==true)
		{
			
			output+="<tr>";
			output+="<td>"+rs.getString(1)+"</td>";
			output+="<td>"+rs.getString(2)+"</td>";
			output+="</tr>";
					
			lst.add(new Customer(rs.getString(1), rs.getString(2)));
		}
		
		output+="</table>";
		
		
		model.addAttribute("ctable", output);
		
		model.addAttribute("listUsers", lst);
		
		return "customers";
	   }
	
	catch (Exception e)
	{
		// to do:
		// add this view: errorPage
		
		logger.info("Error is {}.", e.getLocalizedMessage());
		model.addAttribute("error", e.getMessage());
		return "errorPage";
		
	}
	}
	
	
//	@RequestMapping(value="changeTemp",method= RequestMethod.GET)
//	public String changeTemp(Model model, HttpServletRequest request, HttpServletResponse response)
//	{
//		Cookie[] clientCookies = request.getCookies();
//		String temp = "F";
//		if (clientCookies != null) {
//			temp = clientCookies[1].getValue();
//		} else {
//			Cookie tc = new Cookie("tempCookie", temp);
//			tc.setMaxAge(60 * 60 * 24 * 30);
//			response.addCookie(tc);
//		}
//		
//		if (request.getParameter("temp") != null) {
//			temp = request.getParameter("temp");
//		}
//
//		model.addAttribute("temp", temp);
//		
//		clientCookies[1].setValue(temp);
//		response.addCookie(clientCookies[1]);
//		
//		return "weather";
//	}
	
	@RequestMapping(value={"changeTemp","weather"}, method = RequestMethod.GET)
	public String getweatherPage(Model model, HttpServletRequest request, HttpServletResponse response)
	{
		String temp="F"; // 1 
		
		Cookie[] userCookies=request.getCookies();// bug fix 2  
		String userTemp=request.getParameter("temp");
		
		if (userTemp!=null && !userTemp.isEmpty()) // 2 
		{
			temp = userTemp; 
			
		}
		else // 3 
		{
		
			// userCookies= request.getCookies(); bug fix 1 (remove this line )
			if (userCookies!=null && userCookies.length>1)
			{
				temp = userCookies[1].getValue();
				
			}
				
		}
		
		
		
		model.addAttribute("temp", temp); 
		
		// send the data back to the client in a cookie
		if (userCookies!=null && userCookies.length>1)
		{  
			userCookies[1].setValue(temp);
			response.addCookie(userCookies[1]);
		}
		else
		{
		Cookie c = new Cookie("temp",temp);
		c.setMaxAge(60*60*24*30);// 30 days 
		response.addCookie(c);
					
		}
		
	
		
		
		return "weather";
	}
}
