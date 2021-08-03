package hu.progmasters.vizsgaremek.repository;

import hu.progmasters.vizsgaremek.domain.Project;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class ProjectRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public Project saveProject(Project toSave) {
        entityManager.persist(toSave);
        return toSave;
    }

    public Project findById(Integer id) {
        return entityManager.find(Project.class, id);
    }

    public Project updateProject(Project toUpdate) {
        entityManager.refresh(toUpdate);
        return toUpdate;
    }

    public List<Project> findAll() {
        return entityManager.createQuery("SELECT p FROM Project p", Project.class).getResultList();
    }
}
