package pl.mielecmichal.ceprojects.domain.commands;

import pl.mielecmichal.ceprojects.domain.projects.*;
import pl.mielecmichal.ceprojects.domain.users.*;

public interface Command<T> {
    CommandResult execute(T t);

    enum CommandResult {
        SUCCESS,
        ERROR,
        VALIDATION_ERROR,
        UNEXPECTED_ERROR
    }

    static ValidatingCommand<User> registerUserCommand(UsersRepository usersRepository) {
        return new ValidatingCommand<>(new RegisterUserCommand(usersRepository), new RegisterUserValidator(usersRepository));
    }

    static ValidatingCommand<User> loginUserCommand(UsersRepository usersRepository) {
        return new ValidatingCommand<>(new LoginUserCommand(usersRepository), new LoginUserValidator(usersRepository));
    }

    static ValidatingCommand<User> deleteUserCommand(UsersRepository usersRepository) {
        DeleteUserCommand deleteUserCommand = new DeleteUserCommand(usersRepository);
        DeleteUserValidator deleteUserValidator = new DeleteUserValidator(usersRepository);
        return new ValidatingCommand<>(deleteUserCommand, deleteUserValidator);
    }

    static ValidatingCommand<Project> projectCreationCommand(ProjectsRepository projectsRepository, CurrentUserRepository currentUserRepository) {
        ProjectCreationCommand command = new ProjectCreationCommand(projectsRepository, currentUserRepository);
        ProjectCreationValidator validator = new ProjectCreationValidator();
        return new ValidatingCommand<>(command, validator);
    }

    static ValidatingCommand<Project> projectRemovalCommand(ProjectsRepository projectsRepository, CurrentUserRepository currentUserRepository) {
        ProjectRemovalCommand command = new ProjectRemovalCommand(projectsRepository);
        ProjectRemovalValidator validator = new ProjectRemovalValidator(projectsRepository, currentUserRepository);
        return new ValidatingCommand<>(command, validator);
    }

    static ValidatingCommand<ProjectMembership> projectMembershipCreationCommand(ProjectsRepository projectsRepository) {
        ProjectMembershipCreationCommand command = new ProjectMembershipCreationCommand(projectsRepository);
        ProjectMembershipCreationValidator validator = new ProjectMembershipCreationValidator();
        return new ValidatingCommand<>(command, validator);
    }
}
