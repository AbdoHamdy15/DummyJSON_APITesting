package POJO.Deserialization.Users;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class UserPostsResponse {
    private List<Post> posts;
    private int total;
    private int skip;
    private int limit;

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class Post {
        private int id;
        private String title;
        private String body;
        private int userId;
        private List<String> tags;
        private Reactions reactions;
        private Integer views; // add views if present in response

        @JsonIgnoreProperties(ignoreUnknown = true)
        @Data
        public static class Reactions {
            private int likes;
            private int dislikes;
        }
    }
} 