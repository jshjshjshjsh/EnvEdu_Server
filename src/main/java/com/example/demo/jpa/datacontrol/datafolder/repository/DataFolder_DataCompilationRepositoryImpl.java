package com.example.demo.jpa.datacontrol.datafolder.repository;

import com.example.demo.jpa.datacontrol.datafolder.model.DataFolder_DataCompilation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class DataFolder_DataCompilationRepositoryImpl implements DataFolder_DataCompilationRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public void saveDataFolder_DataCompilation(DataFolder_DataCompilation dataFolderDataCompilation) {
        em.persist(dataFolderDataCompilation);
    }

    @Override
    public List<DataFolder_DataCompilation> findByDataFolderId(Long id) {
        return em.createQuery("SELECT t FROM DataFolder_DataCompilation t WHERE t.dataFolder.id = :id", DataFolder_DataCompilation.class)
                .setParameter("id", id).getResultList();
    }

    @Override
    public DataFolder_DataCompilation findById(Long id) {
        return em.createQuery("SELECT t FROM DataFolder_DataCompilation t WHERE t.id = :id", DataFolder_DataCompilation.class)
                .setParameter("id", id).getSingleResult();
    }

    @Override
    public void save(DataFolder_DataCompilation dataFolderDataCompilation) {
        em.persist(dataFolderDataCompilation);
        em.clear();
        em.flush();
    }

    @Override
    public void deleteAllById(List<Long> id) {
        for (Long i : id)
            em.createQuery("DELETE FROM DataFolder_DataCompilation t WHERE t.id = : id")
                    .setParameter("id", i)
                    .executeUpdate();
        em.clear();
        em.flush();
    }
}
