package io.github.sms.t.ishibashi.sss.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class RepositoryPointcut {
  @Pointcut("execution(* io.github.sms.t.ishibashi.sss.repository..MemoRepository+.save(..))")
  public void save() {}
  
  @Pointcut("execution(* io.github.sms.t.ishibashi.sss.repository..MemoRepository+.findByAuthor(..)) && args(author)")
  public void findByAuthor(String author) {}
}
