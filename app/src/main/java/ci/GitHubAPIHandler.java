package ci;

import java.io.IOException;

import okhttp3.*;

/**
 * This class is used to communicate with the GitHub REST API.
 */
public class GitHubAPIHandler {
    public enum STATE {
        INIT,
        PENDING,
        FAILURE,
        SUCCESS
    }

    /**
     * Personal GitHub token, must be set as an environment variable by the name of
     * {@code"GITHUB_TOKEN"}
     */
    private static final String apiKey = "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";

    private String repository;
    private STATE state = STATE.PENDING;
    private String owner;

    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private static final OkHttpClient client = new OkHttpClient();

    /**
     * Constructor
     * `
     * @param owner      The account owner of the repository. The name is not case
     *                   sensitive.
     * @param repository The name of the repository. The name is not case sensitive.
     */
    public GitHubAPIHandler(String repository, String owner) {
        this.repository = repository;
        this.owner = owner;
    }

    /**
     * Sets the commit status for a commit with SHA {@code headSha}.
     * 
     * @param headSha     The SHA of the commit.
     * @param description A short description of the status.
     * @param targetUrl   The target URL to associate with this status. Will be
     *                    linked from the GitHub UI.
     * @return The response or {@code null} if there was an error
     */
    public Response setStatus(String headSha, String description, String targetUrl) {
        String requestJson = "{\"state\": \"" + state.toString().toLowerCase() + "\",\"target_url\": \"" + targetUrl + "\",\"description\": \""
                + description + "\",\"context\": \"default\"}";
        RequestBody body = RequestBody.create(requestJson, JSON);
        Request request = new Request.Builder()
                .url("https://api.github.com/repos/" + owner + "/" + repository + "/statuses/" + headSha)
                .header("Authorization", "Token " + apiKey)
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

    /**
     * Sets the state of the handler to {@code state}
     * 
     * @param state The state to be set, can be either {@code FAILURE},
     *              {@code SUCCESS} or {@code PENDING}. Otherwise an exception is
     *              thrown.
     * @throws Exception
     */
    public void setState(STATE state) throws Exception {
        if (state == STATE.FAILURE || state == STATE.SUCCESS ||
                state == STATE.PENDING) {
            this.state = state;
        } else {
            throw new Exception("Wrong status.");
        }
    }

    /**
     * Getter method for the {@code state} class variable.
     * 
     * @return Current state
     */
    public STATE getState() {
        return this.state;
    }
}
