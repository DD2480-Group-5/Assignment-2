package ci;

import org.junit.jupiter.api.Test;

import ci.GitHubAPIHandler.STATE;
import ci.Repository.BUILD_STATE;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RepositoryTest {
    
    @Test
    /**
     * This test is used to test a repository can be cloned successfully.
     */
    public void gitCloneTest() {
        String id = "946b7a856c224cfa42b4d1ad185db10759954a5f";
        String name = "My-first-Git";
        String url = "git@github.com:BillXu0424/My-first-Git.git";
        String branch = "main";
        String user = "BillXu0424";

        Repository repo = new Repository(id, name, url, branch, user);
        repo.cloneRepository();
        
        String dirName = repo.getDirName();
        File dir = new File(dirName);
        System.out.println(dir);

        assertTrue(dir.exists() && dir.isDirectory());

        repo.deleteRepository();
    }

    @Test
    /**
     * This test is used to test the build result for success. 
     */
    public void buildRepoSuccessTest() {
        String id = "31113e4dc5da9b2e01c9c4ee85d2c5c4e9aa96b3";
        String name = "Assignment-1";
        String url = "git@github.com:DD2480-Group-5/Assignment-1.git";
        String branch = "main";
        String user = "DD2480-Group-5";

        Repository repo = new Repository(id, name, url, branch, user);
        STATE status = repo.buildRepository();

        System.out.println(status);
        assertSame(STATE.SUCCESS, status);

        repo.deleteRepository();
    }

    @Test
    /**
     * This test is used to test the build result for compile error.
     */
    public void buildRepoCompileErrorTest() {
        String id = "c5d4507af53232f3965d5cb7614bfb263abe7e44";
        String name = "Assignment-1";
        String url = "git@github.com:DD2480-Group-5/Assignment-1.git";
        String branch = "ci_test2";
        String user = "DD2480-Group-5";
        
        Repository repo = new Repository(id, name, url, branch, user);
        repo.buildRepository();
        BUILD_STATE buildState = repo.getBuildState();

        System.out.println(buildState);
        assertSame(BUILD_STATE.COMPILE_ERROR, buildState);

        repo.deleteRepository();
    }

    @Test
    /**
     * This test is used to test the build result for test error.
     */
    public void buildRepoTestErrorTest() {
        String id = "1ec066f4a168c9d93f6ef01ecddffb3cce61cf0a";
        String name = "Assignment-2";
        String url = "git@github.com:DD2480-Group-5/Assignment-2.git";
        String branch = "assessment";
        String user = "DD2480-Group-5";

        Repository repo = new Repository(id, name, url, branch, user);
        repo.buildRepository();
        BUILD_STATE buildState = repo.getBuildState();

        System.out.println(buildState);
        assertSame(BUILD_STATE.TEST_ERROR, buildState);

        repo.deleteRepository();
    }
}
