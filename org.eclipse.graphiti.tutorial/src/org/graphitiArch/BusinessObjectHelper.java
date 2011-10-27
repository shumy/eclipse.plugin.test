package org.graphitiArch;

import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.PictogramLink;

public class BusinessObjectHelper {
	public static Class<?> getBusinessClassFromPictogramElement(PictogramElement pe) {
		PictogramLink link = pe.getLink();
		if(link == null) return null;		//invalid link
		
		Object bo = link.getBusinessObjects().get(0);
		return getBusinessClassFromObject(bo);
	}
	
	public static Class<?> getBusinessClassFromObject(Object bo) {
		//TODO: maybe interfaces are not used!!
		return bo.getClass().getInterfaces()[0];
	}
}
