package pl.mielecmichal.ceprojects.handlers;

import lombok.AllArgsConstructor;
import pl.mielecmichal.ceprojects.constants.Template;
import pl.mielecmichal.ceprojects.domain.projects.Project;
import pl.mielecmichal.ceprojects.domain.projects.ProjectRole;
import pl.mielecmichal.ceprojects.domain.projects.ProjectsRepository;
import pl.mielecmichal.ceprojects.domain.users.CurrentUserRepository;
import pl.mielecmichal.ceprojects.domain.users.User;
import pl.mielecmichal.ceprojects.domain.users.UsersRepository;
import spark.Request;
import spark.Response;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@AllArgsConstructor
public class ProjectDetailsHandler extends VelocityHandler {

    private final UsersRepository usersRepository;
    private final ProjectsRepository projectsRepository;

    @Override
    protected Template handleInternal(Request request, Response response, Map<String, Object> model) throws Exception {
        String name = request.params("projectName");
        Project project = projectsRepository.find(name);
        model.put("project", project);
        List<User> memberships = project.getMemberships()
                .stream()
                .map(m -> usersRepository.find(m.getUserLogin()))
                .collect(Collectors.toList());
        model.put("memberships", memberships);
        CurrentUserRepository currentUserRepository = new CurrentUserRepository(request.session());
        User currentUser = currentUserRepository.getCurrentUser();
        model.put("isUserOwner", project.hasRole(currentUser, ProjectRole.OWNER));

        List<User> allUsers = usersRepository.findAll()
                .stream()
                .filter(u -> !project.isMember(u))
                .collect(Collectors.toList());

        model.put("allUsers", allUsers);

        return Template.PROJECT_DETAILS;
    }
}
