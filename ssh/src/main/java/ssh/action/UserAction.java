package ssh.action;

import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Controller;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.opensymphony.xwork2.ActionSupport;

import ssh.model.User;
import ssh.service.UserService;

@Controller("userAction")
public class UserAction extends ActionSupport {
	
	@Resource
	private UserService userService;
	
	private int id;
	
	public final int getId() {
		return id;
	}
	
	public final void setId(int id) {
		this.id = id;
	}
	
	public String getById() {
		User user = userService.getById(id);
		System.out.println(user);
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("user", user);
		
		
		return "index";
	}
	
}
