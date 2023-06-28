package com.example.demo.openapi.repository;

import com.example.demo.openapi.model.entity.AirQuality;
import com.example.demo.openapi.model.entity.OceanQuality;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OpenApiRepositoryImpl implements OpenApiRepository{

    @PersistenceContext
    private final EntityManager em;

    @Override
    public List<AirQuality> findAirQualityAllByUserId(Long id) {
        //return em.createQuery("SELECT t FROM AirQuality t", AirQuality.class).getResultList();
        return em.createQuery("SELECT t FROM AirQuality t WHERE t.owner.id = :id", AirQuality.class).setParameter("id", id).getResultList();
    }

    @Override
    public List<OceanQuality> findOceanQualityAllByUserId(Long id) {
        //return em.createQuery("SELECT t FROM OceanQuality t", OceanQuality.class).getResultList();
        return em.createQuery("SELECT t FROM OceanQuality t WHERE t.owner.id = :id", OceanQuality.class).setParameter("id", id).getResultList();
    }

    @Override
    public boolean saveAirQuality(List<AirQuality> airQualities) {

        try {
            for (AirQuality airQuality : airQualities) {
                em.persist(airQuality);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean saveOceanQuality(List<OceanQuality> oceanQualities) {

        try {
            for (OceanQuality oceanQuality : oceanQualities) {
                em.persist(oceanQuality);
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
