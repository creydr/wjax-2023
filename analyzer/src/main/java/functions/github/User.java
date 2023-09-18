package functions.github;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class User {

    private String login;
    @JsonProperty("avatar_url")
    private String avatarUrl;

    @JsonProperty("html_url")
    private String url;

    @JsonProperty("site_admin")
    private boolean siteAdmin;
}
