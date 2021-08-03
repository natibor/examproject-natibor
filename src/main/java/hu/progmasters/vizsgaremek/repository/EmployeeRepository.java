package hu.progmasters.vizsgaremek.repository;

import hu.progmasters.vizsgaremek.domain.Employee;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class EmployeeRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public Employee saveEmployee(Employee toSave) {
        entityManager.persist(toSave);
        return toSave;
    }

    public List<Employee> findAll() {
        return entityManager.createQuery("SELECT e FROM Employee e", Employee.class).getResultList();
    }
}
