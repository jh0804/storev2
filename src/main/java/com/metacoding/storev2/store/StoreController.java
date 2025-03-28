package com.metacoding.storev2.store;

import com.metacoding.storev2.user.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class StoreController {
    private final StoreService storeService;
    private final HttpSession session;

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/store")
    public String storeList(HttpServletRequest request) {
        List<StoreResponse.ListDTO> listDTOS = storeService.상품목록();
        request.setAttribute("models", listDTOS);
        return "store/list";
    }

    @GetMapping("/store/save-form")
    public String saveForm() {
        // 로그인한 유저만 상품등록 가능하다.
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) throw new RuntimeException("로그인 후 사용해주세요");

        return "store/save-form";
    }

    @PostMapping("/store/save")
    public String save(StoreRequest.SaveDTO saveDTO) {
        // 로그인한 유저만 상품등록 가능하다.
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) throw new RuntimeException("로그인 후 사용해주세요");
        storeService.상품등록(saveDTO);

        return "redirect:/store";
    }
}
