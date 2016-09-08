package io.github.sms.t.ishibashi.sss.controller.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("book")
public class BookController {
  
  @GetMapping
  public String get() {
    return "book";
  }
  
  @PostMapping
  public String post() {
    return "helloworld";
  }
}
