package pl.mielecmichal.ceprojects.domain.validators;

import lombok.Value;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Value(staticConstructor = "create")
public class ValidationMessages {

    private final Map<String, List<String>> messages = new HashMap<>();

    public void addMessage(String field, String message) {
        List<String> list = messages.getOrDefault(field, new ArrayList<>());
        list.add(message);
        messages.putIfAbsent(field, list);
    }

    public Map<String, List<String>> getMessages() {
        return messages;
    }
}
