package POJO.Deserialization.Users;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class AddUserResponse {
    private int id;
    private String firstName;
    private String lastName;
    private Integer age;
    // أضف باقي الحقول لو احتجتها لاحقًا
} 