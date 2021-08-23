package hu.progmasters.vizsgaremek.repository;

import hu.progmasters.vizsgaremek.domain.Employee;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class EmployeeRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public Employee saveEmployee(Employee toSave) {
        entityManager.persist(toSave);
        return toSave;
    }

    public Optional<Employee> findById(Integer id) {
        Employee found = entityManager.find(Employee.class, id);
        return found != null ? Optional.of(found) : Optional.empty();
    }

    public Employee updateEmployee(Employee toUpdate) {
        entityManager.merge(toUpdate);
        return toUpdate;
    }

    public List<Employee> findAll() {
        return entityManager
                .createQuery("SELECT e FROM Employee e WHERE e.active = true", Employee.class)
                .getResultList();
    }

}
