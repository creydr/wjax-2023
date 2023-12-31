package functions;

import functions.analyzer.Analyzer;
import functions.analyzer.Classification;
import functions.github.IssueCommentEvent;
import io.quarkus.funqy.Funq;
import io.quarkus.funqy.knative.events.CloudEvent;
import io.quarkus.funqy.knative.events.CloudEventBuilder;
import io.quarkus.logging.Log;
import jakarta.inject.Inject;

public class Function {

    @Inject
    private Analyzer analyzer;

    @Funq
    public CloudEvent<Output> function(CloudEvent<IssueCommentEvent> in) {
        Log.infof("Got request for issueCommentEvent: %s", in.id());

        Classification result = this.analyzer.predict(in.data().getComment().getBody());

        String type;
        switch (result) {
            case POSITIVE -> type = "classification.positive";
            case NEGATIVE -> type = "classification.negative";
            case NEUTRAL -> type = "classification.neutral";
            default -> type = "classification.unknown";
        }

        CloudEvent<Output> ce = CloudEventBuilder.create()
                .type(type)
                .build(Output.builder()
                    .comment(in.data().getComment().getBody())
                    .author(in.data().getComment().getUser().getLogin())
                    .commentUrl(in.data().getComment().getUrl())
                    .issueUrl(in.data().getIssue().getUrl())
                    .build());

        Log.infof("Predicted \"%s\" from %s created at %s as %s",
                in.data().getComment().getBody(),
                in.data().getComment().getUser().getLogin(),
                in.data().getComment().getCreatedAt(),
                type);

        return ce;
    }
}
