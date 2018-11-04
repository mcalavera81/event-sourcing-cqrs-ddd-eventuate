import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test

class IntegrationTestsPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        project.sourceSets {
            integrationTest {
                java {
                    compileClasspath += main.output + test.output
                    runtimeClasspath += main.output + test.output
                    srcDir project.file('src/integration-test/java')
                }
                resources.srcDir project.file('src/integration-test/resources')
            }
        }

        project.configurations {
            integrationTestCompile.extendsFrom testCompile
            integrationTestRuntime.extendsFrom testRuntime
        }

        project.task("integrationTest", type: Test) {
            testClassesDir = project.sourceSets.integrationTest.output.classesDir
            classpath = project.sourceSets.integrationTest.runtimeClasspath

            /*Forces Gradle to always run the integration tests when asked to.
            By default, Gradle attempts to optimize task execution by not re-running
            tasks whose inputs have not changed. Since integration tests may fail
            due to external systems, we want to run them even if no code has changed.*/
            outputs.upToDateWhen { false }

            /*
            Enforces task ordering, not task dependency. Unit tests run fast, so
            we want to avoid running integration tests if unit tests fail.
            However, we use mustRunAfter test rather than dependsOn test because
            we do not always want to run unit tests when we run integration tests.*/
            mustRunAfter project.tasks.test
        }

        /*Ensure that the check task fails the build if there are failing
        component    tests.*/
        project.tasks.check.dependsOn project.tasks.integrationTest

        project.tasks.withType(Test) {
            reports.html.destination = project.file("${project.reporting.baseDir}/${name}")
        }
    }
}
