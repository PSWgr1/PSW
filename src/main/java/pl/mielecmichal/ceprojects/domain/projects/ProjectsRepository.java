package pl.mielecmichal.ceprojects.domain.projects;

import pl.mielecmichal.ceprojects.domain.users.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class ProjectsRepository {

    private final Map<String, Project> projects = new ConcurrentHashMap<>();

    public ProjectsRepository() {
    }

    public void save(Project project) {
        projects.put(project.getName(), project);
    }

    public Project find(String name) {
        return projects.get(name);
    }

    public List<Project> findByUser(User user) {
        List<Project> owner = projects.values().stream()
                .filter(p -> p.getMemberships().stream().anyMatch(m -> m.getUserLogin().equals(user.getLogin())))
                .filter(p -> p.getMemberships().stream().anyMatch(m -> m.getProjectRole() == ProjectRole.OWNER))
                .collect(Collectors.toList());

        List<Project> member = projects.values().stream()
                .filter(p -> p.getMemberships().stream().anyMatch(m -> m.getUserLogin().equals(user.getLogin())))
                .filter(p -> p.getMemberships().stream().anyMatch(m -> m.getProjectRole() == ProjectRole.MEMBER))
                .collect(Collectors.toList());

        List<Project> guest = projects.values().stream()
                .filter(p -> p.getMemberships().stream().anyMatch(m -> m.getUserLogin().equals(user.getLogin())))
                .filter(p -> p.getMemberships().stream().anyMatch(m -> m.getProjectRole() == ProjectRole.GUEST))
                .collect(Collectors.toList());

        List<Project> sorted = new ArrayList<>();
        sorted.addAll(owner);
        sorted.addAll(member);
        sorted.addAll(guest);
        return sorted;
    }

    public boolean exists(String name) {
        return projects.containsKey(name);
    }

    public void delete(String name) {
        projects.remove(name);
    }
}
