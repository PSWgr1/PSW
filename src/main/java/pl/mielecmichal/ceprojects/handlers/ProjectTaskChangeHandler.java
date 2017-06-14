package pl.mielecmichal.ceprojects.handlers;

import lombok.AllArgsConstructor;
import pl.mielecmichal.ceprojects.constants.Mapping;
import pl.mielecmichal.ceprojects.constants.Template;
import pl.mielecmichal.ceprojects.domain.projects.Project;
import pl.mielecmichal.ceprojects.domain.projects.ProjectTask;
import pl.mielecmichal.ceprojects.domain.projects.ProjectTaskStatus;
import pl.mielecmichal.ceprojects.domain.projects.ProjectsRepository;
import spark.Request;
import spark.Response;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@AllArgsConstructor
public class ProjectTaskChangeHandler extends VelocityHandler {

    private final ProjectsRepository projectsRepository;

    @Override
    protected Template handleInternal(Request request, Response response, Map<String, Object> model) throws Exception {


        Project project = projectsRepository.find(request.queryParamOrDefault("projectName", ""));

        ProjectTask task = project.findTask(request.queryParamOrDefault("taskName", ""));

        ProjectTask newTask = ProjectTask.builder()
                .assigneeLogin(request.queryParamOrDefault("assigneeLogin", ""))
                .expirationDate(LocalDate.parse(request.queryParamOrDefault("expiration", ""), DateTimeFormatter.ISO_DATE).atTime(12, 0))
                .status(ProjectTaskStatus.valueOf(request.queryParamOrDefault("status", "")))
                .name(task.getName())
                .description(task.getDescription())
                .creationDate(task.getCreationDate())
                .authorLogin(task.getAuthorLogin())
                .projectName(task.getProjectName())
                .build();

        project = project.removeTask(task);
        project = project.addTast(newTask);

        projectsRepository.save(project);
        response.redirect(Mapping.PROJECT_DETAILS.getPath().replace(":projectName", task.getProjectName()));

        return null;
    }
}
