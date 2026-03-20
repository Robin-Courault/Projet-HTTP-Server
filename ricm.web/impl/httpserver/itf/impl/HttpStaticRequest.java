package httpserver.itf.impl;

import java.io.*;


import httpserver.itf.HttpRequest;
import httpserver.itf.HttpResponse;

/*
 * This class allows to build an object representing an HTTP static request
 */
public class HttpStaticRequest extends HttpRequest {
	static final String DEFAULT_FILE = "index.html";
	
	public HttpStaticRequest(HttpServer hs, String method, String ressname) throws IOException {
		super(hs, method, ressname);
	}
	
	public void process(HttpResponse resp) throws Exception {
		switch (getMethod()) {
			case "GET":
				File loadfile = new File(m_hs.getFolder().getPath()+getRessname());
				if (loadfile.exists()) {
					if (!loadfile.isFile()) {
						// on prend l'index.html du dossier
						if (loadfile.getPath().endsWith(File.separator)) {
							loadfile = new File(loadfile.getPath()+DEFAULT_FILE);
						} else {
							loadfile = new File(loadfile.getPath()+File.separator+DEFAULT_FILE);
						}

						// si l'index.html du dossier n'existe pas, on prend celui à la racine
						if (!loadfile.exists()) {
							loadfile = new File(m_hs.getFolder().getPath()+File.separator+DEFAULT_FILE);
						}
					}

					if (loadfile.canRead()) {
						resp.setReplyOk();
						resp.setContentLength((int) loadfile.length());
						resp.setContentType(getContentType(loadfile.getName()));

						sendRespBody(resp, loadfile);
					} else {
						resp.setReplyError(401, "Unauthorized");
					}
				} else {
					resp.setReplyError(404, "Not Found");
				}
				break;
			default:
				resp.setReplyError(405, "Method Not Allowed");
		}
	}

	private void sendRespBody(HttpResponse resp, File loadfile) throws IOException {
		try {
			PrintStream ps = resp.beginBody();
			FileInputStream fis = new FileInputStream(loadfile);
			byte[] buffer = new byte[32];
			int nBytesReaded;
			while (true) {
				nBytesReaded = fis.read(buffer);
				if (nBytesReaded > 0) {
					ps.write(buffer, 0, nBytesReaded);
				} else {
					break;
				}
			}
		} catch (Exception e) {
			resp.setReplyError(500, "Server Internal Error");
			System.out.println(e.getMessage());
		}
	}
}
