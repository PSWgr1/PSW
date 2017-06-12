package pl.mielecmichal.ceprojects.domain.validators;

public interface Validator<T> {

    ValidationMessages validate(T t);

}
