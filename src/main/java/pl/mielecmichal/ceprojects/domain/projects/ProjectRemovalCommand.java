package pl.mielecmichal.ceprojects.domain.projects;

import lombok.AllArgsConstructor;
import pl.mielecmichal.ceprojects.domain.commands.Command;

@AllArgsConstructor
public class ProjectRemovalCommand implements Command<Project> {

    private final ProjectsRepository projectsRepository;

    @Override
    public CommandResult execute(Project project) {
        projectsRepository.delete(project.getName());
        return CommandResult.SUCCESS;
    }
}
