package POJO.Deserialization.Users;

import lombok.Data;

@Data
public class UserMeResponse {
    private int id;
    private String firstName;
    private String lastName;
    private String maidenName;
    private int age;
    private String gender;
    private String email;
    private String image;
    // أضف باقي الحقول إذا احتجتها لاحقًا
} 