package hu.progmasters.vizsgaremek.repository;

import hu.progmasters.vizsgaremek.domain.WorkSession;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class WorkSessionRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public WorkSession saveWorkSession(WorkSession toSave) {
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


}
