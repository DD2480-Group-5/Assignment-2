package ci;

import org.gradle.tooling.GradleConnectionException;
import org.gradle.tooling.GradleConnector;
import org.gradle.tooling.ProjectConnection;
import org.gradle.tooling.ResultHandler;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import ci.Status.Possible_state;
import static ci.Status.Possible_state.*;

public class Repository {
    private String id;
    private String name;
    private String url;
    private String branch;
    /* temporary directory where the git clone command will be executed, need to visit next level directory to access the repository */
    private Path directory;
    private Status status;

    /**
     * Constructor of the Repository class.
     *
     * @param id     : commitID of the git commit
     * @param name   : name of the repository
     * @param url    : ssh-url of the repository
     * @param branch : branch name of the repository
     */
    public Repository(String id, String name, String url, String branch, String user) {
        // may be need a constructor which takes in a JSONObject, currently just use this one //
        this.id = id;
        this.name = name;
        this.url = url;
        this.branch = branch;
        this.status = new Status(name, id, INIT, url, this.name + " " + this.branch, user);
        try {
            this.directory = Files.createTempDirectory(this.name + "_" + this.branch + "-");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getDirName() {
        return this.directory.toAbsolutePath().toString();
    }

    /**
     * This function should build the repository and return the status of the build
     * See issue {@link https://github.com/DD2480-Group-5/Assignment-2/issues/3}.
     */
    public Possible_state buildRepository() {
        try {
            /* get payload from HTTP request and create JSON object */
            System.out.println("Running building process");
            System.out.println("Begin to clone remote repository.");
            cloneRepository();

            String dirName = this.directory.toAbsolutePath().toString();
            File projectDir = Objects.requireNonNull(new File(dirName).listFiles(File::isDirectory))[0];

            // update status -> PENDING
            status.setStatus(PENDING);
            // begin build progress
            try (ProjectConnection connection = GradleConnector.newConnector().forProjectDirectory(projectDir).connect()) {
                connection.newBuild().forTasks("build").run(new ResultHandler<Void>() {
                    // check build results
                    // update status -> SUCCESS/FAILURE
                    @Override
                    public void onComplete(Void result) {
                        System.out.println("Build successful.");
                        try {
                            status.setStatus(SUCCESS);
                            //status.createCommitStatus();
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }

                    @Override
                    public void onFailure(GradleConnectionException failure) {
                        System.out.println("Build failed: " + failure.getMessage());
                        try {
                            status.setStatus(FAILURE);
                            //status.createCommitStatus();
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
                return status.getState();
            }


//            String dirName = this.directory.toAbsolutePath().toString();
//            String buildCommand = "gradle check";
//
//            // build process
//            System.out.println("Begin to build.");
//            Process p = Runtime.getRuntime().exec(buildCommand, null, Objects.requireNonNull(new File(dirName).listFiles(File::isDirectory))[0]);
//            p.waitFor();
//            System.out.println("Build task done!");
//
//            // get build results
//            StringBuilder output = new StringBuilder();
//            String line = "";
//            BufferedReader bf = new BufferedReader(new InputStreamReader(p.getInputStream()));
//            while ((line = bf.readLine()) != null) {
//                output.append(line).append('\n');
//            }
//            String status = output.toString();
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
            String cloneCommand = "git clone --branch " + this.branch + " " + this.url;
            String checkoutCommand = "git checkout " + this.id;
            String dirName = this.directory.toAbsolutePath().toString();

            System.out.println("Cloning the repo to a temporary directory...");
            Process process = Runtime.getRuntime().exec(cloneCommand, null, new File(dirName));
            process.waitFor();
            System.out.println("Clone task done!");

            process = Runtime.getRuntime().exec(checkoutCommand, null, new File(dirName).listFiles(File::isDirectory)[0]);
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
