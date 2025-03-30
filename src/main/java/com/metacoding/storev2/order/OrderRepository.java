package com.metacoding.storev2.order;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class OrderRepository {
    private final EntityManager em;

    public List<OrderResponse.ListDTO> findAllByUserId(Integer id) {
        String sql = "SELECT ot.id, st.name, ot.qty, ot.total_price FROM order_tb ot INNER JOIN store_tb st ON ot.store_id = st.id where ot.user_id = ? ORDER BY ot.id DESC";
        Query query = em.createNativeQuery(sql);
        query.setParameter(1, id);
        List<Object[]> obsList = (List<Object[]>) query.getResultList();

        List<OrderResponse.ListDTO> orderList = new ArrayList<>();

        for (Object[] obs : obsList) {
            OrderResponse.ListDTO order = new OrderResponse.ListDTO(
                    (int) obs[0], (String) obs[1], (int) obs[2], (int) obs[3]);
            orderList.add(order);
        }
        
        return orderList;
    }

    public void save(int storeId, int qty, int totalPrice, Integer sessionUserId) {
        Query query = em.createNativeQuery("insert into order_tb(store_id, qty, total_price, user_id) values (?, ?, ?, ?)");
        query.setParameter(1, storeId);
        query.setParameter(2, qty);
        query.setParameter(3, totalPrice);
        query.setParameter(4, sessionUserId);
        query.executeUpdate();
    }
}
