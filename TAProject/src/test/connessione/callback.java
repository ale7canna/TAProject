package test.connessione;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import twitter4j.AccountSettings;
import twitter4j.IDs;
import twitter4j.PagableResponseList;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;
import twitter4j.auth.RequestToken;

/**
 * Servlet implementation class callback
 */
public class callback extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private final String CONSUMER_KEY = "lcG8Dlc7n21QShe0PQPD1zhbV";
	private final String CONSUMER_SECRET = "BDiIjRmul86mXaGRPnw4utmU2rHnXPoQCHqbuKqmKnteMd0Kad";
    
       
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
		
		String pin = request.getParameter("oauth_verifier");
		
		RequestToken requestToken = (RequestToken) request.getSession().getAttribute("requestToken");
		Twitter twitter = (Twitter)request.getSession().getAttribute("twitter");
		
		
		/*
		ConfigurationBuilder builder = new ConfigurationBuilder();
		builder.setOAuthConsumerKey(CONSUMER_KEY);
		builder.setOAuthConsumerSecret(CONSUMER_SECRET);
		Configuration conf = builder.build();
		
		
		Twitter twitter = new TwitterFactory(conf).getInstance();
		*/
		
		
		
		
		try {
			
			twitter4j.auth.AccessToken accessToken = twitter.getOAuthAccessToken(requestToken, pin);
			twitter.setOAuthAccessToken(accessToken);
		} catch (TwitterException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		AccountSettings as;
		try {
			as = twitter.getAccountSettings();
			response.getWriter().println(as.getScreenName());
			
			ResponseList<Status> stati = twitter.getUserTimeline();
			
			for (Status s:stati){
				out.println("\n" + s.getText());
			}
			
			IDs ids = twitter.getFollowersIDs(100);
			
			//STAMPA FOLLOWERS
			
			
			out.println("------------------------FOLLOWERS\n");
			PagableResponseList<User> fol = twitter.getFollowersList(twitter.getId(), -1);
			for (int k = 0; k< fol.size(); k++)
				out.println("\n"+fol.get(k).getName());
			
			while (fol.hasNext()){
				out.println("\n" + String.valueOf(fol.hasNext()));
				
				fol = twitter.getFollowersList(twitter.getId(), fol.getNextCursor());
				
				for (int k = 0; k< fol.size(); k++)
					out.println("\n"+fol.get(k).getName());
				out.println("\n\n\n\n");
			}


				
			
			out.println("------------------------FOLLOWINGS\n");
			PagableResponseList<User> friends = twitter.getFriendsList(twitter.getId(), -1);
			for (int k = 0; k< friends.size(); k++)
				out.println("\n"+friends.get(k).getName());
			
			while (friends.hasNext()){
				out.println("\n" + String.valueOf(friends.hasNext()));
				friends= twitter.getFollowersList(twitter.getId(), friends.getNextCursor());
				
				for (int k = 0; k< friends.size(); k++)
					out.println("\n"+ friends.get(k).getName());
				out.println("\n\n\n\n");
			}
			
			
			
			
		} catch (TwitterException e1) {
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
