package pl.mielecmichal.ceprojects.domain.users;

import lombok.AllArgsConstructor;
import pl.mielecmichal.ceprojects.domain.validators.ValidationMessages;
import pl.mielecmichal.ceprojects.domain.validators.Validator;

@AllArgsConstructor
public class LoginUserValidator implements Validator<User> {

    private final UsersRepository usersRepository;

    @Override
    public ValidationMessages validate(User user) {
        ValidationMessages messages = ValidationMessages.create();

        if (user.getLogin() == null || user.getLogin().isEmpty()) {
            messages.addMessage("login", "Login cannot be empty");
        }

        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            messages.addMessage("password", "Password cannot be empty");
            return messages;
        }

        User foundUser = usersRepository.find(user.getLogin());
        if (foundUser == null) {
            messages.addMessage("login", "Invalid user or password");
            return messages;
        }

        if (!user.getPassword().equals(foundUser.getPassword())) {
            messages.addMessage("login", "Invalid user or password");
        }
        return messages;
    }
}
