package httpserver.itf.impl;

import httpserver.itf.HttpRequest;
import httpserver.itf.HttpRicmletResponse;

import java.io.PrintStream;

public class HttpRicmletResponseImpl extends HttpResponseImpl implements HttpRicmletResponse {

    public HttpRicmletResponseImpl(HttpServer hs, HttpRequest req, PrintStream ps) {
        super(hs, req, ps);
    }

    @Override
    public void setCookie(String name, String value) {
   	 	m_ps.println("Set-Cookie: " + name + "=" + value); // on envoie le cookie dans le header de la réponse avec le protocole HTTP
    }
}