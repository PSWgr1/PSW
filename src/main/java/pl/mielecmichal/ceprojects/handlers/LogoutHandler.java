package pl.mielecmichal.ceprojects.handlers;

import pl.mielecmichal.ceprojects.constants.Mapping;
import pl.mielecmichal.ceprojects.constants.SessionAttribiute;
import pl.mielecmichal.ceprojects.constants.Template;
import spark.Request;
import spark.Response;

import java.util.Map;

public class LogoutHandler extends VelocityHandler {
    @Override
    protected Template handleInternal(Request request, Response response, Map<String, Object> model) throws Exception {
        request.session().removeAttribute(SessionAttribiute.AUTHENTICATED_USER.name());
        response.redirect(Mapping.INTRO.getPath());
        return null;
    }
}
