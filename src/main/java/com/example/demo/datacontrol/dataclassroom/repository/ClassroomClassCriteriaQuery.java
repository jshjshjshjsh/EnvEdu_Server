package com.example.demo.datacontrol.dataclassroom.repository;

import com.example.demo.datacontrol.dataclassroom.domain.entity.ClassroomClass;
import com.example.demo.datacontrol.dataclassroom.domain.types.ClassroomDataType;
import com.example.demo.datacontrol.dataclassroom.domain.types.ClassroomStudentGrade;
import com.example.demo.datacontrol.dataclassroom.domain.types.ClassroomSubjectType;
import lombok.RequiredArgsConstructor;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.jpa.HibernateEntityManager;
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
public class ClassroomClassCriteriaQuery<T> {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public List<T> getClassroomClasses(String grade,
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
        if (ClassroomClass.class.equals(entityType)){
            for (Object item : result) {
                if (item instanceof ClassroomClass) {
                    ClassroomClass classroom = (ClassroomClass) item;
                    classroom.updateLabels();
                }
            }
        }

        return result;
    }
}