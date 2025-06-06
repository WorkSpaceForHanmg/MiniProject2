// FrontendController.java
package com.example.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FrontendController {

    @RequestMapping(value = {
            "/",
            "/new",
            "/error-note",
            "/diary/{id:[\\d]+}" // 숫자 ID만 포워딩
    })
    public String forward() {
        // src/main/resources/static/index.html로 forward
        return "forward:/index.html";
    }
}
