package pl.mielecmichal.ceprojects.domain.users;

import lombok.AllArgsConstructor;
import pl.mielecmichal.ceprojects.domain.validators.ValidationMessages;
import pl.mielecmichal.ceprojects.domain.validators.Validator;

@AllArgsConstructor
public class DeleteUserValidator implements Validator<User> {

    private UsersRepository usersRepository;

    @Override
    public ValidationMessages validate(User user) {

        ValidationMessages messages = ValidationMessages.create();

        if (!usersRepository.exists(user.getLogin())) {
            messages.addMessage("login", "Login does not exists");
        }
        return messages;
    }
}
