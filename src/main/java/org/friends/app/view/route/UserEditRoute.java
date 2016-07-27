package org.friends.app.view.route;

import java.util.Map;

import org.friends.app.ConfHelper;
import org.friends.app.model.User;
import org.friends.app.service.UserService;
import org.friends.app.view.Templates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.utils.StringUtils;

@Component
public class UserEditRoute extends AdminAuthRoute {

	public final static String PARAM_USER = ":user_id";

	@Autowired
	private UserService userService;

	@Override
	public ModelAndView doHandle(Request request, Response response) {

		String template = Templates.USER_EDIT;

		Map<String, Object> map = Routes.getMap(request);
		User user = getUser(request);

		if ("POST".equalsIgnoreCase(request.requestMethod())) {

			String idUser = request.queryParams("idUser");
			String email = request.queryParams("email");
			String mobile = request.queryParams("mobile");
			String placeNumber = request.queryParams("placeNumber");

			Integer idUserInt = StringUtils.isNotEmpty(idUser) ? Integer.valueOf(idUser) : null;
			Integer placeNumberInt = StringUtils.isNotEmpty(placeNumber) ? Integer.valueOf(placeNumber) : null;

			boolean result = userService.updateUser(idUserInt, email, mobile, placeNumberInt);

			if (result) {
				response.removeCookie(ConfHelper.COOKIE);
				user = userService.findUserByEmail(user.getEmailAMDM());
				if (user != null) {
					request.session().attribute("user", user);
				}
				map.put("user", user);
				map.put("title", "Modification données utilisateur");
				map.put("message", "Les données de l'utilisateur ont été modifiées avec succès.");
				map.put("ok", "ok");
				map.put("urlDest", "/protected/setting");
				map.put("libelleBtn", "Retour");
				template = Templates.MESSAGE_OK_KO;
			} else {
				map.put("user", user);
				map.put(Routes.KEY_ERROR, "Pas de modification des données : données identiques ou autre problème !");
			}

		} else {
			// Utilisateur à modifier
			String userId = request.params().get(PARAM_USER);

			if (StringUtils.isEmpty(userId)) {
				map.put("user", "N/C");
				map.put("message", "Utilisateur introuvable !");
			} else {
				map.put("userid", userId);
				map.put("user", user);
			}
		}

		return new ModelAndView(map, template);

	}
}
