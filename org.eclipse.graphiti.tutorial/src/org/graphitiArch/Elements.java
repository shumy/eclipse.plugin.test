package org.graphitiArch;

import java.lang.reflect.Constructor;
import java.util.Collection;
import java.util.HashMap;

import org.eclipse.graphiti.features.IFeatureProvider;

public class Elements {
	private static Elements instance;
	
	//<business-object-class, element-object-type>
	private final HashMap<Class<?>, IElement<?>> elementBoMap = new HashMap<Class<?>, IElement<?>>();
	
	//<element-type, element-type-object>
	private final HashMap<Class<?>, IElement<?>> elements = new HashMap<Class<?>, IElement<?>>();
	
	public static Elements getInstance() {return instance;}
	public static Elements createInstance() {
		if(instance != null) throw new RuntimeException("Elements is a singleton!");
		instance = new Elements();
		return instance;
	}
	
	private Elements() {}
	
	@SuppressWarnings("unchecked")
	private IElement<?> loadElement(String fullClassName, IFeatureProvider fp) {
		try {
			Class<IElement<?>> clazz = (Class<IElement<?>>) Class.forName(fullClassName);
			Constructor<IElement<?>> constructor = clazz.getConstructor(IFeatureProvider.class);
			return constructor.newInstance(fp);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public void setup(IFeatureProvider fp) {
		//TODO: load from XML file...
		String pack = "org.eclipse.graphiti.tutorial.element";
		
		String[] elementNames = new String[] {
			"XEntity",
			"XLink"
		};
		
		for(String name : elementNames) {
			IElement<?> el = loadElement(pack + "." + name, fp);
			elements.put(el.getClass(), el);
			elementBoMap.put(el.getBusinessClass(), el);
		}
	}
	
	@SuppressWarnings("unchecked")
	public <T> T get(Class<T> elementClass) {
		return (T) elements.get(elementClass);
	}
	
	@SuppressWarnings("unchecked")
	public <T> IElement<T> getFromBusinessClass(Class<T> businessObjectClass) {
		return (IElement<T>) elementBoMap.get(businessObjectClass);
	}
	
	public Collection<IElement<?>> getAll() {
		return elementBoMap.values();
	}
}
