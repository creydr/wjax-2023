package functions.github;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Repository {
    private String name;
    @JsonProperty("full_name")
    private String fullName;
    private User owner;
    @JsonProperty("html_url")
    private String url;
    private String description;
}
