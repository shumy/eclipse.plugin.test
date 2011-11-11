package com.dus.list;

public interface IRList<T> extends Iterable<T> {
	boolean contains(T item);
}
