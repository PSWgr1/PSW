package pl.mielecmichal.ceprojects.domain.users;

import pl.mielecmichal.ceprojects.domain.commands.Command;

public class LoginUserCommand implements Command<User> {

    private final UsersRepository usersRepository;

    public LoginUserCommand(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public CommandResult execute(User user) {
        return CommandResult.SUCCESS;
    }
}
