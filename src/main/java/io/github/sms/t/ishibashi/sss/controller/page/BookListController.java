package io.github.sms.t.ishibashi.sss.controller.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("booklist")
public class BookListController {
  
  @GetMapping
  public String get() {
    return "booklist";
  }
}
