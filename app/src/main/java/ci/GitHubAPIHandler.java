package ci;

public class GitHubAPIHandler {
    private static final String apiKey = System.getenv("GITHUB_TOKEN");

    private String owner;
    private String repo;

    /**
     * 
     * @param owner The account owner of the repository. The name is not case
     *              sensitive.
     * @param repo  The name of the repository. The name is not case sensitive.
     */
    public GitHubAPIHandler(String owner, String repo) {
        this.owner = owner;
        this.repo = repo;
    }

    /**
     * Starts a check run
     * 
     * @param name    The name of the check. For example, "code-coverage".
     * @param headSha The SHA of the commit.
     */
    public void runCheck(String name, String headSha) {

        return;
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
