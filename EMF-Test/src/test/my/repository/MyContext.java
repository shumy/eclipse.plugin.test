package test.my.repository;


public class MyContext {
	private static final ThreadLocal<MyContextData> contextData = new ThreadLocal<MyContextData>() {
		@Override
		protected MyContextData initialValue() {return new MyContextData();}
	};
	
	//used on context data transference...
	public static MyContextData getData() {return contextData.get();}
	public static void setData(MyContextData cd) {contextData.set(cd);}
	
	//TODO: other user available methods!!
}
