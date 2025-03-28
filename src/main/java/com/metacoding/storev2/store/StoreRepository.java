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
}
