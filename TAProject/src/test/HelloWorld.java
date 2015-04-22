package test;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Servlet implementation class HelloWorld
 */

public class HelloWorld extends HttpServlet {
	private static final long serialVersionUID = 10868756L;

	private final String CONSUMER_KEY = "lcG8Dlc7n21QShe0PQPD1zhbV";
	private final String CONSUMER_SECRET = "BDiIjRmul86mXaGRPnw4utmU2rHnXPoQCHqbuKqmKnteMd0Kad";
    /**
     * Default constructor. 
     */
    public HelloWorld() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();
		
		out.println(request.getRequestURL());
		out.println("Hello");
		
		
		/*
		OAuthService service = new ServiceBuilder()
		.provider(TwitterApi.class)
		.apiKey("lcG8Dlc7n21QShe0PQPD1zhbV")
		.apiSecret("BDiIjRmul86mXaGRPnw4utmU2rHnXPoQCHqbuKqmKnteMd0Kad")
		.callback("http://localhost/TAProject/callback")
		.build();
		
		Token reqToken = service.getRequestToken();
		
		request.getSession().setAttribute("service", service);
		*/
		
		ConfigurationBuilder builder = new ConfigurationBuilder();
		builder.setOAuthConsumerKey(CONSUMER_KEY);
		builder.setOAuthConsumerSecret(CONSUMER_SECRET);
		Configuration conf = builder.build();
		
		TwitterFactory fact = new TwitterFactory(conf);
		Twitter twit = fact.getInstance();
		//twit.setOAuthConsumer("lcG8Dlc7n21QShe0PQPD1zhbV", "BDiIjRmul86mXaGRPnw4utmU2rHnXPoQCHqbuKqmKnteMd0Kad");
		RequestToken requestToken = null;
		AccessToken accessToken = null;
		String authUrl = "";
		try {
//			String callbackURL = request.getRequestURI();
			String callbackURL = "http://localhost/TAProject/callback";
			requestToken = twit.getOAuthRequestToken(callbackURL);
			authUrl = requestToken.getAuthenticationURL();
			
			
			
			
			//out.println("\nURL = " + authUrl);
			
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		request.getSession().setAttribute("requestToken", requestToken);
		request.getSession().setAttribute("twitter", twit);
		response.sendRedirect(authUrl);

				
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
