package org.eclipse.graphiti.tutorial.element.xlink;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICreateConnectionContext;
import org.eclipse.graphiti.features.context.impl.AddConnectionContext;
import org.eclipse.graphiti.features.impl.AbstractCreateConnectionFeature;
import org.eclipse.graphiti.mm.pictograms.Anchor;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.tutorial.element.XEntity;
import org.eclipse.graphiti.tutorial.element.XLink;
import org.graphitiArch.Elements;

public class CreateLinkFeature extends AbstractCreateConnectionFeature {

	public CreateLinkFeature(IFeatureProvider fp) {super(fp, "Link", "Create link");}

	public boolean canCreate(ICreateConnectionContext context) {
		Object source = getEntityFromAnchor(context.getSourceAnchor());
		Object target = getEntityFromAnchor(context.getTargetAnchor());
		
		return source != null && target != null && source != target;
	}

	public boolean canStartConnection(ICreateConnectionContext context) {
		return getEntityFromAnchor(context.getSourceAnchor()) != null;
	}

	public Connection create(ICreateConnectionContext context) {
		Object source = getEntityFromAnchor(context.getSourceAnchor());
		Object target = getEntityFromAnchor(context.getTargetAnchor());
		XLink xlink = Elements.getInstance().get(XLink.class);
		
		Object link = xlink.createLink(source, target);
		
		// add connection for business object
		AddConnectionContext addContext = new AddConnectionContext(context.getSourceAnchor(), context.getTargetAnchor());
		addContext.setNewObject(link);
		
		return (Connection) getFeatureProvider().addIfPossible(addContext);
	}

	private Object getEntityFromAnchor(Anchor anchor) {
		XEntity xentity = Elements.getInstance().get(XEntity.class);
		if (anchor != null) {
			Object object = getBusinessObjectForPictogramElement(anchor.getParent());
			if (xentity.getBusinessClass().isAssignableFrom(object.getClass()))
				return object;
		}
		return null;
	}
}
