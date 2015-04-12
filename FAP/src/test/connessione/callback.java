package test.connessione;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.scribe.model.OAuthRequest;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

import facebook4j.Account;
import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.ResponseList;
import facebook4j.auth.AccessToken;

/**
 * Servlet implementation class callback
 */
public class callback extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public callback() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		out.println("Ciao CallBack");
		
		Enumeration<String> e = request.getParameterNames();
		
		
		
		while (e.hasMoreElements())
		{
			String s = e.nextElement();
			String value = request.getParameter(s);
			out.println("\n Parametro " + s + " 	valore = " + value);
		}
		
		
		
		OAuthService service = (OAuthService) request.getSession().getAttribute("service");
		Verifier ver = new Verifier(request.getParameter("code"));
		
		Token accessToken = service.getAccessToken(null, ver);
		
		OAuthRequest req = new OAuthRequest(Verb.GET, "https://graph.facebook.com/me/photos");
		
		service.signRequest(accessToken, req);
		org.scribe.model.Response resp = req.send();
		
		out.println("\n\nRichiesta\n"+resp.getBody());
		out.close();	
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		Facebook facebook = new FacebookFactory().getInstance();
		facebook.setOAuthAppId("110050672488637", "5d20f8c4d58a77b2a0e25a3fc3bda5e7");
		facebook.setOAuthAccessToken(new AccessToken(accessToken.getToken(), null));
		
		ResponseList<Account> accounts;
		try {
			accounts = facebook.getAccounts();
			Account yourPageAccount = accounts.get(0);  // if index 0 is your page account.
			String pageAccessToken = yourPageAccount.getAccessToken();
		} catch (FacebookException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		
		
									
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		PrintWriter out = response.getWriter();
		out.println("Ciao CallBack");
		
		Enumeration<String> e = request.getParameterNames();
		
		while (e.hasMoreElements())
		{
			String s = e.nextElement();
			String value = request.getParameter(s);
			out.println("\n Parametro " + s + " 	valore = " + value);
		}
	}

}
