package pl.mielecmichal.ceprojects.handlers;

import lombok.AllArgsConstructor;
import pl.mielecmichal.ceprojects.constants.Mapping;
import pl.mielecmichal.ceprojects.constants.SessionAttribiute;
import pl.mielecmichal.ceprojects.constants.Template;
import pl.mielecmichal.ceprojects.domain.commands.Command;
import pl.mielecmichal.ceprojects.domain.commands.Command.CommandResult;
import pl.mielecmichal.ceprojects.domain.commands.ValidatingCommand;
import pl.mielecmichal.ceprojects.domain.users.CurrentUserRepository;
import pl.mielecmichal.ceprojects.domain.users.User;
import pl.mielecmichal.ceprojects.domain.users.UsersRepository;
import spark.Request;
import spark.Response;

import java.util.Map;

@AllArgsConstructor
public class LoginHandler extends VelocityHandler {

    private final UsersRepository usersRepository;

    @Override
    public Template handleInternal(Request request, Response response, Map<String, Object> model) throws Exception {

        User user = User.builder()
                .login(request.queryParamOrDefault("login", ""))
                .password(request.queryParamOrDefault("password", ""))
                .build();

        ValidatingCommand<User> loginUserCommand = Command.loginUserCommand(usersRepository);
        CommandResult result = loginUserCommand.execute(user);
        if (result == CommandResult.SUCCESS) {
            CurrentUserRepository currentUserRepository = new CurrentUserRepository(request.session());
            currentUserRepository.setCurrentUser(usersRepository.find(user.getLogin()));
            request.session().attribute(SessionAttribiute.AUTHENTICATED_USER.name(), true);
            response.redirect(Mapping.PROJECTS.getPath());
            return null;
        }

        if (result == CommandResult.VALIDATION_ERROR) {
            model.put("errorMessages", loginUserCommand.getMessages());
        }

        return Template.INTRO;

    }
}
