import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.book.service.TitleService;
import com.book.vo.JsonResult;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Tree {
	
	@Autowired
	private TitleService titleService;
	
	@Test
	public JsonResult name() {
		JsonResult js = new JsonResult();
		js.setData(titleService.queryTitle());
		System.out.println(js);
		return js;
	}
	
}
