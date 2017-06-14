package pl.mielecmichal.ceprojects.handlers;

import lombok.AllArgsConstructor;
import pl.mielecmichal.ceprojects.constants.Mapping;
import pl.mielecmichal.ceprojects.constants.Template;
import pl.mielecmichal.ceprojects.domain.commands.Command;
import pl.mielecmichal.ceprojects.domain.commands.Command.CommandResult;
import pl.mielecmichal.ceprojects.domain.commands.ValidatingCommand;
import pl.mielecmichal.ceprojects.domain.projects.ProjectTask;
import pl.mielecmichal.ceprojects.domain.projects.ProjectsRepository;
import pl.mielecmichal.ceprojects.domain.users.CurrentUserRepository;
import pl.mielecmichal.ceprojects.domain.users.UsersRepository;
import spark.Request;
import spark.Response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@AllArgsConstructor
public class ProjectTaskCreationHandler extends VelocityHandler {

    private final ProjectsRepository projectsRepository;
    private final UsersRepository usersRepository;
    private final ProjectDetailsHandler projectDetailsHandler;

    @Override
    protected Template handleInternal(Request request, Response response, Map<String, Object> model) throws Exception {
        ProjectTask task = ProjectTask.builder()
                .name(request.queryParamOrDefault("name", ""))
                .description(request.queryParamOrDefault("description", ""))
                .assigneeLogin(request.queryParamOrDefault("assigneeLogin", ""))
                .projectName(request.queryParamOrDefault("projectName", ""))
                .expirationDate(LocalDate.parse(request.queryParamOrDefault("expiration", ""), DateTimeFormatter.ISO_DATE).atTime(12, 0))
                .build();

        CurrentUserRepository currentUserRepository = new CurrentUserRepository(request.session());
        ValidatingCommand<ProjectTask> command = Command.projectTaskCreationCommand(projectsRepository, currentUserRepository, usersRepository);
        CommandResult result = command.execute(task);

        if (result == Command.CommandResult.SUCCESS) {
            response.redirect(Mapping.PROJECT_DETAILS.getPath().replace(":projectName", task.getProjectName()));
            return null;
        }

        if (result == Command.CommandResult.VALIDATION_ERROR) {
            model.put("errorMessages", command.getMessages());
        }
        return projectDetailsHandler.handleInternal(request, response, model);

    }

}
