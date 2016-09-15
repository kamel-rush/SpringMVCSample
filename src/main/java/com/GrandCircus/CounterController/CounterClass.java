package com.GrandCircus.CounterController;

import java.sql.Connection;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;



@Controller
public class CounterClass {
	@RequestMapping(value="cus", method = RequestMethod.GET)
	public ModelAndView tsthiber(Model model)
	{
		Session session = (new Configuration().configure().buildSessionFactory()).openSession();
		
		session.beginTransaction();

		Criteria criteria = session.createCriteria(Customer.class);

		List<Customer> ls = (List<Customer>) criteria.list();
		return new ModelAndView("welcome4", "cList", ls);
		
		
	}
	
	@RequestMapping(value="counterpage", method = RequestMethod.GET)
	public String incrementCounter(HttpSession session, Model model, ServletContext Application)
	{
//		Object ob =session.getAttribute("counter");
//		
//		if (ob ==null)// very first visit to the page
//		{
//			session.setAttribute("counter",1);
//		}
//		else
//		{
//			Integer counter  = (Integer)ob; 
//			counter++;
//			session.setAttribute("counter", counter);
//		}
//		model.addAttribute("counter", session.getAttribute("counter"));
//		return "counterpage";
		
		
		Object ob =Application.getAttribute("counter");
		
		if (ob ==null)// very first visit to the page
		{
			Application.setAttribute("counter",1);
		}
		else
		{
			Integer counter  = (Integer)ob; 
			counter++;
			Application.setAttribute("counter", counter);
		}
		model.addAttribute("counter", Application.getAttribute("counter"));
		return "counterpage";
		
		
	}
	
	
}
