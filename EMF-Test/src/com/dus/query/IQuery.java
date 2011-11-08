package com.dus.query;

import java.util.List;

import org.eclipse.emf.ecore.EObject;

public interface IQuery<T extends EObject> {
	List<T> execute();
}
