package pl.mielecmichal.ceprojects.domain.users;

import lombok.AllArgsConstructor;
import pl.mielecmichal.ceprojects.domain.commands.Command;

@AllArgsConstructor
public class DeleteUserCommand implements Command<User> {

    private final UsersRepository usersRepository;

    @Override
    public CommandResult execute(User user) {
        usersRepository.delete(user.getLogin());
        return CommandResult.SUCCESS;
    }
}
