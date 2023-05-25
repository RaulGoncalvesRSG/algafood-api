package com.algaworks.algafood.infrasctrure;

import com.algaworks.algafood.domain.repository.CustomJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import java.util.Optional;

public class CustomJpaRepositoryImpl<T, ID> extends SimpleJpaRepository<T, ID>      //Implementação padrão para todos repositórios
        implements CustomJpaRepository<T, ID> {

    @Autowired
    private EntityManager manager;

    //Criação manualmente do constutor pq da alteração da classe padrão (SimpleJpaRepository)
    public CustomJpaRepositoryImpl(JpaEntityInformation<T, ?> entityInformation,
                                   EntityManager entityManager) {
        super(entityInformation, entityManager);

        this.manager = entityManager;
    }

    @Override
    public Optional<T> buscarPrimeiro() {
        var jpql = "from " + getDomainClass().getName();

        T entity = manager.createQuery(jpql, getDomainClass())
                .setMaxResults(1)
                .getSingleResult();

        return Optional.ofNullable(entity);
    }

    @Override
    public void detach(T entity) {
        manager.detach(entity);     //Remove o gerenciamento do JPA do obj
    }
}