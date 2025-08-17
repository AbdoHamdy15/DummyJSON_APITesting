package POJO.Deserialization.Users;

import lombok.Data;
import java.util.List;

@Data
public class UserTodosResponse {
    private List<Todo> todos;
    private int total;
    private int skip;
    private int limit;

    @Data
    public static class Todo {
        private int id;
        private String todo;
        private boolean completed;
        private int userId;
    }
} 