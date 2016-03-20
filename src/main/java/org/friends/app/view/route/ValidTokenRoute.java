package org.friends.app.view.route;

import java.util.HashMap;
import java.util.Map;

import org.friends.app.service.impl.UserServiceBean;
import org.friends.app.view.Templates;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;
import spark.utils.StringUtils;

public class ValidTokenRoute implements TemplateViewRoute {

	UserServiceBean service = new UserServiceBean();
	
	@Override
	public ModelAndView handle(Request request, Response response) throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put("title", "Validation de l'adresse email");
		
		String token = request.queryParams(Routes.PARAM_TOKEN_VALUE);
		String message = "Aucun code de validation fournit.";
		boolean success = false;

		if (!StringUtils.isEmpty(token)) {
			success = service.activate(token);
			message = success ? "Votre adresse email vient d'être validée, vous pouvez maintenant vous connecter."
					: "Echec lors de la validation de l'adresse email. Vérifiez le lien fournit et recommencez."; 
		}

		map.put("message", message);
		if (success)
			map.put("ok", "ok");

		return new ModelAndView(map, Templates.MESSAGE_OK_KO);
	}

}