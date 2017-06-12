package pl.mielecmichal.ceprojects.domain.users;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UsersRepository {

    private final Map<String, User> users = new ConcurrentHashMap<>();

    public UsersRepository() {
        User user = User.builder()
                .login("m")
                .password("m")
                .name("Michał")
                .surname("Mielec")
                .email("mielecmichal@gmail.com")
                .build();
        users.put(user.getLogin(), user);

        user = User.builder()
                .login("jan_nowak")
                .password("jan_nowak")
                .name("Jan")
                .surname("Nowak")
                .email("jan_nowak@gmail.com")
                .build();
        users.put(user.getLogin(), user);

        user = User.builder()
                .login("karol_kowalski")
                .password("karol_kowalski")
                .name("Karol")
                .surname("Kowalski")
                .email("karol_kowalski@gmail.com")
                .build();
        users.put(user.getLogin(), user);
    }

    public void save(User user) {
        users.put(user.getLogin(), user);
    }

    public User find(String login) {
        return users.get(login);
    }

    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }

    public boolean exists(String login) {
        return users.containsKey(login);
    }

    public void delete(String login) {
        users.remove(login);
    }

}
