package ci;

import ci.GitHubAPIHandler.STATE;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;

public class Repository {
    public enum BUILD_STATE {
        INIT,
        SUCCESS,
        COMPILE_ERROR,
        TEST_ERROR
    };

    private String id;
    private String name;
    private String url;
    private String branch;
    private String buildOutput = "";
    /* buildState variable used for test */
    private BUILD_STATE buildState = BUILD_STATE.INIT;
    private Path directory;
    private GitHubAPIHandler handler;
    // private Status status;

    /**
     * Constructor of the Repository class.
     *
     * @param id     : commitID of the git commit
     * @param name   : name of the repository
     * @param url    : ssh-url of the repository
     * @param branch : branch name of the repository
     * @param user   : user name of the repository owner
     */
    public Repository(String id, String name, String url, String branch, String user) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.branch = branch;
        this.handler = new GitHubAPIHandler(name, user);
        try {
            this.directory = Files.createTempDirectory(this.name + "-");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Return the name of the temporary directory of the cloned repository.
     * @return Directory in String.
     */
    public String getDirName() {
        return this.directory.toAbsolutePath().toString();
    }

    /**
     * Return the build state of the repository.
     * @return INIT, SUCCESS, COMPILE_ERROR or TEST_ERROR.
     */
    public BUILD_STATE getBuildState() {
        return this.buildState;
    }

    /**
     * This function should build the repository and return the status of the build
     * See issue {@link https://github.com/DD2480-Group-5/Assignment-2/issues/3}.
     */
    public STATE buildRepository() {
        try {
            /* get payload from HTTP request and create JSON object */
            System.out.println("Running building process");
            System.out.println("Begin to clone remote repository.");
            cloneRepository();

            String dirName = this.directory.toAbsolutePath().toString();
            File projectDir = new File(dirName);
            String buildCommand = "cmd /c gradle build";

            // update status -> PENDING
            this.handler.setState(STATE.PENDING);

            // begin build progress
            System.out.println("Begin to build.");

            Process p = Runtime.getRuntime().exec(buildCommand, null, projectDir);
            p.waitFor();

            /* get build output */
            StringBuilder output = new StringBuilder();
            String line = "";
            BufferedReader bf = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((line = bf.readLine()) != null) {
                System.out.println(line);
                output.append(line).append('\n');
            }
            this.buildOutput = output.toString();

            int exitCode = p.exitValue();

            // exit code 0 is success by convention
            if (exitCode == 0) {
                System.out.println("Build Successful!");
                this.buildState = BUILD_STATE.SUCCESS;
                this.handler.setState(STATE.SUCCESS);
            } else {
                if (buildOutput.contains("Task :app:compileJava FAILED") || 
                    buildOutput.contains("Task :app:compileTestJava FAILED")) {
                    this.buildState = BUILD_STATE.COMPILE_ERROR;
                    System.out.println("Build Failed: Compilation Failed.");
                }
                if (buildOutput.contains("Task :app:test FAILED")) {
                    this.buildState = BUILD_STATE.TEST_ERROR;
                    System.out.println("Build Failed: Test Failed.");
                }
                this.handler.setState(STATE.FAILURE);
            }

            return this.handler.getState();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This function should use the class variables to clone the repository
     * See issue {@link https://github.com/DD2480-Group-5/Assignment-2/issues/1}.
     */
    public void cloneRepository() {
        try {
            String cloneCommand = "git clone --branch " + this.branch + " " + this.url + " " + this.directory;
            String checkoutCommand = "git checkout " + this.id;
            String dirName = this.directory.toAbsolutePath().toString();

            System.out.println("Cloning the repo to a temporary directory...");
            Process process = Runtime.getRuntime().exec(cloneCommand);
            process.waitFor();
            System.out.println("Clone task done!");

            process = Runtime.getRuntime().exec(checkoutCommand, null, new File(dirName));
            process.waitFor();
            System.out.println("Checkout commit ID " + this.id);

        } catch (IOException | InterruptedException e) {
            System.out.println("Clone task failed.");
        }
    }

    /**
     * This function delete the temporary directory used to store the cloned repository.
     */
    public void deleteRepository() {
        File dir = new File(this.directory.toAbsolutePath().toString());
        delete(dir);
        System.out.println("Remove the repository successfully.");
    }

    /**
     * This is a helper function to deleteRepository(). It deletes the directory recursively.
     *
     * @param dir : File object of the directory.
     */
    private static void delete(File dir) {
        if (dir.isDirectory()) {
            File[] files = dir.listFiles();
            if (files != null) {
                for (File file : files) {
                    delete(file);
                }
            }
        }
        dir.delete();
    }
}
