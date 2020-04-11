package edu.pjwstk.sri.lab2.dao;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import edu.pjwstk.sri.lab2.model.Category;
import javax.ejb.TransactionManagementType;

/**
 * DAO for Category
 */
@Startup
@Singleton
public class CategoryDao {
	
	@PersistenceContext(unitName = "sri2-persistence-unit")
	private EntityManager em;

	public void create(Category entity) {
		em.persist(entity);
	}

	public void deleteById(Long id) {
		Category entity = em.find(Category.class, id);
		if (entity != null) {
			em.remove(entity);
		}
	}

	public Category update(Category entity) {
		return em.merge(entity);
	}

@Lock(LockType.READ)
@Schedule(second="*", minute="1", hour="*", persistent=false)
@TransactionAttribute(TransactionAttributeType.NEVER)
	public List<Category> listAll() {
		TypedQuery<Category> findAllQuery = em
				.createQuery(
						"SELECT DISTINCT c FROM Category c LEFT JOIN FETCH c.parentCategory LEFT JOIN FETCH c.childCategories ORDER BY c.id",
						Category.class);
		return findAllQuery.getResultList();
	}
}


