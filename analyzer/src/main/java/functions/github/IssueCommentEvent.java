package functions.github;

import lombok.Data;

@Data
public class IssueCommentEvent {
    private Issue issue;
    private Comment comment;
    private Repository repository;
}
