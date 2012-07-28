package maven.code.generator;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * @goal generate
 */
public class CodeGeneratorPlugin extends AbstractMojo {

    /**
     * Output directory for generated source files.
     *
     * @parameter expression="${project.build.directory}/generated-sources/mavencodegen"
     */
    private File outputDirectory;

    public void execute() throws MojoExecutionException {
        if(!outputDirectory.exists()) {
            outputDirectory.mkdirs();
        }
        File testFile = new File(outputDirectory, "test.txt");
        try {
            PrintWriter testOut = new PrintWriter(testFile);
            testOut.println("Hello World.");
            testOut.close();
        } catch (FileNotFoundException e) {
            throw new MojoExecutionException("Can not write to " + testFile.getAbsolutePath(), e);
        }
    }
}
