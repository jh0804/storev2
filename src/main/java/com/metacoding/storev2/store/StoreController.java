package com.metacoding.storev2.store;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class StoreController {
    private final StoreService storeService;

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/store")
    public String storeList(HttpServletRequest request) {
        List<StoreResponse.ListDTO> listDTOS = storeService.상품목록();
        request.setAttribute("models", listDTOS);
        return "/store/list";
    }
}
