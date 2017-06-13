package pl.mielecmichal.ceprojects.domain.projects;

import lombok.AllArgsConstructor;
import pl.mielecmichal.ceprojects.domain.commands.Command;

@AllArgsConstructor
public class ProjectMembershipCreationCommand implements Command<ProjectMembership> {

    private final ProjectsRepository projectsRepository;

    @Override
    public CommandResult execute(ProjectMembership membership) {
        Project editedProject = projectsRepository.find(membership.getProjectName());
        Project project = editedProject.addMembership(membership);
        projectsRepository.save(project);
        return CommandResult.SUCCESS;
    }
}
