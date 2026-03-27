package httpserver.itf.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import httpserver.itf.HttpRicmletRequest;
import httpserver.itf.HttpResponse;
import httpserver.itf.HttpSession;

public class HttpRicmletRequestImpl extends HttpRicmletRequest {
    private final static String SESSION_COOKIE_NAME = "session_id";

    private Map<String, String> m_args = new HashMap<>();
    private HttpSession m_session;

    public HttpRicmletRequestImpl(HttpServer hs, String method, String ressname, BufferedReader br) throws IOException {
        super(hs, method, ressname, br);

        // parse les arguments (exemple ?name=Bob&surname=Marley)
        int idx = ressname.indexOf('?');
        if (idx != -1) {
            m_ressname = ressname.substring(0, idx);
            for (String pair : ressname.substring(idx + 1).split("&")) {
                String[] kv = pair.split("=", 2);
                if (kv.length == 2)
                    m_args.put(kv[0], kv[1]);
            }
        }

        String sessionId = getCookie(SESSION_COOKIE_NAME);
        m_session = hs.getSession(sessionId);

        // on vide les headers qui restent
        while (!(br.readLine()).isEmpty());
    }

    @Override
    public String getArg(String name) {
        return m_args.get(name);
    }

    @Override
    public String getCookie(String name) { return null; }

    @Override
    public HttpSession getSession() { return m_session; }

    @Override
    public void process(HttpResponse resp) throws Exception {
        // /ricmlets/examples/HelloRicmlet -> examples.HelloRicmlet
        String clsname = m_ressname.substring("/ricmlets/".length()).replace('/', '.');
        try {
            m_hs.getInstance(clsname).doGet(this, (HttpRicmletResponseImpl) resp);
            ((HttpRicmletResponseImpl) resp).setCookie(SESSION_COOKIE_NAME, m_session.getId());
        } catch (ClassNotFoundException e) {
            resp.setReplyError(404, "Ricmlet not found");
            resp.beginBody();
        }
    }
}