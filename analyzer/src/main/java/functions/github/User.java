package functions.github;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.net.URL;

@Data
public class User {

    private String login;
    @JsonProperty("avatar_url")
    private URL avatarUrl;

    @JsonProperty("html_url")
    private URL url;

    @JsonProperty("site_admin")
    private boolean siteAdmin;
}
