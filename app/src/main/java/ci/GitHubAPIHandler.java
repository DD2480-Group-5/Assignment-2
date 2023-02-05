package ci;

import java.io.IOException;

import okhttp3.*;

public class GitHubAPIHandler {
    public enum STATE {
        INIT,
        PENDING,
        FAILURE,
        SUCCESS
    }

    private static final String apiKey = System.getenv("GITHUB_TOKEN");

    private String repository;
    private STATE state = STATE.PENDING;
    private String owner;

    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private static final OkHttpClient client = new OkHttpClient();

    /**
     * 
     * @param owner      The account owner of the repository. The name is not case
     *                   sensitive.
     * @param repository The name of the repository. The name is not case sensitive.
     */
    public GitHubAPIHandler(String repository, String owner) {
        this.repository = repository;
        this.owner = owner;
    }

    /**
     * Starts a check run
     * 
     * @param name        The name of the check. For example, "code-coverage".
     * @param headSha     The SHA of the commit.
     * @param description A short description of the status.
     * @param targetUrl   The target URL to associate with this status.
     * @return The response or {@code null} if there was an error
     */
    public Response setStatus(String name, String headSha, String description, String targetUrl) {
        String requestJson = "{\"state\": \"" + state + "\",\"target_url\": \"" + targetUrl + "\",\"description\": \""
                + description + "\",\"context\": \"default\"}";
        RequestBody body = RequestBody.create(requestJson, JSON);
        Request request = new Request.Builder()
                .url("https://api.github.com/repos/" + repository + "/statuses/" + headSha)
                .header("Authorization", owner + " " + apiKey)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new Exception("Could not create commit status. Response code: " + response.code());
            }
            return response;
        } catch (IOException e) {
            return null;
        } catch (Exception e) {
            System.err.print(e.getMessage());
            return null;
        }
    }

    public void setState(STATE state) throws Exception {
        if (state == STATE.FAILURE || state == STATE.SUCCESS ||
                state == STATE.PENDING) {
            this.state = state;
        } else {
            throw new Exception("Wrong status.");
        }
    }

    public STATE getState() {
        return this.state;
    }

    /**
     * Updates a check run
     * 
     * @param checkRunId The unique identifier of the check run.
     * @param status     Can be one of: {@code "queued"},
     *                   {@code "in_progress"} ,{@code "completed"}
     * @param conclusion Can be one of: {@code "action_required"},
     *                   {@code "failure"}, {@code "success"},
     *                   {@code "skipped"}, {@code "stale"}, {@code "timed_out"}.
     */
    public void updateCheck(String checkRunId, String status, String conclusion) {

        return;
    }
}
