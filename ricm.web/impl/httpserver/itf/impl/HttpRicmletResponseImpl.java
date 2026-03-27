package httpserver.itf.impl;

import httpserver.itf.HttpRequest;
import httpserver.itf.HttpRicmletResponse;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

public class HttpRicmletResponseImpl extends HttpResponseImpl implements HttpRicmletResponse {

    private Map<String, String> m_cookies = new HashMap<>();
    public HttpRicmletResponseImpl(HttpServer hs, HttpRequest req, PrintStream ps) {
        super(hs, req, ps);
    }

    @Override
    public void setCookie(String name, String value) {
    	m_cookies.put(name, value);
    }
    
    @Override
	public PrintStream beginBody() {
    	for (Map.Entry<String, String> cookie : m_cookies.entrySet()) {
			m_ps.println("Set-Cookie: " + cookie.getKey() + "=" + cookie.getValue());
		}
		m_ps.println(); 
		return m_ps;
    }
}