package functions;

import lombok.Builder;
import lombok.Data;

import java.net.URL;

@Data
@Builder
public class Output {
    private String author;
    private String comment;
    private URL issueUrl;
    private URL commentUrl;
}
