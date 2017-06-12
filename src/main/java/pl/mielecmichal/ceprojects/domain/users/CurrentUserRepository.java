package pl.mielecmichal.ceprojects.domain.users;

import lombok.AllArgsConstructor;
import pl.mielecmichal.ceprojects.constants.SessionAttribiute;
import spark.Session;

@AllArgsConstructor
public class CurrentUserRepository {

    private final Session session;

    public User getCurrentUser() {
        return session.attribute(SessionAttribiute.CURRENT_USER.name());
    }

    public void setCurrentUser(User user) {
        session.attribute(SessionAttribiute.CURRENT_USER.name(), user);
    }

}
