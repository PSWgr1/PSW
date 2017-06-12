package pl.mielecmichal.ceprojects.handlers;

import lombok.AllArgsConstructor;
import pl.mielecmichal.ceprojects.constants.Template;
import pl.mielecmichal.ceprojects.domain.projects.Project;
import pl.mielecmichal.ceprojects.domain.projects.ProjectsRepository;
import pl.mielecmichal.ceprojects.domain.users.CurrentUserRepository;
import spark.Request;
import spark.Response;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class ProjectsHandler extends VelocityHandler {

    private final ProjectsRepository projectsRepository;

    @Override
    protected Template handleInternal(Request request, Response response, Map<String, Object> model) throws Exception {

        CurrentUserRepository currentUserRepository = new CurrentUserRepository(request.session());
        List<Project> projects = projectsRepository.findByUser(currentUserRepository.getCurrentUser());
        model.put("projects", projects);

        return Template.PROJECTS;
    }
}
