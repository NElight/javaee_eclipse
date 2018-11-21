package base;

public class YZGStringbuffbuild {
	public static void main(String args[]) {
		StringBuffer buffer = new StringBuffer("ajflaf");
		buffer.append("dsjflajfaj");
		System.out.println(buffer);
		System.out.println(buffer.reverse());
		buffer.delete(0, 7);
		System.out.println(buffer);
	}
}
