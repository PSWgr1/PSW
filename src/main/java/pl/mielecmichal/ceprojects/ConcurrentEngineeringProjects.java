package pl.mielecmichal.ceprojects;

import org.eclipse.jetty.http.HttpMethod;
import pl.mielecmichal.ceprojects.constants.Mapping;
import pl.mielecmichal.ceprojects.domain.projects.ProjectsRepository;
import pl.mielecmichal.ceprojects.domain.users.CurrentUserRepository;
import pl.mielecmichal.ceprojects.filters.AuthenticationFilter;
import pl.mielecmichal.ceprojects.filters.AuthenticationRule;
import pl.mielecmichal.ceprojects.handlers.*;
import pl.mielecmichal.ceprojects.domain.users.UsersRepository;
import spark.Spark;

import java.util.Arrays;
import java.util.PrimitiveIterator;

public class ConcurrentEngineeringProjects {

    public static void main(String[] args) {
        UsersRepository usersRepository = new UsersRepository();
        ProjectsRepository projectsRepository = new ProjectsRepository();
        IntroHandler introHandler = new IntroHandler();
        RegistrationHandler registrationHandler = new RegistrationHandler(usersRepository);
        LoginHandler loginHandler = new LoginHandler(usersRepository);
        LogoutHandler logoutHandler = new LogoutHandler();
        ProjectsHandler projectsHandler = new ProjectsHandler(projectsRepository);
        ProjectCreationHandler projectCreationHandler = new ProjectCreationHandler(projectsRepository);
        ProjectRemovalHandler projectRemovalHandler = new ProjectRemovalHandler(projectsRepository);
        ProjectDetailsHandler projectDetailsHandler = new ProjectDetailsHandler(usersRepository, projectsRepository);
        ProjectMembershipCreationHandler projectMembershipCreationHandler = new ProjectMembershipCreationHandler(projectsRepository, projectDetailsHandler);

        Spark.staticFileLocation("/static");

        Spark.before(new AuthenticationFilter(
                Arrays.asList(
                        new AuthenticationRule(HttpMethod.GET, Mapping.INTRO),
                        new AuthenticationRule(HttpMethod.POST, Mapping.LOGIN),
                        new AuthenticationRule(HttpMethod.POST, Mapping.REGISTRATION)
                )));

        Spark.get(Mapping.INTRO.getPath(), introHandler);
        Spark.get(Mapping.PROJECTS.getPath(), projectsHandler);
        Spark.get(Mapping.LOGOUT.getPath(), logoutHandler);
        Spark.post(Mapping.REGISTRATION.getPath(), registrationHandler);
        Spark.post(Mapping.LOGIN.getPath(), loginHandler);
        Spark.get(Mapping.PROJECT_DETAILS.getPath(), projectDetailsHandler);
        Spark.post(Mapping.PROJECT_CREATION.getPath(), projectCreationHandler);
        Spark.post(Mapping.PROJECT_REMOVAL.getPath(), projectRemovalHandler);
        Spark.post(Mapping.PROJECT_MEMBERSHIP_CREATION.getPath(), projectMembershipCreationHandler);
    }

    private ConcurrentEngineeringProjects() {
    }
}
