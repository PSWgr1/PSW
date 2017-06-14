package pl.mielecmichal.ceprojects.domain.projects;

import lombok.AllArgsConstructor;
import pl.mielecmichal.ceprojects.domain.commands.Command;
import pl.mielecmichal.ceprojects.domain.users.CurrentUserRepository;
import pl.mielecmichal.ceprojects.domain.users.UsersRepository;

import java.time.LocalDateTime;

@AllArgsConstructor
public class ProjectTaskCreationCommand implements Command<ProjectTask> {

    private final ProjectsRepository projectsRepository;
    private final CurrentUserRepository currentUserRepository;
    private final UsersRepository usersRepository;

    @Override
    public CommandResult execute(ProjectTask task) {
        Project existingProject = projectsRepository.find(task.getProjectName());
        projectsRepository.delete(existingProject.getName());
        ProjectTask projectTask = ProjectTask.builder()
                .name(task.getName())
                .assigneeLogin(task.getAssigneeLogin())
                .authorLogin(currentUserRepository.getCurrentUser().getLogin())
                .creationDate(LocalDateTime.now())
                .expirationDate(task.getExpirationDate())
                .description(task.getDescription())
                .projectName(existingProject.getName())
                .status(ProjectTaskStatus.CREATED)
                .build();

        Project project = existingProject.addTast(projectTask);
        projectsRepository.save(project);
        return CommandResult.SUCCESS;
    }
}
