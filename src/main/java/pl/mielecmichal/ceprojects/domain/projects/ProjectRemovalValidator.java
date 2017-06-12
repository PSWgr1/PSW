package pl.mielecmichal.ceprojects.domain.projects;

import lombok.AllArgsConstructor;
import pl.mielecmichal.ceprojects.domain.users.CurrentUserRepository;
import pl.mielecmichal.ceprojects.domain.users.User;
import pl.mielecmichal.ceprojects.domain.validators.ValidationMessages;
import pl.mielecmichal.ceprojects.domain.validators.Validator;

@AllArgsConstructor
public class ProjectRemovalValidator implements Validator<Project> {

    private final ProjectsRepository projectsRepository;
    private final CurrentUserRepository currentUserRepository;

    @Override
    public ValidationMessages validate(Project project) {

        ValidationMessages messages = ValidationMessages.create();
        if (!projectsRepository.exists(project.getName())) {
            messages.addMessage("name", String.format("%s project does not exist!", project.getName()));
            return messages;
        }

        Project dbProject = projectsRepository.find(project.getName());
        User currentUser = currentUserRepository.getCurrentUser();
        if (!isUserProjectOwner(currentUser, dbProject)) {
            messages.addMessage("name", String.format("You are not owner of \"%s\" project!", project.getName()));
        }
        return messages;
    }

    private static boolean isUserProjectOwner(User user, Project project) {
        return project.getMemberships().stream()
                .filter(m -> m.getUser().getLogin().equals(user.getLogin()))
                .anyMatch(m -> m.getProjectRole() == ProjectRole.OWNER);
    }
}
