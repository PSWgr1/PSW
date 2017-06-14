package pl.mielecmichal.ceprojects.handlers;

import lombok.AllArgsConstructor;
import pl.mielecmichal.ceprojects.constants.Mapping;
import pl.mielecmichal.ceprojects.constants.Template;
import pl.mielecmichal.ceprojects.domain.projects.Project;
import pl.mielecmichal.ceprojects.domain.projects.ProjectTask;
import pl.mielecmichal.ceprojects.domain.projects.ProjectsRepository;
import spark.Request;
import spark.Response;

import java.util.Map;

@AllArgsConstructor
public class ProjectTaskDeletionHandler extends VelocityHandler {

    private final ProjectsRepository projectsRepository;

    @Override
    protected Template handleInternal(Request request, Response response, Map<String, Object> model) throws Exception {

        Project project = projectsRepository.find(request.queryParamOrDefault("projectName", ""));
        ProjectTask task = project.findTask(request.queryParamOrDefault("taskName", ""));
        project = project.removeTask(task);
        projectsRepository.save(project);

        response.redirect(Mapping.PROJECT_DETAILS.getPath().replace(":projectName", project.getName()));
        return null;
    }
}
