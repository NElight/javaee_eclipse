package ssh.test.anno;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

@Service("nameService")
public class NameService {
	
	private String name = "abc";
	private ArrayList<String> nameStrings = new ArrayList<String>() {{add("abc"); add("def"); add("ghi");}};
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name= name;
	}
	
	public ArrayList<String> getNames(){
		return nameStrings;
	}
	
}
