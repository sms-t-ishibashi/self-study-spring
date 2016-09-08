package io.github.sms.t.ishibashi.sss.controller.page;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import io.github.sms.t.ishibashi.sss.aspect.ExceptionControllerAdvice;
import io.github.sms.t.ishibashi.sss.model.Memo;
import io.github.sms.t.ishibashi.sss.service.MemoService;

@Controller
@RequestMapping("memo")
public class MemoController {

  private MemoService memoService;

  @Autowired
  public MemoController(MemoService memoService) {
    this.memoService = memoService;
  }

  @RequestMapping("")
  public String get(Model model) {
    model.addAttribute("items", getMemoService().readAll());
    return "memo";
  }

  @RequestMapping("{author}")
  public String get(@PathVariable String author, Model model) {
    model.addAttribute("items", getMemoService().readByAuthor(author));
    return "memo";
  }

  @RequestMapping("param/{memo:[a-zA-Z0-9]+}")
  public String getParams(@PathVariable String memo,
      @RequestParam(required = false, defaultValue = "Default Author") String author, Model model) {
    List<Memo> items = new ArrayList<>();
    items.add(getMemoService().join(memo, author));

    model.addAttribute("items", items);
    return "memo";
  }

  @RequestMapping(value = "", method = RequestMethod.POST)
  public String post(@ModelAttribute Memo item, Model model) {
    getMemoService().write(item.getMemo(), item.getAuthor());
    return "redirect:/memo";
  }

  public MemoService getMemoService() {
    return memoService;
  }
  
  @RequestMapping("error")
  public String getError(Model model) {
      throw new ExceptionControllerAdvice.MemoException();
  }
}
