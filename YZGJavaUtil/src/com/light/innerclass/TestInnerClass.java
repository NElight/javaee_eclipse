package com.light.innerclass;

public class TestInnerClass {
	public static void main(String[] args) {
		OuterClass.InnerClass aClass = new OuterClass().new InnerClass();
		aClass.print();
		new OuterClass().show();
		OuterClass.StaticInnerClass class1 = new OuterClass.StaticInnerClass();
		class1.staticPrint();
		new OuterClass().print1();
		new OuterClass().print2();
		new OuterClass().print3(new eat() {
			@Override
			public void eat() {
				System.out.print("参数式匿名类");
			}
			
		});
	}
}


class OuterClass{
	
	
	class InnerClass{
		public void print() {
			System.out.println("hello , i am innerclass");
		}
	}
	
	
	public void show() {
		//只能在内部实例化
		class MethodInnerClass{
			public void showMessage() {
				System.out.println("i am method inner class");
			}
		}
		new MethodInnerClass().showMessage();
	}
	
	public static class StaticInnerClass{
		public void staticPrint() {
			System.out.println("i am a static inner class");
		}
	}
	
	public void print1() {
		cat a1 = new cat() {
			
			@Override
			public void eat() {
				// TODO Auto-generated method stub
				System.out.println("i am 匿名内部类");
			}
		};
		a1.eat();
	}
	
	public void print2() {
		eat c1 = new eat() {
			
			@Override
			public void eat() {
				// TODO Auto-generated method stub
				System.out.println("i am 接口式内部类");
			}
		};
		c1.eat();
	}
	
	public void print3(eat eat) {
		eat.eat();
	}
	
}

abstract class cat{
	public abstract void eat();
}

interface eat{
	public void eat();
}

