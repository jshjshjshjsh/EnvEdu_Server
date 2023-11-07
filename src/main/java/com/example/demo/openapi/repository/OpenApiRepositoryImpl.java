package com.example.demo.openapi.repository;

import com.example.demo.openapi.model.entity.AirQuality;
import com.example.demo.openapi.model.entity.OceanQuality;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class OpenApiRepositoryImpl implements OpenApiRepository{

    @PersistenceContext
    private final EntityManager em;

    @Override
    public AirQuality findAirQualityById(Long id) {
        return em.createQuery("SELECT t FROM AirQuality t WHERE t.id = :id", AirQuality.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    @Override
    public OceanQuality findOceanQualityById(Long id) {
        return em.createQuery("SELECT t FROM OceanQuality t WHERE t.id = :id", OceanQuality.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    @Override
    public List<AirQuality> findAirQualityAllByUserIdAndDataUuid(UUID uuid, Long id) {
        return em.createQuery("SELECT t FROM AirQuality t WHERE t.owner.id = :id and t.dataUUID = :uuid", AirQuality.class)
                .setParameter("id", id)
                .setParameter("uuid", uuid)
                .getResultList();
    }

    @Override
    public List<AirQuality> findAirQualityAllByUserId(Long id) {
        //return em.createQuery("SELECT t FROM AirQuality t", AirQuality.class).getResultList();
        return em.createQuery("SELECT t FROM AirQuality t WHERE t.owner.id = :id", AirQuality.class).setParameter("id", id).getResultList();
    }

    @Override
    public List<OceanQuality> findOceanQualityAllByUserIdAndDataUuid(UUID uuid, Long id) {
        return em.createQuery("SELECT t FROM OceanQuality t WHERE t.owner.id = :id and t.dataUUID = :uuid", OceanQuality.class)
                .setParameter("id", id)
                .setParameter("uuid", uuid)
                .getResultList();
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

    @Override
    public List<AirQuality> findAllByDataTimeBetween(LocalDateTime start, LocalDateTime end) {
        //return em.createQuery("SELECT t FROM AirQuality t WHERE t.owner.id = :id", AirQuality.class).setParameter("id", id).getResultList();

        return em.createQuery("SELECT t FROM AirQuality t WHERE t.dataTime BETWEEN :start AND :end", AirQuality.class)
                .setParameter("start", start)
                .setParameter("end", end)
                .getResultList();
    }

}
