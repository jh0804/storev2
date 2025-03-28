package com.metacoding.storev2.store;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class StoreService {

    private final StoreRepository storeRepository;

    public List<StoreResponse.ListDTO> 상품목록() {
        return storeRepository.findAll();
    }

    @Transactional
    public void 상품등록(StoreRequest.SaveDTO saveDTO) {
        storeRepository.save(saveDTO.getName(), saveDTO.getStock(), saveDTO.getPrice());
    }

    public Store 상품상세보기(int id) {
        return storeRepository.findById(id);
    }

    @Transactional
    public void 상품삭제(int id) {
        // 1. 상품 있는지 확인
        Store store = storeRepository.findById(id);
        // 2. 없으면 exception
        if (store == null) throw new RuntimeException("상품이 없으므로 삭제할 수 없습니다.");
        // 3. 있으면 삭제
        storeRepository.deleteById(id);
    }

    @Transactional
    public void 상품수정(int id, StoreRequest.UpdateDTO updateDTO) {
        // 1. 상품 있는지 확인
        Store store = storeRepository.findById(id);
        // 2. 없으면 exception
        if (store == null) throw new RuntimeException("상품이 없으므로 수정할 수 없습니다.");
        // 3. 있으면 수정
        storeRepository.updateById(id, updateDTO.getName(), updateDTO.getStock(), updateDTO.getPrice());
    }
}
