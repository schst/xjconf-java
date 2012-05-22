package net.schst.XJConf.Examples;

public class MyClass implements MyInterface {

	private MyInterface bar;
	
	public void foo() {
	}

	public void setBar(MyInterface bar) {
		this.bar = bar;
	}
	
	public MyInterface getBar() {
		return this.bar;
	}
}
