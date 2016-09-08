package io.github.sms.t.ishibashi.sss.controller.api.v1;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.github.sms.t.ishibashi.sss.aspect.ExceptionControllerAdvice;
import io.github.sms.t.ishibashi.sss.model.Memo;
import io.github.sms.t.ishibashi.sss.service.MemoService;

@RestController
@RequestMapping("api/v1/memo")
public class MemoResource {
    private MemoService memoService;

    @Autowired
    public MemoResource(MemoService memoService) {
        this.memoService = memoService;
    }

    @RequestMapping("")
    public List<Memo> get() {
        return getMemoService().readAll();
    }

    @RequestMapping("{author}")
    public List<Memo> get(@PathVariable String author) {
        return getMemoService().readByAuthor(author);
    }

    @RequestMapping("param/{memo:[a-zA-Z0-9]+}")
    public List<Memo> getParams(@PathVariable String memo,
                            @RequestParam(required = false, defaultValue = "Default Author") String author) {
        List<Memo> items = new ArrayList<>();
        items.add(getMemoService().join(memo, author));

        return items;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public List<Memo> post(@RequestBody Memo item) {
        getMemoService().write(item.getMemo(), item.getAuthor());
        List<Memo> items = new ArrayList<>();
        items.add(item);

        return items;
    }

    @RequestMapping("error")
    public List<Memo> getError() {
        throw new ExceptionControllerAdvice.MemoException();
    }

    public MemoService getMemoService() {
        return memoService;
    }
}
