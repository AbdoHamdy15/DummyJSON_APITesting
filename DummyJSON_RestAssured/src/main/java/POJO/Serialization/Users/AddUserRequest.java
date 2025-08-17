package POJO.Serialization.Users;

import lombok.Data;

@Data
public class AddUserRequest {
    private String firstName;
    private String lastName;
    private Integer age;
    private String email;
    private String gender;
    private String phone;
    // أضف باقي الحقول لو احتجتها لاحقًا
} 