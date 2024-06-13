package com.example.demo.jpa.datacontrol.dataclassroom.repository;

import com.example.demo.jpa.datacontrol.dataclassroom.domain.types.ClassroomDataType;
import com.example.demo.jpa.datacontrol.dataclassroom.domain.types.ClassroomStudentGrade;
import com.example.demo.jpa.datacontrol.dataclassroom.domain.types.ClassroomSubjectType;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ClassroomObjectCriteriaQuery<T> {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public List<T> getObjectByGradeAndSubjectAndDataType(String grade,
                                                         String subject,
                                                         String dataType, Class<T> entityType) {

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();

        CriteriaQuery<T> query = builder.createQuery(entityType);
        Root<T> root = query.from(entityType);

        List<Predicate> predicates = new ArrayList<>();

        if (grade != null) {
            predicates.add(builder.equal(root.get("grade"), ClassroomStudentGrade.getByLabel(grade)));
        }

        if (subject != null) {
            predicates.add(builder.equal(root.get("subject"), ClassroomSubjectType.getByLabel(subject)));
        }

        if (dataType != null) {
            predicates.add(builder.equal(root.get("dataType"), ClassroomDataType.getByLabel(dataType)));
        }

        query.select(root).where(predicates.toArray(new Predicate[]{}));

        List<T> result = entityManager.createQuery(query).getResultList();

        return result;
    }
}