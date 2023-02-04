package ci;

import okhttp3.*;

public class Status {

    private static String repository = null;
    private static String sha = null;
    private static String state = null;
    private static String targetUrl = null;
    private static String description = null;

    private static String user = null;

    private static enum Possible_state {
        PENDING,
        FAILURE,
        SUCCESS
    }

    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private static final OkHttpClient client = new OkHttpClient();

    public Status(String repository, String sha, String state, String targetUrl, String description, String user) {
        Status.repository = repository;
        Status.sha = sha;
        Status.state = state;
        Status.targetUrl = targetUrl;
        Status.description = description;
        Status.user = user;
    }


    public void setStatus(String state) throws Exception {
        if (state.equals(Possible_state.FAILURE) || state.equals(Possible_state.SUCCESS) ||
                state.equals(Possible_state.PENDING)) {
            Status.state = state;
        } else {
            throw new Exception("Wrong status");
        }
    }

    public void createCommitStatus() throws Exception {
        String requestJson = "{\"state\": \"" + state + "\",\"target_url\": \"" + targetUrl + "\",\"description\": \"" + description + "\",\"context\": \"default\"}";
        RequestBody body = RequestBody.create(JSON, requestJson);
        Request request = new Request.Builder()
                .url("https://api.github.com/repos/" + repository + "/statuses/" + sha)
                .header("Authorization", user + " " + System.getenv("GITHUB_TOKEN"))
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new Exception("Could not create commit status. Response code: " + response.code());
            }
        }
    }
}
