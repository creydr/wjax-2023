# Github Comment Sentiment Analysis

This is a demo project doing sentiment analysis for GitHub issue comments based on:
* Knative Eventing
* Knative Functions

# Step 1: Deploy Knative Eventing GitHub Source Controller

```sh
kubectl apply -f https://github.com/knative-extensions/eventing-github/releases/download/knative-v1.11.1/github.yaml
```

Verify by checking that the manager is running:

```sh
kubectl -n knative-sources get pods --selector control-plane=github-controller-manager
```

# Step 2: Create Resources for the GitHub Source

## Create a Secret with GitHub Token

Follow the steps from [Create GitHub Tokens](https://github.com/knative/docs/tree/main/code-samples/eventing/github-source#create-github-tokens) to create a GitHub token for the GitHub source.

Afterwards create a secret with this token:

```sh
kubectl -n default create secret generic githubsecret --from-literal=accessToken=<your-token> --from-literal secretToken=$(head -c 8 /dev/urandom)
```

## Create a Broker for the events from the GitHub source

```sh
cat <<-EOF | kubectl apply -f -
apiVersion: eventing.knative.dev/v1
kind: Broker
metadata:
  annotations:
    eventing.knative.dev/broker.class: MTChannelBasedBroker
  name: github-events
EOF
```

## Create a Trigger & Service to display all events from the GitHub source

```sh
cat <<-EOF | kubectl apply -f -
apiVersion: serving.knative.dev/v1
kind: Service
metadata:
  name: all-issue-comments-display
spec:
  template:
    metadata:
      annotations:
        autoscaling.knative.dev/min-scale: "1"
    spec:
      containers:
        - image: gcr.io/knative-releases/knative.dev/eventing/cmd/event_display

---

apiVersion: eventing.knative.dev/v1
kind: Trigger
metadata:
  name: all-events
spec:
  broker: github-events
  subscriber:
    ref:
      apiVersion: serving.knative.dev/v1
      kind: Service
      name: all-issue-comments-display
EOF
```

## Create a GitHub Source for new issue comments

```sh
cat <<-EOF | kubectl apply -f -
apiVersion: sources.knative.dev/v1alpha1
kind: GitHubSource
metadata:
  name: github-issues
spec:
  eventTypes:
    - issue_comment
  ownerAndRepository: "creydr/wjax-2023"
  accessToken:
    secretKeyRef:
      name: githubsecret
      key: accessToken
  secretToken:
    secretKeyRef:
      name: githubsecret
      key: secretToken
  sink:
    ref:
      apiVersion: eventing.knative.dev/v1
      kind: Broker
      name: github-events
  secure: false
EOF
```

Make sure your GitHub Source can be reached publicly. Also make sure, that the GitHub Webhook gets [registered correctly](https://github.com/knative/docs/tree/main/code-samples/eventing/github-source#verify) in your repository (`creydr/wjax-2023` in the above example).

# Step 3: Verify existing setup

Verify that new comments on issues are shown as an event in the `all-events-display` Knative service. This can be done, by commenting on an issue and tailing the logs from one of the `all-events-display` pods:

```sh
kubectl logs -l serving.knative.dev/service=event-display -f
```

# Step 4: Deploy the Knative Function to analyze GitHub issue comments

```sh
pushd analyzer
func deploy
popd
```

# Step 5: Create a Trigger to send new issue comments to the Function

```sh
cat <<-EOF | kubectl apply -f -
apiVersion: eventing.knative.dev/v1
kind: Trigger
metadata:
  name: issue-comment-events-analyze
spec:
  broker: github-events
  filter:
    attributes:
      type: dev.knative.source.github.issue_comment
      action: created
  subscriber:
    uri: http://analyzer.default.svc.cluster.local
EOF
```

# Step 6: Create a Trigger & Service to display all events which were classified negatively

```sh
cat <<-EOF | kubectl apply -f -
apiVersion: serving.knative.dev/v1
kind: Service
metadata:
  name: negative-comments-display
spec:
  template:
    metadata:
      annotations:
        autoscaling.knative.dev/min-scale: "1"
    spec:
      containers:
        - image: gcr.io/knative-releases/knative.dev/eventing/cmd/event_display

---

apiVersion: eventing.knative.dev/v1
kind: Trigger
metadata:
  name: negative-comments
spec:
  broker: github-events
  filter:
    attributes:
      type: classification.negative
  subscriber:
    ref:
      apiVersion: serving.knative.dev/v1
      kind: Service
      name: negative-comments-display
EOF
```
