package pl.mielecmichal.ceprojects.domain.users;

import lombok.*;

@Value
@Builder
public class User {

    private final String login;
    private final String email;
    private final String name;
    private final String surname;
    private final String password;

}
