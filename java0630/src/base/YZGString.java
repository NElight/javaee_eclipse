package base;


public class YZGString {
	public static void main(String args[]) {
		System.out.println("hello ".concat("world;"));
		
		System.out.println(formatString("abcdefg"));
		System.out.println(charAtString("hello world", 4));
		
		System.out.println(comapreToObj("acb", "adfsa"));
		System.out.println("abcd".getBytes());
		
		System.out.println("abc".hashCode());
		System.out.println("ajfljafdjl".indexOf('l'));
		System.out.println("ajfljafdjl".indexOf('l', 4));
		System.out.println("ajfljafdjl".indexOf("djaaf"));
		System.out.println("ajfldjafljaf".intern());
		System.out.println("dalfjasdfj".lastIndexOf('d'));
		System.out.println("djlfjcasljfdajlfajfals".replace('l', '@'));
		System.out.println("jflajflajfd12lajdf23".replaceAll(".*12", "@"));
		System.out.println("a@sdadfjlas@jsllas@sjf".split("@").toString());
		System.out.println("ajlfdljla".startsWith("a"));
		System.out.println("hello world".startsWith("w", 6));
		System.out.println("abcdefg".substring(2, 4));
		System.out.println("abcdefg".toCharArray()[1]);
	}
	
	static String formatString(String s) {
		
		String rel;
		rel = String.format("format string is %10s", s);
		
		return rel;
	}
	
	static char charAtString(String s, int index) {
		char c;
		c = s.charAt(index);
		return c;
	}
	
	static int comapreToObj(String s, String s2) {
		return s.compareTo(s2);
	}
	
	
	
}