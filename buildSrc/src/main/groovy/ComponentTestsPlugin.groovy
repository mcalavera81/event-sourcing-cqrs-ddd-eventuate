import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.internal.impldep.aQute.libg.generics.Create
import org.gradle.internal.impldep.org.bouncycastle.jcajce.provider.symmetric.IDEA

class ComponentTestsPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
    
    	project.apply(plugin: 'eclipse')
    	
        project.sourceSets {
            //Create a new source set called componentTest.
            /* A SourceSet represents a set of Java source files and
            miscellaneous resources, the directories for which we need to configure
            relative to the working directory of the sub-project. */
            componentTest {
                java {
                    /*
                    Provides, at compile-time, the classes produced by the main
                    and test SourceSets, allowing the component tests to access
                    the production code in main and allowing them to reuse any
                    unit test helper methods in test.
                     */
                    compileClasspath += main.output + test.output

                    /*Ensure that the output of the main and test source sets is
                    added to the runtime classpath.*/
                    runtimeClasspath += main.output + test.output

                    /*Set the source directory of our component tests to
                    src/component-test/java.*/
                    srcDir project.file('src/component-test/java')
                }
                /*Set the resource directory of our integration tests to
                src/component-test/resources*/
                resources.srcDir project.file('src/component-test/resources')
            }
        }

        /*Configures the compile and runtime configurations for our
        integration tests. The problem is that these configurations do not contain
        the dependencies of our unit tests. */
        project.configurations {
            /*
            The componentTestCompile configuration is used to declare the dependencies
            that are required when our component tests are compiled.
            Ensure that the componentTestCompile configuration contains the dependencies
            that are required to compile our unit tests.*/
            componentTestCompile.extendsFrom testCompile
            /*
            The componentTestRuntime configuration is used to declare the dependencies
            that are required to run our component tests.
            Ensure that the componentTestRuntime configuration contains the dependencies
            that are required to run our unit tests.*/
            componentTestRuntime.extendsFrom testRuntime
        }

		project.eclipse.classpath.plusConfigurations << project.configurations.componentTestCompile

        /* Create the task that runs the component tests found from the
           configured source directory and uses the correct classpath.*/
        project.task("componentTest", type: Test) {
            /* Describes this task for reports and user interfaces such as
            when running ./gradlew tasks.*/
            description = 'Runs the component tests.'
            //Sets the code to test as the compiled code from the integrationTest source set.
            testClassesDir = project.sourceSets.componentTest.output.classesDir
            // Sets the runtime classpath to be as defined in the integrationTest source set.
            classpath = project.sourceSets.componentTest.runtimeClasspath

            mustRunAfter project.tasks.test

        }


        /* Ensure that our integration tests are run before the check task
        and that the check task fails the build if there are failing
        component tests.*/
        project.tasks.check.dependsOn project.tasks.componentTest


        project.tasks.withType(Test) {
            reports.html.destination = project.file("${project.reporting.baseDir}/${name}")
        }
    }
}
