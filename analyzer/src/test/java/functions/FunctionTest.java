package functions;

import functions.analyzer.Analyzer;
import functions.analyzer.Classification;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;

@QuarkusTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FunctionTest {

    @InjectMock
    Analyzer analyzer;

    @BeforeAll
    public void setup() {
        Mockito.when(analyzer.predict("This project is awesome.")).thenReturn(Classification.POSITIVE);
        Mockito.when(analyzer.predict("This sucks")).thenReturn(Classification.NEGATIVE);
    }

    @Test
    public void testFunctionIntegration() {
        String requestData = """
                {
                  "action": "created",
                  "issue": {
                    "url": "https://api.github.com/repos/creydr/test/issues/1",
                    "labels_url": "https://api.github.com/repos/creydr/test/issues/1/labels{/name}",
                    "comments_url": "https://api.github.com/repos/creydr/test/issues/1/comments",
                    "events_url": "https://api.github.com/repos/creydr/test/issues/1/events",
                    "html_url": "https://github.com/creydr/test/issues/1",
                    "id": 1893896471,
                    "node_id": "I_kwDOKSnLEs5w4pEX",
                    "number": 1,
                    "title": "First test issue",
                    "user": {
                      "login": "creydr",
                      "id": 8654480,
                      "node_id": "MDQ6VXNlcjg2NTQ0ODA=",
                      "avatar_url": "https://avatars.githubusercontent.com/u/8654480?v=4",
                      "gravatar_id": "",
                      "url": "https://api.github.com/users/creydr",
                      "html_url": "https://github.com/creydr",
                      "followers_url": "https://api.github.com/users/creydr/followers",
                      "following_url": "https://api.github.com/users/creydr/following{/other_user}",
                      "gists_url": "https://api.github.com/users/creydr/gists{/gist_id}",
                      "starred_url": "https://api.github.com/users/creydr/starred{/owner}{/repo}",
                      "subscriptions_url": "https://api.github.com/users/creydr/subscriptions",
                      "organizations_url": "https://api.github.com/users/creydr/orgs",
                      "repos_url": "https://api.github.com/users/creydr/repos",
                      "events_url": "https://api.github.com/users/creydr/events{/privacy}",
                      "received_events_url": "https://api.github.com/users/creydr/received_events",
                      "type": "User",
                      "site_admin": false
                    },
                    "labels": [],
                    "state": "open",
                    "locked": false,
                    "assignee": null,
                    "assignees": [],
                    "milestone": null,
                    "comments": 1,
                    "created_at": "2023-09-13T07:14:02Z",
                    "updated_at": "2023-09-13T07:16:01Z",
                    "closed_at": null,
                    "body": "This is the first test issue"
                  },
                  "comment": {
                    "url": "https://api.github.com/repos/creydr/test/issues/comments/1717076224",
                    "html_url": "https://github.com/creydr/test/issues/1#issuecomment-1717076224",
                    "issue_url": "https://api.github.com/repos/creydr/test/issues/1",
                    "id": 1717076224,
                    "node_id": "IC_kwDOKSnLEs5mWIEA",
                    "user": {
                      "login": "creydr",
                      "id": 8654480,
                      "node_id": "MDQ6VXNlcjg2NTQ0ODA=",
                      "avatar_url": "https://avatars.githubusercontent.com/u/8654480?v=4",
                      "gravatar_id": "",
                      "url": "https://api.github.com/users/creydr",
                      "html_url": "https://github.com/creydr",
                      "followers_url": "https://api.github.com/users/creydr/followers",
                      "following_url": "https://api.github.com/users/creydr/following{/other_user}",
                      "gists_url": "https://api.github.com/users/creydr/gists{/gist_id}",
                      "starred_url": "https://api.github.com/users/creydr/starred{/owner}{/repo}",
                      "subscriptions_url": "https://api.github.com/users/creydr/subscriptions",
                      "organizations_url": "https://api.github.com/users/creydr/orgs",
                      "repos_url": "https://api.github.com/users/creydr/repos",
                      "events_url": "https://api.github.com/users/creydr/events{/privacy}",
                      "received_events_url": "https://api.github.com/users/creydr/received_events",
                      "type": "User",
                      "site_admin": false
                    },
                    "created_at": "2023-09-13T07:16:01Z",
                    "updated_at": "2023-09-13T07:16:01Z",
                    "body": "This project is awesome.",
                    "author_association": "OWNER"
                  },
                  "repository": {
                    "id": 690604818,
                    "node_id": "R_kgDOKSnLEg",
                    "name": "test",
                    "full_name": "creydr/test",
                    "owner": {
                      "login": "creydr",
                      "id": 8654480,
                      "node_id": "MDQ6VXNlcjg2NTQ0ODA=",
                      "avatar_url": "https://avatars.githubusercontent.com/u/8654480?v=4",
                      "gravatar_id": "",
                      "url": "https://api.github.com/users/creydr",
                      "html_url": "https://github.com/creydr",
                      "followers_url": "https://api.github.com/users/creydr/followers",
                      "following_url": "https://api.github.com/users/creydr/following{/other_user}",
                      "gists_url": "https://api.github.com/users/creydr/gists{/gist_id}",
                      "starred_url": "https://api.github.com/users/creydr/starred{/owner}{/repo}",
                      "subscriptions_url": "https://api.github.com/users/creydr/subscriptions",
                      "organizations_url": "https://api.github.com/users/creydr/orgs",
                      "repos_url": "https://api.github.com/users/creydr/repos",
                      "events_url": "https://api.github.com/users/creydr/events{/privacy}",
                      "received_events_url": "https://api.github.com/users/creydr/received_events",
                      "type": "User",
                      "site_admin": false
                    },
                    "private": false,
                    "html_url": "https://github.com/creydr/test",
                    "description": "",
                    "fork": false,
                    "url": "https://api.github.com/repos/creydr/test",
                    "forks_url": "https://api.github.com/repos/creydr/test/forks",
                    "keys_url": "https://api.github.com/repos/creydr/test/keys{/key_id}",
                    "collaborators_url": "https://api.github.com/repos/creydr/test/collaborators{/collaborator}",
                    "teams_url": "https://api.github.com/repos/creydr/test/teams",
                    "hooks_url": "https://api.github.com/repos/creydr/test/hooks",
                    "issue_events_url": "https://api.github.com/repos/creydr/test/issues/events{/number}",
                    "events_url": "https://api.github.com/repos/creydr/test/events",
                    "assignees_url": "https://api.github.com/repos/creydr/test/assignees{/user}",
                    "branches_url": "https://api.github.com/repos/creydr/test/branches{/branch}",
                    "tags_url": "https://api.github.com/repos/creydr/test/tags",
                    "blobs_url": "https://api.github.com/repos/creydr/test/git/blobs{/sha}",
                    "git_tags_url": "https://api.github.com/repos/creydr/test/git/tags{/sha}",
                    "git_refs_url": "https://api.github.com/repos/creydr/test/git/refs{/sha}",
                    "trees_url": "https://api.github.com/repos/creydr/test/git/trees{/sha}",
                    "statuses_url": "https://api.github.com/repos/creydr/test/statuses/{sha}",
                    "languages_url": "https://api.github.com/repos/creydr/test/languages",
                    "stargazers_url": "https://api.github.com/repos/creydr/test/stargazers",
                    "contributors_url": "https://api.github.com/repos/creydr/test/contributors",
                    "subscribers_url": "https://api.github.com/repos/creydr/test/subscribers",
                    "subscription_url": "https://api.github.com/repos/creydr/test/subscription",
                    "commits_url": "https://api.github.com/repos/creydr/test/commits{/sha}",
                    "git_commits_url": "https://api.github.com/repos/creydr/test/git/commits{/sha}",
                    "comments_url": "https://api.github.com/repos/creydr/test/comments{/number}",
                    "issue_comment_url": "https://api.github.com/repos/creydr/test/issues/comments{/number}",
                    "contents_url": "https://api.github.com/repos/creydr/test/contents/{+path}",
                    "compare_url": "https://api.github.com/repos/creydr/test/compare/{base}...{head}",
                    "merges_url": "https://api.github.com/repos/creydr/test/merges",
                    "archive_url": "https://api.github.com/repos/creydr/test/{archive_format}{/ref}",
                    "downloads_url": "https://api.github.com/repos/creydr/test/downloads",
                    "issues_url": "https://api.github.com/repos/creydr/test/issues{/number}",
                    "pulls_url": "https://api.github.com/repos/creydr/test/pulls{/number}",
                    "milestones_url": "https://api.github.com/repos/creydr/test/milestones{/number}",
                    "notifications_url": "https://api.github.com/repos/creydr/test/notifications{?since,all,participating}",
                    "labels_url": "https://api.github.com/repos/creydr/test/labels{/name}",
                    "releases_url": "https://api.github.com/repos/creydr/test/releases{/id}",
                    "created_at": "2023-09-12T14:09:28Z",
                    "updated_at": "2023-09-12T14:09:29Z",
                    "pushed_at": "2023-09-12T14:09:29Z",
                    "git_url": "git://github.com/creydr/test.git",
                    "ssh_url": "git@github.com:creydr/test.git",
                    "clone_url": "https://github.com/creydr/test.git",
                    "svn_url": "https://github.com/creydr/test",
                    "homepage": null,
                    "size": 0,
                    "stargazers_count": 0,
                    "watchers_count": 0,
                    "language": null,
                    "has_issues": true,
                    "has_downloads": true,
                    "has_wiki": true,
                    "has_pages": false,
                    "forks_count": 0,
                    "mirror_url": null,
                    "open_issues_count": 1,
                    "forks": 0,
                    "open_issues": 1,
                    "watchers": 0,
                    "default_branch": "main"
                  },
                  "sender": {
                    "login": "creydr",
                    "id": 8654480,
                    "node_id": "MDQ6VXNlcjg2NTQ0ODA=",
                    "avatar_url": "https://avatars.githubusercontent.com/u/8654480?v=4",
                    "gravatar_id": "",
                    "url": "https://api.github.com/users/creydr",
                    "html_url": "https://github.com/creydr",
                    "followers_url": "https://api.github.com/users/creydr/followers",
                    "following_url": "https://api.github.com/users/creydr/following{/other_user}",
                    "gists_url": "https://api.github.com/users/creydr/gists{/gist_id}",
                    "starred_url": "https://api.github.com/users/creydr/starred{/owner}{/repo}",
                    "subscriptions_url": "https://api.github.com/users/creydr/subscriptions",
                    "organizations_url": "https://api.github.com/users/creydr/orgs",
                    "repos_url": "https://api.github.com/users/creydr/repos",
                    "events_url": "https://api.github.com/users/creydr/events{/privacy}",
                    "received_events_url": "https://api.github.com/users/creydr/received_events",
                    "type": "User",
                    "site_admin": false
                  }
                }
                        """;

        RestAssured.given().contentType("application/json")
                .body(requestData)
                .header("ce-id", "42")
                .header("ce-specversion", "1.0")
                .header("ce-type", "dev.knative.source.github.issue_comment")
                .header("ce-source", "https://github.com/creydr/test")
                .post("/")
                .then().statusCode(200)
                .header("ce-type", "classification.positive");
        //.body(equalTo(requestData))
        //.header("ce-type", "classification.positive");
    }


}
