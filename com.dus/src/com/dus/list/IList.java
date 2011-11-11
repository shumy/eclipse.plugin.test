package com.dus.list;

public interface IList<T> extends IRList<T> {
	void add(T item);
	void add(int index, T item);
	
	void remove(T item);
}
