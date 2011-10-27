package org.eclipse.graphiti.tutorial.element.xentity;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.graphiti.examples.common.ExampleUtil;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICreateContext;
import org.eclipse.graphiti.features.impl.AbstractCreateFeature;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.tutorial.element.XEntity;
import org.graphitiArch.Elements;

public class CreateEntityFeature extends AbstractCreateFeature {
	private static final String TITLE = "Create entity";
	private static final String USER_QUESTION = "Enter new entity name";

	public CreateEntityFeature(IFeatureProvider fp) {super(fp, "Entity", TITLE);}

	public boolean canCreate(ICreateContext context) {
		return context.getTargetContainer() instanceof Diagram;
	}

	public Object[] create(ICreateContext context) {
		String newClassName = ExampleUtil.askString(TITLE, USER_QUESTION, "");
		if (newClassName == null || newClassName.trim().length() == 0)
			return EMPTY;

		XEntity xentity = Elements.getInstance().get(XEntity.class);
		EObject entity = xentity.createEntity(newClassName);
		getDiagram().eResource().getContents().add(entity);

		addGraphicalRepresentation(context, entity);
		return new Object[] {entity};
	}
}
