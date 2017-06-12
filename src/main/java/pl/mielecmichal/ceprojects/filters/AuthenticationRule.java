package pl.mielecmichal.ceprojects.filters;

import lombok.Value;
import org.eclipse.jetty.http.HttpMethod;
import pl.mielecmichal.ceprojects.constants.Mapping;
import spark.Request;

@Value
public class AuthenticationRule {

    private final HttpMethod method;
    private final Mapping mapping;

    public boolean matches(Request request) {
        if (!request.requestMethod().equalsIgnoreCase(method.asString())) {
            return false;
        }

        if (!request.pathInfo().equals(mapping.getPath())) {
            return false;
        }
        return true;
    }
}
