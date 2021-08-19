package hu.progmasters.vizsgaremek.domain;

import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SoftAssertionsExtension.class)
public class ProjectTest {

    @Test
    void TestProjectNoArgs() {
        Project project = new Project();
        assertThat(project.getId()).isEqualTo(0);
        assertThat(project.getName()).isEqualTo(null);
        assertThat(project.getDescription()).isEqualTo(null);
        assertThat(project.getLeader()).isEqualTo(null);
        assertThat(project.isActive()).isEqualTo(true);
        Project another = new Project();
        assertThat(another).isEqualTo(project);
    }

}
