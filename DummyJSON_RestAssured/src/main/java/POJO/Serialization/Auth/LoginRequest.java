package POJO.Serialization.Auth;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
    private int expiresInMins;
}
