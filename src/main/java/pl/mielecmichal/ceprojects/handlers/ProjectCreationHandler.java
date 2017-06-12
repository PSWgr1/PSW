package pl.mielecmichal.ceprojects.handlers;

import lombok.AllArgsConstructor;
import pl.mielecmichal.ceprojects.constants.Mapping;
import pl.mielecmichal.ceprojects.constants.Template;
import pl.mielecmichal.ceprojects.domain.commands.Command;
import pl.mielecmichal.ceprojects.domain.commands.ValidatingCommand;
import pl.mielecmichal.ceprojects.domain.projects.Project;
import pl.mielecmichal.ceprojects.domain.projects.ProjectsRepository;
import pl.mielecmichal.ceprojects.domain.users.CurrentUserRepository;
import spark.Request;
import spark.Response;

import java.util.Arrays;
import java.util.Map;

@AllArgsConstructor
public class ProjectCreationHandler extends VelocityHandler {

    private final ProjectsRepository projectsRepository;

    @Override
    protected Template handleInternal(Request request, Response response, Map<String, Object> model) throws Exception {

        Project project = Project.builder()
                .name(request.queryParamOrDefault("name", ""))
                .build();

        CurrentUserRepository currentUserRepository = new CurrentUserRepository(request.session());
        ValidatingCommand<Project> command = Command.projectCreationCommand(projectsRepository, currentUserRepository);
        Command.CommandResult result = command.execute(project);
        if (result == Command.CommandResult.SUCCESS) {
            model.put("successMessages", Arrays.asList("Project created successfully!"));
            response.redirect(Mapping.PROJECTS.getPath());
            return null;
        }

        if (result == Command.CommandResult.VALIDATION_ERROR) {
            model.put("errorMessages", command.getMessages());
            return Template.PROJECTS;
        }
        return Template.PROJECTS;
    }
}
