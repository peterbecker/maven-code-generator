package maven.code.generator;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @goal generate
 */
public class CodeGeneratorPlugin extends AbstractMojo {

    /**
     * The Maven project representing the whole build.
     *
     * @parameter expression="${project}"
     */
    private MavenProject project;

    /**
     * Output directory for generated source files.
     *
     * @parameter expression="${project.build.directory}/generated-sources/mavencodegen"
     */
    private File outputDirectory;

    /**
     * File containing the entity definition.
     *
     * @parameter expression="${project.basedir}/src/main/mcg/entities.xml"
     */
    private File entitiesFile;

    private Template entityTemplate;
    private Template daoTemplate;

    public void execute() throws MojoExecutionException {
        try {
            validateParametersAndInitialize();
            setUpTemplates();
            Entities entities = readEntities();
            createSourceFiles(entities);
        } catch (MojoExecutionException e) {
            getLog().error(e);
            throw e;
        }
        project.addCompileSourceRoot(outputDirectory.getAbsolutePath());
    }

    private void validateParametersAndInitialize() throws MojoExecutionException {
        if (!outputDirectory.exists()) {
            outputDirectory.mkdirs();
        }
        if(!entitiesFile.exists()) {
            throw new MojoExecutionException("Can not find input file " + entitiesFile.getAbsolutePath());
        }
    }

    private Entities readEntities() throws MojoExecutionException {
        Entities entities;
        try {
            JAXBContext context = JAXBContext.newInstance("maven.code.generator");
            Unmarshaller unmarshaller = context.createUnmarshaller();
            entities = (Entities) unmarshaller.unmarshal(entitiesFile);
        } catch (JAXBException e) {
            throw new MojoExecutionException("Failed to parse " + entitiesFile.getAbsolutePath());
        }
        return entities;
    }

    private void setUpTemplates() throws MojoExecutionException {
        Configuration fmConfig = new Configuration();
        fmConfig.setClassForTemplateLoading(CodeGeneratorPlugin.class, "/templates");

        try {
            entityTemplate = fmConfig.getTemplate("Entity.java.ftl");
            daoTemplate = fmConfig.getTemplate("Dao.java.ftl");
        } catch (IOException e) {
            throw new MojoExecutionException("Can not read template", e);
        }
    }

    private void createSourceFiles(Entities entities) throws MojoExecutionException {
        File packageDirectory = new File(outputDirectory, entities.getPackage().replace(".","/"));
        packageDirectory.mkdirs();

        Map<String, Object> data = new HashMap<String, Object>();
        data.put("package", entities.getPackage());

        for (EntityType entity : entities.getEntity()) {
            data.put("entity", entity);
            File entityFile = new File(packageDirectory, entity.getName() + ".java");
            processTemplate(data, entityTemplate, entityFile);
            File daoFile = new File(packageDirectory, entity.getName() + "Dao.java");
            processTemplate(data, daoTemplate, daoFile);
        }
    }

    private void processTemplate(Map<String, Object> data, Template template, File entityFile) throws MojoExecutionException {
        Writer out = null;
        try {
            out = new OutputStreamWriter(new FileOutputStream(entityFile));
            template.process(data, out);
        } catch (TemplateException e) {
            throw new MojoExecutionException("Can not process template", e);
        } catch (IOException e) {
            throw new MojoExecutionException("Can not generate code at " + entityFile.getAbsolutePath(), e);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    throw new MojoExecutionException("Failed to close output.", e);
                }
            }
        }
    }
}
