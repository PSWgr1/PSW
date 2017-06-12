package pl.mielecmichal.ceprojects.domain.projects;

import lombok.AllArgsConstructor;
import pl.mielecmichal.ceprojects.domain.commands.Command;
import pl.mielecmichal.ceprojects.domain.users.CurrentUserRepository;
import pl.mielecmichal.ceprojects.domain.users.User;

import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
public class ProjectCreationCommand implements Command<Project> {

    private final ProjectsRepository projectsRepository;
    private final CurrentUserRepository currentUserRepository;

    @Override
    public CommandResult execute(Project project) {
        User user = currentUserRepository.getCurrentUser();

        List<ProjectMembership> memberships = Arrays.asList(
                ProjectMembership.builder()
                        .user(user)
                        .projectRole(ProjectRole.OWNER)
                        .build()
        );

        Project newProject = Project.builder()
                .name(project.getName())
                .memberships(memberships)
                .build();

        projectsRepository.save(newProject);

        return CommandResult.SUCCESS;
    }
}
