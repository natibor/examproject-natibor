package hu.progmasters.vizsgaremek.repository;

import hu.progmasters.vizsgaremek.domain.Project;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class ProjectRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public Project save(Project toSave) {
        entityManager.persist(toSave);
        return toSave;
    }

    public Optional<Project> findById(Integer id) {
        Project found = entityManager.find(Project.class, id);
        return found != null ? Optional.of(found) : Optional.empty();
    }

    public Project update(Project toUpdate) {
        entityManager.merge(toUpdate);
        return toUpdate;
    }

    public List<Project> findAll() {
        return entityManager
                .createQuery("SELECT p FROM Project p WHERE p.inProgress = true", Project.class)
                .getResultList();
    }
}
