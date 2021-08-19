package hu.progmasters.vizsgaremek.domain;

import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SoftAssertionsExtension.class)
public class WorkSessionTest {

    @Test
    void TestWorkSessionNoArgs() {
        WorkSession workSession = new WorkSession();
        assertThat(workSession.getId()).isEqualTo(0);
        assertThat(workSession.getEmployeeId()).isEqualTo(0);
        assertThat(workSession.getProjectId()).isEqualTo(0);
        assertThat(workSession.getBookedHours()).isEqualTo(0.0);
        WorkSession another = new WorkSession();
        assertThat(another).isEqualTo(workSession);
    }

}
