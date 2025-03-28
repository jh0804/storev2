package com.metacoding.storev2.order;

import lombok.AllArgsConstructor;
import lombok.Data;

public class OrderResponse {

    @Data
    @AllArgsConstructor
    public static class ListDTO {
        private Integer id;
        private String storeName;
        private Integer qty;
        private Integer totalPrice;
    }
}
