package pl.mielecmichal.ceprojects.handlers;

import pl.mielecmichal.ceprojects.constants.Template;
import spark.Request;
import spark.Response;

import java.util.Map;

public class IntroHandler extends VelocityHandler {

    @Override
    public Template handleInternal(Request request, Response response, Map<String, Object> model) throws Exception {
        return Template.INTRO;
    }

}
