package com.metacoding.storev2.store;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class StoreController {
    private final StoreService storeService;

    @GetMapping("/")
    public String home() {
        return "home";
    }
}
