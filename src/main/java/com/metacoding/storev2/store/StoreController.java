package com.metacoding.storev2.store;

import com.metacoding.storev2.user.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/store/{id}")
    public String detail(@PathVariable("id") int id, HttpServletRequest request) {
        Store store = storeService.상품상세보기(id);
        request.setAttribute("model", store);
        return "store/detail";
    }

    @PostMapping("/store/{id}/delete")
    public String delete(@PathVariable("id") int id) {
        // 로그인한 유저만 상품삭제 가능하다.
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) throw new RuntimeException("로그인 후 사용해주세요");

        storeService.상품삭제(id);
        return "redirect:/store";
    }

    @GetMapping("/store/{id}/update-form")
    public String updateForm(@PathVariable("id") int id, HttpServletRequest request) {
        // 로그인한 유저만 상품수정 가능하다.
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) throw new RuntimeException("로그인 후 사용해주세요");

        Store store = storeService.상품상세보기(id);
        request.setAttribute("model", store);

        return "store/update-form";
    }

    @PostMapping("/store/{id}/update")
    public String update(@PathVariable("id") int id, StoreRequest.UpdateDTO updateDTO) {
        // 로그인한 유저만 상품수정 가능하다.
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) throw new RuntimeException("로그인 후 사용해주세요");

        storeService.상품수정(id, updateDTO);
        return "redirect:/store/" + id;
    }
}
