package com.metacoding.storev2.order;

import com.metacoding.storev2.user.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class OrderController {
    private final OrderService orderService;
    private final HttpSession session;

    @GetMapping("/order")
    public String orderList(HttpServletRequest request) {
        // 로그인한 유저만 자신의 구매목록을 확인 가능하다.
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) throw new RuntimeException("로그인 후 사용해주세요");

        List<OrderResponse.ListDTO> listDTOs = orderService.구매목록(sessionUser.getId());
        request.setAttribute("models", listDTOs);
        
        return "order/list";
    }

    @PostMapping("/order/save")
    public String orderSave(@RequestParam("storeId") int storeId, @RequestParam("qty") int qty){
        // 로그인한 유저만 구매 가능하다.
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) throw new RuntimeException("로그인 후 사용해주세요");

        orderService.상품구매(storeId, qty, sessionUser.getId());

        return "redirect:/order";
    }
}
