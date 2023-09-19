package functions.github;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.net.URL;
import java.util.Date;

@Data
public class Comment {

    @JsonProperty("html_url")
    private URL url;

    @JsonProperty("issue_url")
    private URL issueUrl;

    private User user;

    @JsonProperty("created_at")
    private Date createdAt;

    @JsonProperty("updated_at")
    private Date updatedAt;

    private String body;
}
