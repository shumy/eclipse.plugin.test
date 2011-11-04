package test.my.repository;

public class MyContextData {
	private MyTransaction tx;
	
	MyTransaction getTransaction() {return tx;}
	void newTransaction(MySession session) {this.tx = new MyTransaction(session);}
}
