package functions.analyzer;

import com.theokanning.openai.completion.CompletionChoice;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;

import java.util.Arrays;
import java.util.List;

public class Analyzer {

    private OpenAiService service;

    public Analyzer(String apiToken) {
        this.service = new OpenAiService(apiToken);
    }

    public Classification predict(String message) {
        CompletionRequest completionRequest = CompletionRequest.builder()
                .prompt("Sentiment analysis of the following text:\n" + message)
                .model("text-davinci-003")
                .echo(false)
                .temperature(0.5)
                .maxTokens(4)
                .topP(1.0)
                .frequencyPenalty(0.0)
                .presencePenalty(0.0)
                .n(1)
                .build();

        for (CompletionChoice choice : this.service.createCompletion(completionRequest).getChoices()) {
            //only take the first one

            switch (choice.getText().strip().toLowerCase()) {
                case "positive":
                    return Classification.POSITIVE;
                case "negative":
                    return Classification.NEGATIVE;
                case "neutral":
                    return Classification.NEUTRAL;
                default:
                    return Classification.UNKNOWN;
            }
        }

        return Classification.UNKNOWN;
    }
}
