package servlet.supers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class Servlet extends HttpServlet {

	
	private static final long serialVersionUID = -6563352080432836472L;
	protected String path;

	/**
	 * 
	 */
	public Servlet(String path) {
		this.path = path;
	}
	
	public abstract void process(Request request, HttpServletResponse response) throws ServletException, IOException;
	

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}


	private void doProcess(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Request r = new Request(request);
		process(r, response);
		getServletContext().getRequestDispatcher(path).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//doGet(request, response);
	}

}

