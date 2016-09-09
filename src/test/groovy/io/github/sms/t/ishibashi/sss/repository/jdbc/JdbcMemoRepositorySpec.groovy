package io.github.sms.t.ishibashi.sss.repository.jdbc

import io.github.sms.t.ishibashi.sss.model.Memo
import io.github.sms.t.ishibashi.sss.repository.MemoRepository

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

import spock.lang.Specification
import spock.lang.Unroll

import com.ninja_squad.dbsetup.DbSetup
import com.ninja_squad.dbsetup.Operations
import com.ninja_squad.dbsetup.destination.Destination

@Unroll
@SpringBootTest
class JdbcMemoRepositorySpec extends Specification {
  
  @Autowired
  Destination destination

  @Autowired
  MemoRepository repository
  
  def setup() {
    def initData = initData()

    new DbSetup(destination,
      Operations.sequenceOf(Operations.truncate('MEMO'), initData)).launch()
  }
  
  def initData() {
    return Operations.insertInto('MEMO')
    .columns('MEMO', 'AUTHOR', 'CREATED')
    .values('Spring Bootを学ぶ', '金次郎', '2016-09-08 17:01:00.0')
    .values('Spockを学ぶ', '金太郎', '2016-09-08 17:02:00.0')
    .values('Thymeleafを学ぶ', '金字塔', '2016-09-08 17:03:00.0')
    .values('Flywayを学ぶ', '金さん銀さん' ,'2016-09-08 17:04:00.0')
    .build()
  }
  
  def "memo= '#memo' 、author= '#author' のMemoがRepositoryに渡ること"() {
    setup:
    Memo item = makeMemo(memo, author)
    
    expect:
    repository.save(item)
    List<Memo> results = repository.findByAuthor(author)
    assert memo == results.get(0).getMemo()
    assert author == results.get(0).getAuthor()
    
    where:
    memo | author
    "保存するメモ" | "カイポチさん"
    "" | "メモが空文字"
    "authorが空文字" | ""
  }
  
  
  def "Memoが全量検索できること"() {
    setup:
    def testData = [
      ['Spring Bootを学ぶ' , '金次郎', '2016-09-08 17:01:00.0'],
      ['Spockを学ぶ'       , '金太郎', '2016-09-08 17:02:00.0'],
      ['Thymeleafを学ぶ'       , '金字塔', '2016-09-08 17:03:00.0'],
      ['Flywayを学ぶ'       , '金さん銀さん', '2016-09-08 17:04:00.0']
    ]
    
    def insertOps = Operations.insertInto('MEMO')
      .columns('MEMO', 'AUTHOR', 'CREATED')

    for (def item : testData) {
      insertOps.values((Object[]) item.toArray());
    }

    new DbSetup(destination,
      Operations.sequenceOf(Operations.truncate('MEMO'), insertOps.build())).launch()
    
    when:
    def results = repository.find()
    
    then:
    assert results.size() == testData.size()
    
    for (def i = 0; i < results.size(); i++) {
      assert results[i].author == testData[i][1]
      assert results[i].memo == testData[i][0]
    }
  }
  
  
  def "指定したauthorのメモが検索できること"() {
    setup:
    def testData = [
      ['Spring Bootを学ぶ' , '金次郎', '2016-09-08 17:01:00.0'],
      ['Spockを学ぶ'       , '金次郎', '2016-09-08 17:02:00.0'],
      ['Thymeleafを学ぶ'       , '金字塔', '2016-09-08 17:03:00.0'],
      ['Flywayを学ぶ'       , '金さん銀さん', '2016-09-08 17:04:00.0']
    ]
    
    def insertOps = Operations.insertInto('MEMO')
      .columns('MEMO', 'AUTHOR', 'CREATED')

    for (def item : testData) {
      insertOps.values((Object[]) item.toArray());
    }

    new DbSetup(destination,
      Operations.sequenceOf(Operations.truncate('MEMO'), insertOps.build())).launch()
      
    when:
    def results = repository.findByAuthor("金次郎")
    
    then:
    assert results.size() == 2
    
    for (def i = 0; i < results.size(); i++) {
      assert results[i].author == "金次郎"
      assert results[i].memo == testData[i][0]
    }
  }
  
  def makeMemo(String memo, String author) {
    Memo modelMemo = new Memo()
    modelMemo.setMemo(memo)
    modelMemo.setAuthor(author)
    modelMemo.setCreated(new Date())
    return modelMemo
  }
}
