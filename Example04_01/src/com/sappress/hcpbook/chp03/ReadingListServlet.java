package com.sappress.hcpbook.chp03;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This Servlet provides a simple, in-memory database for storing a user's
 * reading list. It's primitive implementation is for demonstrative purposes
 * only. We'll learn how to deal with real-world persistence in Chapter 5.
 */
@WebServlet("/ReadingListServlet")
public class ReadingListServlet extends HttpServlet
{
  // Constant Declarations:
  private static final long serialVersionUID = 1L;
  
//Create a logger instance:
  private static Logger logger = null;
		   
      
 @Override
 public void init() throws ServletException
 {
	 logger = LoggerFactory.getLogger(ReadingListServlet.class);
	 logger.debug("Servlet EnvReadServlet has been initialized.");
 }

  /**
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  @SuppressWarnings("unchecked")
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException
  {
    // Retrieve the current list from the user's session:
    List<String> readingList = null;
    
    HttpSession session = request.getSession();
    Object o = session.getAttribute("readingList");
    if (o == null)
    {
      readingList = new ArrayList<String>();
      session.setAttribute("readingList", readingList);
    }
    else
    {
      readingList = (List<String>) o;
    }
    
    // Add the selected item to the list:
    String book = request.getParameter("book");
    if (book != null)
      readingList.add(book);
    
    // Also keep track of the number of items in the list:
    session.setAttribute("itemCount", readingList.size());
    Map<String, String> envVarMap = System.getenv();
	  for (String key : envVarMap.keySet())
	  {
	    String msg = "Variable [" + key + "]: " + envVarMap.get(key); 
	    //response.getWriter().println(msg);
	    logger.info(msg);
	  }
    // Re-route the user back to the main reading list form:
    RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
    rd.forward(request, response);
  } // -- protected void doPost() -- //
  public void destroy()
	{
	  logger.debug("Servlet EnvReadServlet is being destroyed.");
	}

} // -- End of class ReadingListServlet -- //