package pl.mielecmichal.ceprojects.domain.projects;

import lombok.Builder;
import lombok.Value;
import pl.mielecmichal.ceprojects.domain.users.User;

import java.util.ArrayList;
import java.util.List;

@Value
@Builder
public class Project {

    private String name;
    private List<ProjectMembership> memberships;
    private List<ProjectTask> tasks;

    public boolean isMember(User user) {
        return memberships.stream()
                .anyMatch(m -> m.getUserLogin().equals(user.getLogin()));
    }

    public boolean hasRole(User user, ProjectRole projectRole) {
        return memberships.stream()
                .filter(m -> m.getUserLogin().equals(user.getLogin()))
                .anyMatch(m -> m.getProjectRole() == projectRole);
    }

    public Project addMembership(ProjectMembership membership) {

        List<ProjectMembership> newMemberships = new ArrayList<>();
        newMemberships.addAll(memberships);
        newMemberships.add(membership);

        return Project.builder()
                .name(name)
                .memberships(newMemberships)
                .tasks(tasks)
                .build();
    }

    public Project addTast(ProjectTask projectTask) {
        List<ProjectTask> newTasks = new ArrayList<>();
        newTasks.addAll(tasks);
        newTasks.add(projectTask);

        return Project.builder()
                .name(name)
                .memberships(memberships)
                .tasks(newTasks)
                .build();

    }

    public Project removeTask(ProjectTask projectTask) {
        List<ProjectTask> newTasks = new ArrayList<>();
        newTasks.addAll(tasks);
        newTasks.remove(projectTask);

        return Project.builder()
                .name(name)
                .memberships(memberships)
                .tasks(newTasks)
                .build();

    }

    public ProjectTask findTask(String taskName) {
        return tasks.stream()
                .filter(t -> t.getName().equals(taskName))
                .findFirst()
                .orElse(null);
    }

}
