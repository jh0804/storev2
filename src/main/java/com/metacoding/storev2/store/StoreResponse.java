package com.metacoding.storev2.store;

import lombok.AllArgsConstructor;
import lombok.Data;

public class StoreResponse {

    @AllArgsConstructor
    @Data
    public static class ListDTO {
        private Integer id;
        private String name;
    }
}
