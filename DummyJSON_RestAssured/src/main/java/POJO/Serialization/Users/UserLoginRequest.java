package POJO.Serialization.Users;

import lombok.Data;

@Data
public class UserLoginRequest {
    private String username;
    private String password;
    private Integer expiresInMins; // optional
} 