package pl.mielecmichal.ceprojects.handlers;

import lombok.AllArgsConstructor;
import pl.mielecmichal.ceprojects.constants.Template;
import pl.mielecmichal.ceprojects.domain.commands.Command;
import pl.mielecmichal.ceprojects.domain.commands.Command.CommandResult;
import pl.mielecmichal.ceprojects.domain.commands.ValidatingCommand;
import pl.mielecmichal.ceprojects.domain.users.User;
import pl.mielecmichal.ceprojects.domain.users.UsersRepository;
import spark.Request;
import spark.Response;

import java.util.Arrays;
import java.util.Map;

@AllArgsConstructor
public class RegistrationHandler extends VelocityHandler {

    private final UsersRepository usersRepository;

    @Override
    public Template handleInternal(Request request, Response response, Map<String, Object> model) throws Exception {

        User user = User.builder()
                .login(request.queryParamOrDefault("login", ""))
                .name(request.queryParamOrDefault("name", ""))
                .surname(request.queryParamOrDefault("surname", ""))
                .email(request.queryParamOrDefault("email", ""))
                .password(request.queryParamOrDefault("password", ""))
                .build();

        ValidatingCommand<User> command = Command.registerUserCommand(usersRepository);
        CommandResult result = command.execute(user);
        if (result == CommandResult.SUCCESS) {
            model.put("successMessages", Arrays.asList("New user registered. Please log in."));
            return Template.INTRO;
        }

        if (result == CommandResult.VALIDATION_ERROR) {
            model.put("errorMessages", command.getMessages());
            return Template.INTRO;
        }
        return Template.INTRO;
    }
}
