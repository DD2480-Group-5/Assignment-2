package ci;

import org.junit.jupiter.api.Test;

import ci.GitHubAPIHandler.STATE;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertFalse;
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
     * This test is used to test the build result. 
     */
    public void buildRepoTest() {
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
}
