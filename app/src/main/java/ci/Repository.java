package ci;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Repository {
    private String id;
    private String name;
    private String url;
    private String branch;
    private Path directory;

    /**
     * Constructor of the Repository class.
     * @param id : commitID of the git commit
     * @param name : name of the repository
     * @param url : ssh-url of the repository
     * @param branch : branch name of the repository
     */
    public Repository(String id, String name, String url, String branch) {
        // may be need a constructor which takes in a JSONObject, currently just use this one //
        this.id = id;
        this.name = name;
        this.url = url;
        this.branch = branch;
        try {
            this.directory = Files.createTempDirectory(this.name + "_" + this.branch);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This function should build the repository and return the status of the build
     * See issue {@link https://github.com/DD2480-Group-5/Assignment-2/issues/3}.
     */
    public void buildRepository() {

        return;
    }

    /**
     * This function should use the class variables to clone the repository
     * See issue {@link https://github.com/DD2480-Group-5/Assignment-2/issues/1}.
     */
    public void cloneRepository() {
        try {
            String cloneCommand = "git clone --branch " + this.branch + " " + this.url + " " + this.id;
            System.out.println("Cloning the repo to a temporary directory...");
            Process process = Runtime.getRuntime().exec(cloneCommand, null, new File(this.directory.toAbsolutePath().toString()));
            process.waitFor();
            System.out.println("Clone task done!");
        } catch (IOException | InterruptedException e) {
            System.out.println("Clone task failed.");
        }
    }
}
