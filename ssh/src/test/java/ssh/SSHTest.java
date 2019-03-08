package ssh;

import java.util.Date;
import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ssh.service.UserService;
import ssh.test.anno.NameService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext.xml")
public class SSHTest {
	
	@Resource
	private Date date;
	
	@Test
	public void testSpring() {
		System.out.println(date);
	}
	
	@Resource
	private UserService userService;
	@Test
	public void testGetById() {
		System.out.println(userService.getById(1));
	}
	
	@Resource
	private NameService nameService;
	
	@Test
	public void testGetName() {
		System.out.println(nameService.getName());
		
	}
	
	
}
