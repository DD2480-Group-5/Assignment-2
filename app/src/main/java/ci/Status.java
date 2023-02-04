package ci;

import okhttp3.*;

public class Status {

    private String repository = null;
    private String sha = null;
    private Possible_state state = null;
    private String targetUrl = null;
    private String description = null;
    private String user = null;

    public enum Possible_state {
        INIT,
        PENDING,
        FAILURE,
        SUCCESS
    }

    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private static final OkHttpClient client = new OkHttpClient();


    public Status(String repository, String sha, Possible_state state, String targetUrl, String description, String user) {
        this.repository = repository;
        this.sha = sha;
        this.state = state;
        this.targetUrl = targetUrl;
        this.description = description;
        this.user = user;
    }

    public Possible_state getState() {
        return state;
    }

    /**
     * Depreceated, see {@code GitHubAPIHandler.setState}
     * 
     * @throws Exception
     */
    @Deprecated
    public void setStatus(Possible_state state) throws Exception {
        if (state == Possible_state.FAILURE || state == Possible_state.SUCCESS ||
                state == Possible_state.PENDING) {
            this.state = state;
        } else {
            throw new Exception("Wrong status.");
        }
    }

    /**
     * Depreceated, see {@code GitHubAPIHandler.runCheck}
     * 
     * @throws Exception
     */
    @Deprecated
    public Response createCommitStatus() throws Exception {
        String requestJson = "{\"state\": \"" + state + "\",\"target_url\": \"" + targetUrl + "\",\"description\": \""
                + description + "\",\"context\": \"default\"}";
        RequestBody body = RequestBody.create(requestJson, JSON);
        Request request = new Request.Builder()
                .url("https://api.github.com/repos/" + repository + "/statuses/" + sha)
                .header("Authorization", user + " " + System.getenv("GITHUB_TOKEN"))
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new Exception("Could not create commit status. Response code: " + response.code());
            }
            return response;
        }
    }
}
