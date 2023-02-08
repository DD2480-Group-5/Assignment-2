package ci;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class History {
    /**
     * get the history in a specific html file.
     *
     * @param commitID ID of a commit
     * @return a html with build history, return null if there is no such build
     */
    public String getHistory(String commitID) {
        String buildPath = "ciHistory/" + commitID + ".html";
        if (Files.exists(Paths.get(buildPath))) {
            StringBuilder buildHistory = new StringBuilder();
            try (BufferedReader bf = new BufferedReader(new FileReader(buildPath))) {
                while (bf.readLine() != null) {
                    buildHistory.append(bf.readLine()).append("\n");
                }
            } catch (IOException e) {
                return null;
            }

            return buildHistory.toString();

        } else {
            System.out.println("No such file.");
            return null;
        }
    }

    /**
     * 
     * @param commitID : commit SHA of the commit
     * @param status : commit status
     * @param message : build message
     * @param timestamp : timestamp of the commit
     * @param commitURL : commit URL on GitHub
     * @param committer : committer of the commit
     * @param commitInfo : Information of the commit
     * @param save : Boolean. If true, save this commit in html file.
     * @return Boolean. If true, the program is successfully exit.
     * @throws IOException
     */
    public boolean saveBuild(String commitID, String status, String message, String timestamp, String commitURL,
                             String committer, String commitInfo, boolean save) throws IOException {
        String commitInfo_copy = commitInfo == null ? "Null" : commitInfo;

        // read the template.html
        File directory = new File("");

        String templatePath = directory.getAbsolutePath() + "\\ciHistory\\template.html";
        BufferedReader br = new BufferedReader(new FileReader(templatePath));
        String template = br.lines().collect(Collectors.joining("\n"));
        br.close();

        // replace tag
        template = template.replaceAll("\\{\\{commitID}}", commitID)
                .replaceAll("\\{\\{status}}", status)
                .replaceAll("\\{\\{message}}", message)
                .replaceAll("\\{\\{timestamp}}", timestamp)
                .replaceAll("\\{\\{commitURL}}", commitURL)
                .replaceAll("\\{\\{committer}}", committer)
                .replaceAll("\\{\\{commitInfo}}", commitInfo_copy);

        // save the html
        if (save) {
            File buildPath = new File(directory.getAbsolutePath() + "\\ciHistory\\" + commitID + ".html");
            FileWriter fw = new FileWriter(buildPath);
            fw.write(template);
            fw.close();
        }


        // save this build to allBuild.html
        String allBuildPath = directory.getAbsolutePath() + "\\ciHistory\\allBuilds.html";
        if (Files.exists(Paths.get(allBuildPath))) {
            StringBuilder lines = new StringBuilder();
            String line;
            BufferedReader bf = new BufferedReader(new FileReader(allBuildPath));

            while ((line = bf.readLine()) != null) {
                lines.append(line);
            }
            bf.close();

            String allBuilds = lines.toString();
            allBuilds = allBuilds.substring(0, allBuilds.length() - 14);

            allBuilds += '\n' + "<h2>Build History of commit " + commitID + "</h2>\n\n" +
                    "<p><b>Commit ID</b>: " + commitID + "</p>\n" +
                    "<p><b>Commit Time</b>: " + timestamp + "</p>\n" +
                    "<p><b>Commit URL</b>: <a href=\"" + commitURL + "\">" + commitURL + "</a></p>\n" +
                    "<p><b>Committer</b>: " + committer + "</p>\n" +
                    "<p><b>Message</b>: " + message + "</p>\n" +
                    "<p><b>Status</b>: " + status + "</p>\n" +
                    "<p><b>Commit Info</b>: " + commitInfo_copy + "</p>\n" +
                    "</body>\n" +
                    "</html>";

            // save the renewed allBuilds.html
            if (save) {
                File allBuildFile = new File(allBuildPath);
                FileWriter allBuilds_fw = new FileWriter(allBuildFile);
                allBuilds_fw.write(allBuilds);
                allBuilds_fw.close();
            }
        }
        return true;
    }
}
