package examples;

import java.io.IOException;
import java.io.PrintStream;

import httpserver.itf.HttpRicmletRequest;
import httpserver.itf.HttpRicmletResponse;

public class MyFirstCookieRicmlet implements httpserver.itf.HttpRicmlet {
	@Override
	public void doGet(HttpRicmletRequest req,  HttpRicmletResponse resp) throws IOException {
		resp.setReplyOk();
		resp.setContentType("text/html");
		String cookieName = "MyFirstCookie2";
		String cookieValue = req.getCookie(cookieName);
		//si le cookie existe, on l'incrémente, sinon on le crée et on le met à 0
		if(cookieValue != null) {
			resp.setCookie(cookieName, String.valueOf(Integer.parseInt(cookieValue) + 1));
		} else {
			resp.setCookie(cookieName, "0");
		}
		PrintStream ps = resp.beginBody();
		ps.println("<HTML><HEAD><TITLE> My First Cookie Ricmlet </TITLE></HEAD>"); 
		//affichage de l'action effectuée
		if(cookieValue != null) {
			ps.print("Cookie value: " + cookieValue);
		} else {
			ps.print("The cookie MyFirstCookie is not set, I set it to 0 !");
		}
		ps.println("</BODY></HTML>");
		ps.println();
	}
}
