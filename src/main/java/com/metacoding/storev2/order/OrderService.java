package com.metacoding.storev2.order;

import com.metacoding.storev2.store.Store;
import com.metacoding.storev2.store.StoreRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final StoreRepository storeRepository;

    public List<OrderResponse.ListDTO> 구매목록(Integer id) {
        return orderRepository.findAllByUserId(id);
    }

    @Transactional
    public void 상품구매(int storeId, int qty, Integer sessionUserId) {
        // 1. 상품 조회 
        Store store = storeRepository.findById(storeId);

        // 2. 재고보다 많이 구매할 경우, exception
        if (store.getStock() < qty) throw new RuntimeException("재고가 부족합니다. 현 재고 : " + store.getStock() + "개");
        
        // 3. 재고보다 적게 구매할 경우, 상품 재고 업데이트 (조회, 업데이트)
        store.재고감소(qty);
        storeRepository.updateById(store.getId(), store.getName(), store.getStock(), store.getPrice());

        // 4. 구매 기록 하기
        orderRepository.save(storeId, qty, qty * store.getPrice(), sessionUserId);
    }
}
