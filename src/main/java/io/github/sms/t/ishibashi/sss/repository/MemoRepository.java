package io.github.sms.t.ishibashi.sss.repository;

import java.util.List;

import io.github.sms.t.ishibashi.sss.model.Memo;

public interface MemoRepository {
  List<Memo> find();

  List<Memo> findByAuthor(String author);

  void save(Memo item);
}
