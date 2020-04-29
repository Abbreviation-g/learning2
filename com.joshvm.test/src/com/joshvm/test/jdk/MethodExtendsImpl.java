package com.joshvm.test.jdk;

public class MethodExtendsImpl {
	static class Derive extends Father implements Interface{
		@Override
		public void f2() {
			
		}
	}
	static class Father{
		public void f(){
			
		}
	}
	static interface Interface{
		void f();
		void f2();
	}
}
