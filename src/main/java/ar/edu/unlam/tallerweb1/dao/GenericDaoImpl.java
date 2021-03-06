package ar.edu.unlam.tallerweb1.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Sebastian on 27/05/2017.
 */
@Repository
public abstract class GenericDaoImpl<E, K extends Serializable>
        implements GenericDao<E, K> {

    @Autowired
    private SessionFactory sessionFactory;

    protected Class<? extends E> daoType;

    public GenericDaoImpl() {
        Type t = getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) t;
        daoType = (Class) pt.getActualTypeArguments()[0];
    }

    protected Session currentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public void add(E entity) {
        currentSession().save(entity);
    }

    @Override
    public void saveOrUpdate(E entity) {
        currentSession().saveOrUpdate(entity);
    }

    @Override
    public void update(E entity) {
        currentSession().saveOrUpdate(entity);
    }

    @Override
    public void remove(E entity) {
        currentSession().delete(entity);
    }

    @Override
    public E find(K key) {
        return (E) currentSession().get(daoType, key);
    }

    @Override
    public List<E> getAll() {
        return currentSession().createCriteria(daoType).list();
    }

    @Override
    public List<E> getAllOrderBy(String field, Boolean desc) {
        if (desc) {
            return currentSession().createCriteria(daoType).addOrder(Order.desc(field)).list();
        }
        return currentSession().createCriteria(daoType).addOrder(Order.asc(field)).list();
    }

    @Override
    public List<E> getAllOrderBy(String field) {
        return currentSession().createCriteria(daoType).addOrder(Order.desc(field)).list();
    }
}
