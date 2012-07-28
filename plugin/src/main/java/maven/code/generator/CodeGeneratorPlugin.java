package maven.code.generator;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

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

        Configuration fmConfig = new Configuration();
        fmConfig.setClassForTemplateLoading(CodeGeneratorPlugin.class, "/templates");

        Map<String, Object> data = new HashMap<String, Object>();
        data.put("message", "Hello World.");

        try {
            Template template = fmConfig.getTemplate("HelloWorld.java.ftl");
            File testFile = new File(outputDirectory, "HelloWorld.java");
            Writer out = new OutputStreamWriter(new FileOutputStream(testFile));
            template.process(data, out);
        } catch (IOException e) {
            throw new MojoExecutionException("Can not generate code in target location", e);
        } catch (TemplateException e) {
            throw new MojoExecutionException("Can not process template", e);
        }
    }
}
