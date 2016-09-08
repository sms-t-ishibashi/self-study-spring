package io.github.sms.t.ishibashi.sss.service

import io.github.sms.t.ishibashi.sss.model.Memo
import io.github.sms.t.ishibashi.sss.repository.MemoRepository

import org.mockito.ArgumentCaptor
import org.mockito.Mockito
import org.springframework.boot.test.context.SpringBootTest

import spock.lang.Specification
import spock.lang.Unroll

@Unroll
@SpringBootTest
class MemoServiceSpec extends Specification {
  
  MemoService memoService
  MemoRepository memoRepository 
  
  def setup() {
    memoRepository = Mockito.mock(MemoRepository)
    memoService = new MemoService(memoRepository)
  }
  
  def "joinは引数のmemoとauthorを格納したモデルMemoを返すこと"() {
    
    expect:
    Memo expected = memoService.join(memo, author)
    assert memo == expected.getMemo()
    assert author == expected.getAuthor()
    
    where:
    memo | author
    "memo" | "author"
    "モデルのメモですよ" | "モデルのオーサーですよ"
    "memo123" | "author123"
  }
  
  def "readAllで全メモを取得できること"() {
    setup:
    Mockito.doReturn(expected).when(memoRepository).find()
    
    expect:
    assert expected == memoService.readAll()
    
    where:
    expected                                                    | _
    [makeMemo("メモ", "カイポチ")]                              | _
    [makeMemo("メモ1", "カイポチ1"), makeMemo("メモ2", "カイポチ2")] | _
    
  }
  
  def "readByAuthorでは指定したauthorのメモを取得できること"() {
    setup:
    Mockito.doReturn(expected).when(memoRepository).findByAuthor(Mockito.anyString())
    
    expect:
    assert expected == memoService.readByAuthor(author)
    
    where:
    expected | author
    [makeMemo("メモ1", "カイポチ"), makeMemo("メモ2", "カイポチ")] | "カイポチ"
  }
  
  def "writeでリポジトリにMemoデータが正しく渡っていること"() {
    setup:
    Mockito.doNothing().when(memoRepository).save(Mockito.any())
    ArgumentCaptor<Memo> args = ArgumentCaptor.forClass(Memo.class)
    
    expect:
    memoService.write(memo, author)
    Mockito.verify(memoRepository).save(args.capture())
    assert memo == args.getValue().getMemo()
    assert author == args.getValue().getAuthor()
    
    where:
    memo | author
    "メモメモメモ" | "カイポチ"
    "abcdefghijkl" | "linda"
  }
  
  def makeMemo(String memo, String author) {
    Memo modelMemo = new Memo()
    modelMemo.setMemo(memo)
    modelMemo.setAuthor(author)
    modelMemo.setCreated(new Date())
    return modelMemo
  }
}
