package hu.progmasters.vizsgaremek.domain;

import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SoftAssertionsExtension.class)
public class EmployeeTest {

    @Test
    void TestEmployeeNoArgs() {
        Employee employee = new Employee();
        assertThat(employee.getId()).isEqualTo(0);
        assertThat(employee.getName()).isEqualTo(null);
        assertThat(employee.getBookedHours()).isEqualTo(0.0);
        assertThat(employee.getWorkedDays()).isEqualTo(0);
        assertThat(employee.isActive()).isEqualTo(true);
        Employee another = new Employee();
        assertThat(another).isEqualTo(employee);
    }
}
