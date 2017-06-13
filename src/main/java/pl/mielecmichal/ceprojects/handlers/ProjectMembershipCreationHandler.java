package pl.mielecmichal.ceprojects.handlers;

import lombok.AllArgsConstructor;
import pl.mielecmichal.ceprojects.constants.Mapping;
import pl.mielecmichal.ceprojects.constants.Template;
import pl.mielecmichal.ceprojects.domain.commands.Command;
import pl.mielecmichal.ceprojects.domain.commands.Command.CommandResult;
import pl.mielecmichal.ceprojects.domain.commands.ValidatingCommand;
import pl.mielecmichal.ceprojects.domain.projects.ProjectMembership;
import pl.mielecmichal.ceprojects.domain.projects.ProjectRole;
import pl.mielecmichal.ceprojects.domain.projects.ProjectsRepository;
import pl.mielecmichal.ceprojects.domain.users.CurrentUserRepository;
import spark.Request;
import spark.Response;

import java.util.Map;

@AllArgsConstructor
public class ProjectMembershipCreationHandler extends VelocityHandler {

    private final ProjectsRepository projectsRepository;
    private final ProjectDetailsHandler projectDetailsHandler;

    @Override
    protected Template handleInternal(Request request, Response response, Map<String, Object> model) throws Exception {

        CurrentUserRepository currentUserRepository = new CurrentUserRepository(request.session());

        ProjectMembership membership = ProjectMembership.builder()
                .projectName(request.queryParamOrDefault("projectName", ""))
                .userLogin(request.queryParamOrDefault("userLogin", ""))
                .projectRole(ProjectRole.valueOf(request.queryParamOrDefault("projectRole", ProjectRole.GUEST.name())))
                .build();

        ValidatingCommand<ProjectMembership> command = Command.projectMembershipCreationCommand(projectsRepository);
        CommandResult result = command.execute(membership);

        if (result == Command.CommandResult.SUCCESS) {
            response.redirect(Mapping.PROJECT_DETAILS.getPath().replace(":projectName", membership.getProjectName()));
            return null;
        }

        if (result == Command.CommandResult.VALIDATION_ERROR) {
            model.put("errorMessages", command.getMessages());
        }
        return projectDetailsHandler.handleInternal(request, response, model);
    }
}
