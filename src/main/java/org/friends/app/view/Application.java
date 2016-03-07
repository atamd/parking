package org.friends.app.view;

import static org.friends.app.Configuration.getPort;
import static spark.Spark.after;
import static spark.Spark.before;
import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.staticFileLocation;

import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.SQLException;

import org.friends.app.Configuration;
import org.friends.app.model.User;

import spark.Filter;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.template.freemarker.FreeMarkerEngine;
import spark.utils.StringUtils;

import com.heroku.sdk.jdbc.DatabaseUrl;

public class Application {

	private static Application instance;

	public Application() {
		if (instance == null) instance = this;
	}

	public void start() {
		port(getPort());
		staticFileLocation("/public");

		/*
		 * Auto compress response 
		 */
		after((request, response) -> {
			String header = request.raw().getHeader("Accept-Encoding");
			if (header != null && header.contains("gzip"))
				response.header("Content-Encoding", "gzip");
		});
		
		/*
		 * When in production, controle that user first logged in
		 */
		if (Configuration.development()) {
			Filter checkLoggedIn = new Filter() {
				@Override
				public void handle(Request request, Response response) throws Exception {
					User authenticatedUser = getAuthenticatedUser(request);
					if(StringUtils.isEmpty(authenticatedUser)) {
						response.redirect(Routes.LOGIN);
					}
				}
			};
			before("/protected/*", checkLoggedIn); 
			before("/", checkLoggedIn);
		}

		get("/", (request, response) -> {
			return new ModelAndView(null, "index.ftl");
		}, new FreeMarkerEngine());

		/*
		 * User login 
		 */
		LoginRoute loginRoute = new LoginRoute();
		get(Routes.LOGIN, loginRoute, new FreeMarkerEngine());
		post(Routes.LOGIN, loginRoute, new FreeMarkerEngine());

		/*
		 * Déconnexion
		 */
		get(Routes.LOGOUT, (req, res) -> {
			removeAuthenticatedUser(req);
			res.redirect(Routes.LOGIN);
			return null;
		});

		/* 
		 * User register 
		 */
		RegisterRoute registerRoute = new RegisterRoute();
		get(Routes.REGISTER, registerRoute, new FreeMarkerEngine());
		post(Routes.REGISTER, registerRoute, new FreeMarkerEngine());

		/*
		 * Forgot password
		 */
		ForgottenPwdRoute forgottenPwdRoute = new ForgottenPwdRoute();
		get(Routes.PASSWORD_LOST, forgottenPwdRoute, new FreeMarkerEngine());
		post(Routes.PASSWORD_LOST, forgottenPwdRoute, new FreeMarkerEngine());


		/*
		 * Places booking 
		 */
		get(Routes.PLACE_SEARCH, new SearchRoute(), new FreeMarkerEngine());
		//post("/protected/search", new SearchRoute(), new FreeMarkerEngine());

		get("/protected/book/:placeId", (req, res) -> {
			return "Are you looking for " + req.params(":placeId");
		});

		/*
		 * Share a place
		 */
		SharePlace shareRoute = new SharePlace();
		get("/protected/sharePlace", shareRoute, new FreeMarkerEngine()); 
		post("/protected/sharePlace", shareRoute, new FreeMarkerEngine());//(req, res) -> "Vous libérez la place   " + req.queryParams("number") +" du " + req.queryParams("dateDebut") +" du " + req.queryParams("dateFin"));


		/* Doc */
		// TODO someone at some point ;)
//		get("/help", (req, res) -> "Nothing yet at help");

		
		/*
	    get("/db", (req, res) -> {
	      Connection connection = null;
	      Map<String, Object> attributes = new HashMap<>();
	      try {
	        connection = getConnection();

	        Statement stmt = connection.createStatement();
	        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS ticks (tick timestamp)");
	        stmt.executeUpdate("INSERT INTO ticks VALUES (now())");
	        ResultSet rs = stmt.executeQuery("SELECT tick FROM ticks");

	        ArrayList<String> output = new ArrayList<String>();
	        while (rs.next()) {
	          output.add( "Read from DB: " + rs.getTimestamp("tick"));
	        }

	        attributes.put("results", output);
	        return new ModelAndView(attributes, "db.ftl");
	      } catch (Exception e) {
	        attributes.put("message", "There was an error: " + e);
	        return new ModelAndView(attributes, "error.ftl");
	      } finally {
	        if (connection != null) try{connection.close();} catch(SQLException e){}
	      }
	    }, new FreeMarkerEngine());
		 */
		

		/* Intercept 404 */
		get("*", (request, response) -> {
			response.redirect(Routes.LOGIN);
			return "404";
		});
	}

	protected Connection getConnection() throws SQLException, URISyntaxException {
		return DatabaseUrl.extract().getConnection();
	}

	public static Application instance() {
		return instance;
	}
	
	private User getAuthenticatedUser(Request request) {
		return request.session().attribute("user");
	}

	private void removeAuthenticatedUser(Request request) {
		request.session().removeAttribute("user");
	}
}
