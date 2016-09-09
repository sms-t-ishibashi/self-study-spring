package io.github.sms.t.ishibashi.sss.controller.page

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
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
  
  def "リクエストが'#page'の時、viewは'#view'を返し、statusは'#status'であること"() {

    expect:
    mockMvc.perform(MockMvcRequestBuilders.get(page))
    .andExpect(MockMvcResultMatchers.view().name(view))
    .andExpect(MockMvcResultMatchers.status().is(status))
    
    where:
    page | view | status
    "/memo" | "memo" | 200
  }
}
