package ci;

import okhttp3.*;

public class Status {

    private static String repository = null;
    private static String sha = null;
    private static Possible_state state = null;
    private static String targetUrl = null;
    private static String description = null;

    private static String user = null;

    public enum Possible_state {
        INIT,
        PENDING,
        FAILURE,
        SUCCESS
    }

    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private static final OkHttpClient client = new OkHttpClient();

    public Status(String repository, String sha, Possible_state state, String targetUrl, String description,
            String user) {
        Status.repository = repository;
        Status.sha = sha;
        Status.state = state;
        Status.targetUrl = targetUrl;
        Status.description = description;
        Status.user = user;
    }

    public static Possible_state getState() {
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
            Status.state = state;
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
