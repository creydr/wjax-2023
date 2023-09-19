package functions.github;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.net.URL;
import java.util.Date;
import java.util.List;

@Data
public class Issue {

    @JsonProperty("html_url")
    private URL url;

    private int number;

    private String title;

    private User user;

    private List<String> labels;

    private String state;

    private boolean locked;

    private User assignee;

    private List<User> assignees;

    @JsonProperty("created_at")
    private Date createdAt;

    @JsonProperty("updated_at")
    private Date updatedAt;

    @JsonProperty("closed_at")
    private Date closedAt;

    private String body;
}
