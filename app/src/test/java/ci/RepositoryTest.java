package ci;

import org.junit.jupiter.api.Test;

import java.io.File;

import static ci.Status.Possible_state;
import static ci.Status.Possible_state.SUCCESS;
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
        File dir = new File(dirName).listFiles(File::isDirectory)[0];
        System.out.println(dir);

        assertTrue(dir.exists() && dir.isDirectory());

        repo.deleteRepository();
    }

    @Test
    /**
     * This test is used to test the build result. 
     */
    public void buildRepoTest() {
        String id = "946b7a856c224cfa42b4d1ad185db10759954a5f";
        String name = "My-first-Git";
        String url = "git@github.com:BillXu0424/My-first-Git.git";
        String branch = "main";
        String user = "BillXu0424";

        Repository repo = new Repository(id, name, url, branch, user);
        Possible_state status = repo.buildRepository();

        System.out.println(status);
        assertSame(SUCCESS, status);

        repo.deleteRepository();
    }
}
