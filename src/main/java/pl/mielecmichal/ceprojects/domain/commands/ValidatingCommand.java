package pl.mielecmichal.ceprojects.domain.commands;

import pl.mielecmichal.ceprojects.domain.validators.ValidationMessages;
import pl.mielecmichal.ceprojects.domain.validators.Validator;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class ValidatingCommand<T> implements Command<T> {
    private final Command<T> delegate;
    private final Validator<T> validator;

    private ValidationMessages messages = ValidationMessages.create();

    public ValidatingCommand(Command<T> delegate, Validator<T> validator) {
        this.delegate = delegate;
        this.validator = validator;
    }

    @Override
    public CommandResult execute(T t) {
        messages = validator.validate(t);
        if (!messages.getMessages().isEmpty()) {
            return CommandResult.VALIDATION_ERROR;
        }

        return delegate.execute(t);
    }

    public List<String> getMessages() {
        return messages.getMessages().values()
                .stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }
}
