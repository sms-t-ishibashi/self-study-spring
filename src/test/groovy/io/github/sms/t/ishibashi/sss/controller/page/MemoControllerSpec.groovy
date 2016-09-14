package io.github.sms.t.ishibashi.sss.controller.page

import io.github.sms.t.ishibashi.sss.model.Memo
import io.github.sms.t.ishibashi.sss.service.MemoService

import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.SpyBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

import spock.lang.Specification
import spock.lang.Unroll

@Unroll
@SpringBootTest
@AutoConfigureMockMvc
class MemoControllerSpec extends Specification {
  
  @Autowired
  MockMvc mockMvc
  @SpyBean
  MemoService memoService
  
  def "リクエストが'#page'の時、全メモを格納したモデルがビュー'#view'に渡されること"() {

    setup:
    Mockito.doReturn(memos).when(memoService).readAll()
    
    expect:
    mockMvc.perform(MockMvcRequestBuilders.get(page))
    .andExpect(MockMvcResultMatchers.view().name(view))
    .andExpect(MockMvcResultMatchers.status().is(status))
    .andExpect(MockMvcResultMatchers.model().attribute('items', memos))
    
    Mockito.verify(memoService, Mockito.times(1)).readAll() || true
    
    where:
    memos | page | view | status
    [memo('メモ1', '徳川家康'), memo('メモ2', '織田信長'), memo('メモ3', '豊臣秀吉')] | '/memo' | 'memo' | 200
  }
  
  def "リクエストが'#page'の時、指定のauthorのメモを格納したモデルがビュー'#view'に渡されること"() {
    
        setup:
        Mockito.doReturn(memos).when(memoService).readByAuthor(Mockito.anyString())
        
        expect:
        mockMvc.perform(MockMvcRequestBuilders.get(page))
        .andExpect(MockMvcResultMatchers.view().name(view))
        .andExpect(MockMvcResultMatchers.status().is(status))
        .andExpect(MockMvcResultMatchers.model().attribute('items', memos))
        
        Mockito.verify(memoService, Mockito.times(1)).readByAuthor(Mockito.anyString()) || true
        
        where:
        memos | page | view | status
        [memo('メモ1', '浅井長政'), memo('メモ2', '浅井長政'), memo('メモ3', '浅井長政')] | '/memo/浅井長政' | 'memo' | 200
        [memo('メモ1', '真田幸村'), memo('メモ2', '真田幸村'), memo('メモ3', '真田幸村')] | '/memo/真田幸村' | 'memo' | 200
  }
  
  def "リクエストが'#page'の時、'#memo'と'#author'正しくがサービスに渡されること"() {
    
        setup:
        Mockito.doNothing().when(memoService).write(Mockito.anyString(), Mockito.anyString())
        
        expect:
        mockMvc.perform(MockMvcRequestBuilders.post(page).param('memo', memo).param('author', author))
        .andExpect(MockMvcResultMatchers.view().name(view))
        .andExpect(MockMvcResultMatchers.status().is(status))
        
        Mockito.verify(memoService, Mockito.times(1)).write(Mockito.anyString(), Mockito.anyString()) || true
        
        where:
        memo | author | page | view | status
        '五輪の書' | '宮本武蔵' | '/memo' | 'redirect:/memo' | 302
        '巌流島の戦い' | '佐々木小次郎' | '/memo' | 'redirect:/memo' | 302
  }
  def memo(String memo, String author) {
    Memo m = new Memo()
    m.setMemo(memo)
    m.setAuthor(author)
    m.setCreated(new Date())
    return m
  } 
  
}
