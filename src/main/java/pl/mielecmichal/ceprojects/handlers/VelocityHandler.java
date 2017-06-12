package pl.mielecmichal.ceprojects.handlers;

import pl.mielecmichal.ceprojects.constants.SessionAttribiute;
import pl.mielecmichal.ceprojects.constants.Template;
import pl.mielecmichal.ceprojects.constants.ViewAttribiute;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;
import java.util.Map;

public abstract class VelocityHandler implements Route {

    @Override
    public Object handle(Request request, Response response) throws Exception {
        final Map<String, Object> model = new HashMap<>();

        Template template = handleInternal(request, response, model);

        if (template != null) {
            model.putIfAbsent(ViewAttribiute.PAGE_CONTENT.name(), template.getPath());
        }

        model.putIfAbsent("isAuthenticated", request.session().attributes().contains(SessionAttribiute.AUTHENTICATED_USER.name()));
        model.putIfAbsent("title", "Project Manager");

        return new VelocityTemplateEngine().render(
                new ModelAndView(model, "templates/main.vm")
        );
    }

    protected abstract Template handleInternal(Request request, Response response, Map<String, Object> model) throws Exception;
}
