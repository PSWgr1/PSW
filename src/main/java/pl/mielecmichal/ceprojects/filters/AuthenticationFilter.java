package pl.mielecmichal.ceprojects.filters;

import lombok.Value;
import pl.mielecmichal.ceprojects.constants.Mapping;
import pl.mielecmichal.ceprojects.constants.SessionAttribiute;
import spark.Filter;
import spark.Request;
import spark.Response;
import spark.Spark;

import java.util.List;

@Value
public class AuthenticationFilter implements Filter {

    private final List<AuthenticationRule> ignored;

    @Override
    public void handle(Request request, Response response) throws Exception {
        boolean isIgnored = ignored.stream().anyMatch(rule -> rule.matches(request));
        if (isIgnored) {
            return;
        }

        boolean notAuthenticated = !request.session().attributes().contains(SessionAttribiute.AUTHENTICATED_USER.name());
        if (notAuthenticated) {
            response.redirect(Mapping.INTRO.getPath());
            Spark.halt();
        }
    }
}
