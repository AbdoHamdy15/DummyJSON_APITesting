package POJO.Deserialization.Users;

import POJO.Deserialization.Auth.AuthUserResponse;
import lombok.Data;
import java.util.List;

@Data
public class UsersListResponse {
    private List<AuthUserResponse> users;
    private int total;
    private int skip;
    private int limit;
} 