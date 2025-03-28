package com.metacoding.storev2.store;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class StoreRepository {

    private final EntityManager em;

    public List<StoreResponse.ListDTO> findAll() {
        Query query = em.createNativeQuery("select id, name from store_tb order by id");
        List<Object[]> obsList = query.getResultList();

        List<StoreResponse.ListDTO> listDTOs = new ArrayList<>();

        for (Object[] obs : obsList) {
            StoreResponse.ListDTO dto = new StoreResponse.ListDTO(
                    (int) obs[0],
                    (String) obs[1]
            );
            listDTOs.add(dto);
        }
        return listDTOs;

    }

    public void save(String name, Integer stock, Integer price) {
        Query query = em.createNativeQuery("insert into store_tb(name, stock, price) values (?, ?, ?)");
        query.setParameter(1, name);
        query.setParameter(2, stock);
        query.setParameter(3, price);
        query.executeUpdate();
    }

    public Store findById(int id) {
        Query query = em.createNativeQuery("select * from store_tb where id = ?", Store.class);
        query.setParameter(1, id);
        try {
            return (Store) query.getSingleResult();
        } catch (Exception ex) {
            return null;
        }
    }

    public void deleteById(int id) {
        Query query = em.createNativeQuery("delete from store_tb where id = ?");
        query.setParameter(1, id);
        query.executeUpdate();
    }
}
