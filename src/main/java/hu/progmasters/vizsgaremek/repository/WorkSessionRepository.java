package hu.progmasters.vizsgaremek.repository;

import hu.progmasters.vizsgaremek.domain.WorkSession;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class WorkSessionRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public WorkSession save(WorkSession toSave) {
        entityManager.persist(toSave);
        return toSave;
    }

    public List<WorkSession> findByEmployeeId(Integer employeeId) {
        return entityManager.createQuery(
                        "SELECT w FROM WorkSession w WHERE w.employeeId = (?1)", WorkSession.class)
                .setParameter(1, employeeId).getResultList();
    }

    public List<WorkSession> findByProjectId(Integer projectId) {
        return entityManager.createQuery(
                        "SELECT w FROM WorkSession w WHERE w.projectId = (?1)", WorkSession.class)
                .setParameter(1, projectId).getResultList();
    }


    public Optional<WorkSession> findById(Long id) {
        WorkSession found = entityManager.find(WorkSession.class, id);
        return found != null ? Optional.of(found) : Optional.empty();
    }

    public WorkSession update(WorkSession toUpdate) {
        entityManager.merge(toUpdate);
        return toUpdate;
    }

    public List<WorkSession> findAll() {
        return entityManager
                .createQuery("SELECT w FROM WorkSession w", WorkSession.class)
                .getResultList();
    }

    public void delete(WorkSession workSession) {
        entityManager.remove(workSession);
    }
}
