package ssh.test.anno;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.*;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Controller;

import ssh.model.User;
import ognl.*;

@Controller("nameController")
public class NameAction {
	
	@SuppressWarnings("restriction")
	@Resource
	private NameService nameService;
	
	private ArrayList<String> list;
	
	private ArrayList<Map<String, String>> listmap;
	
	public ArrayList<Map<String, String>> getListmap() {
		return listmap;
	}

	public void setListmap(ArrayList<Map<String, String>> listmap) {
		this.listmap = listmap;
	}

	public String execute() {
		
		
		
		
		
		
		setList(nameService.getNames());
		
		listmap = new ArrayList<Map<String,String>>();
		HashMap<String, String > a1 = new HashMap<String, String>();
		a1.put("xh", "1223");
		a1.put("xm", "zhangsan");
		listmap.add(a1);
		
		HashMap<String, String> a2 = new HashMap<String, String>();
		a2.put("xh", "1213");
		a2.put("xm", "lisi");
		listmap.add(a2);
		
		return "index";
		
	}

	public ArrayList<String> getList() {
		return list;
	}

	public void setList(ArrayList<String> list) {
		this.list = list;
	}
	
}
