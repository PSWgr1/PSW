package pl.mielecmichal.ceprojects.domain.users;

import lombok.AllArgsConstructor;
import pl.mielecmichal.ceprojects.domain.validators.ValidationMessages;
import pl.mielecmichal.ceprojects.domain.validators.Validator;

@AllArgsConstructor
public class RegisterUserValidator implements Validator<User> {

    private final UsersRepository usersRepository;

    @Override
    public ValidationMessages validate(User user) {

        ValidationMessages messages = ValidationMessages.create();

        if (user.getLogin() == null || user.getLogin().isEmpty()) {
            messages.addMessage("login", "Login cannot be empty");
        }

        if (user.getLogin().length() < 6 || user.getLogin().length() > 20) {
            messages.addMessage("login", "Login length should be from 6 to 20");
        }

        if (!user.getLogin().chars().allMatch(c -> Character.isAlphabetic(c) || Character.isDigit(c))) {
            messages.addMessage("login", "Login should contains only alphabetic characters or digits");
        }

        if (usersRepository.exists(user.getLogin())) {
            messages.addMessage("login", "login already exists");
        }

        return messages;
    }
}
