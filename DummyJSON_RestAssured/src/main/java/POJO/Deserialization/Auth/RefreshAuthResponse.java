package POJO.Deserialization.Auth;

import lombok.Data;

@Data
public class RefreshAuthResponse {
    private String accessToken;
    private String refreshToken;
}
