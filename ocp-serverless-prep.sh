#!/bin/bash

set -eo pipefail

echo "**** Installing OpenShift Serverless Operator ****"
cat <<EOF | kubectl apply -f -
apiVersion: v1
kind: Namespace
metadata:
  name: openshift-serverless
---
apiVersion: operators.coreos.com/v1
kind: OperatorGroup
metadata:
  name: serverless-operators
  namespace: openshift-serverless
spec: {}
---
apiVersion: operators.coreos.com/v1alpha1
kind: Subscription
metadata:
  name: serverless-operator
  namespace: openshift-serverless
spec:
  channel: stable
  name: serverless-operator
  source: redhat-operators
  sourceNamespace: openshift-marketplace
EOF

echo "**** Waiting for install plan to finish ****"
kubectl -n openshift-serverless wait --for=condition=installplanpending --timeout 300s subscription serverless-operator
install_plan=$(oc -n openshift-serverless get subscription serverless-operator -ojsonpath='{.status.installPlanRef.name}')
kubectl -n openshift-serverless wait --for=condition=installed --timeout 300s installplan ${install_plan}
echo "**** Operator installed ****"

echo "**** Installing Serving ****"
cat <<EOF | kubectl apply -f -
apiVersion: v1
kind: Namespace
metadata:
  name: knative-serving
---
apiVersion: operator.knative.dev/v1beta1
kind: KnativeServing
metadata:
    name: knative-serving
    namespace: knative-serving
EOF

echo "**** Waiting for Serving being up ****"
kubectl -n knative-serving wait --for=condition=ready --timeout 300s knativeserving.operator.knative.dev knative-serving

echo "**** Installing Eventing ****"
cat <<EOF | kubectl apply -f -
apiVersion: v1
kind: Namespace
metadata:
  name: knative-eventing
---
apiVersion: operator.knative.dev/v1beta1
kind: KnativeEventing
metadata:
    name: knative-eventing
    namespace: knative-eventing
spec:
  config:
    config-features:
      kreference-group: "disabled"
      delivery-retryafter: "disabled"
      delivery-timeout: "enabled"
      kreference-mapping: "disabled"
      strict-subscriber: "disabled"
      new-trigger-filters: "enabled"
EOF

echo "**** Waiting for Eventing being up ****"
kubectl -n knative-eventing wait --for=condition=ready --timeout 300s knativeeventing.operator.knative.dev knative-eventing

echo "**** Install GithubSource controller ****"
kubectl apply -f https://github.com/knative-extensions/eventing-github/releases/download/knative-v1.11.1/github.yaml

echo "**** Wait for GithubSource controller being up ****"
kubectl -n knative-sources wait --for=condition=ready --timeout 300s pod --selector control-plane=github-controller-manager

echo "**** Done ****"
