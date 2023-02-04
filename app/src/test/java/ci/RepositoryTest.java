package ci;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

class RepositoryTest {
    
    @Test
    /**
     * This test is used to test a repository can be cloned successfully.
     */
    public void gitCloneTest() {
        String id = "946b7a856c224cfa42b4d1ad185db10759954a5f";
        String name = "test";
        String url = "git@github.com:LMC117/test.git";
        String branch = "main";

        Repository repo = new Repository(id, name, url, branch);
        repo.cloneRepository();
        
        String dirName = repo.getDirName();
        File dir = new File(dirName).listFiles(File::isDirectory)[0];
        System.out.println(dir);

        assertTrue(dir.exists() && dir.isDirectory());

        repo.deleteRepository();
    }
}
