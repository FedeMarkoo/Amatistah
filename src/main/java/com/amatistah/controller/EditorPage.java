package com.amatistah.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.amatistah.servlet.supers.Request;
import com.amatistah.servlet.supers.Servlet;

/**
 * Servlet implementation class EditorPage
 */
@Controller
public class EditorPage extends Servlet implements ErrorController {
	private static String javaPath = "C:\\Amatista\\Workspace\\Amatista\\servlet\\actions\\";
	private static String jspPath = "C:\\Amatista\\Workspace\\Amatista\\WebContent\\";
	private static String templateJavaPath = "C:\\Amatista\\Workspace\\Amatista\\templates\\javaClassTemplate";

	/**
	 * 
	 */
	private static final long serialVersionUID = -1852840069540564755L;

	public EditorPage() {
		super("/views/editorpage.tista.jsp");
	}

	private void generateOrUpdateJavaFile(String title, String url, String xmlData) {
		String jspFile = "/views" + url + ".jsp";
		writeJavaClass(url, jspFile);
		writeJspClass(jspFile, title, xmlData);
	}

	private void writeJavaClass(String url, String jspFile) {
		try {
			String camelCase = makeUpperFirst(url);
			File f = new File(javaPath + camelCase + ".java");
			f.createNewFile();
			FileWriter fw = new FileWriter(f);
			fw.write(getJavaClassContent(camelCase, url, jspFile));
			fw.close();
		} catch (Exception e) {
		}
	}

	private void writeJspClass(String jspFile, String title, String xmlData) {
		try {
			File f = new File(jspPath + jspFile);
			f.createNewFile();
			FileWriter fw = new FileWriter(f);
			fw.write(getJspContent(title, xmlData));
			fw.close();
		} catch (Exception e) {
		}
	}

	private String getJspContent(String title, String xmlData) {
		return xmlData;
	}

	private String getJavaClassContent(String className, String url, String jspFile) throws FileNotFoundException {
		File file = new File(templateJavaPath);
		Scanner fr = new Scanner(file);
		String fileContent = "";
		while (fr.hasNextLine())
			fileContent += fr.nextLine() + "\r\n";
		fr.close();
		return fileContent.replace("{urlPage}", url).replace("{className}", className).replace("{jspPath}", jspFile);
	}

	private String makeUpperFirst(String url) {
		return url.substring(0, 1).toUpperCase() + url.substring(1);
	}

	@RequestMapping({
		//"/error", 
		"/editorPage"})
	public String process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Request request2 = new Request(request);
		Integer errorCode = (Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		if (errorCode==null || errorCode == HttpStatus.NOT_FOUND.value()) {
			if (request2.existParameter("isForEdit")) {
				String url = request.getParameter("url");
				String xmlData = getJspExistContent(url);
				request.getSession().setAttribute("xmlData", getBody(xmlData));
				request.getSession().setAttribute("title", getHeader(xmlData));
				request.getSession().setAttribute("url", "Amatista/" + url);
			} else if (request2.existParameter("isForSave")) {
				String name = request.getParameter("title");
				String url = request.getParameter("url");
				String xmlData = request.getParameter("xmlData");
				generateOrUpdateJavaFile(name, url, xmlData);

			} 
		}
		return "editorPage.tista";
	}

	private String getHeader(String xmlData) {
		Pattern c = Pattern.compile("<title>([^<]+)</title>");
		Matcher matcher = c.matcher(xmlData);
		if (matcher.find()) {
			return matcher.group(1);
		}
		return "";
	}

	private String getBody(String xmlData) {
		Pattern c = Pattern.compile("<body>(.*)</body>");
		Matcher matcher = c.matcher(xmlData);
		if (matcher.find()) {
			return matcher.group(1);
		}
		return "";
	}

	private String getJspExistContent(String url) throws FileNotFoundException {
		File file = new File(jspPath + "views\\" + url + ".jsp");
		Scanner fr = new Scanner(file);
		String xmlData = "";
		while (fr.hasNextLine())
			xmlData += fr.nextLine() + "\r\n";
		fr.close();
		return xmlData;
	}

	@Override
	public String getErrorPath() {
		return "/error";
	}

}
