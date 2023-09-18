package functions.github;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class Comment {

    @JsonProperty("html_url")
    private String url;

    @JsonProperty("issue_url")
    private String issueUrl;

    private User user;

    @JsonProperty("created_at")
    private Date createdAt;

    @JsonProperty("updated_at")
    private Date updatedAt;

    private String body;
}
