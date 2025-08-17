package POJO.Deserialization.Users;

import lombok.Data;

@Data
public class UserLoginResponse {
    private int id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String gender;
    private String image;
    private String accessToken;
    private String refreshToken;
} 