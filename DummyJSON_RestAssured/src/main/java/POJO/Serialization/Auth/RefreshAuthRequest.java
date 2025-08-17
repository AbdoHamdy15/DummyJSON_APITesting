package POJO.Serialization.Auth;

import lombok.Data;

@Data
public class RefreshAuthRequest {
    private String refreshToken;
    private int expiresInMins;
}
