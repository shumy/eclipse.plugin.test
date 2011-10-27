package org.eclipse.graphiti.tutorial.element.xentity;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.impl.AbstractAddShapeFeature;
import org.eclipse.graphiti.mm.GraphicsAlgorithmContainer;
import org.eclipse.graphiti.mm.algorithms.Polyline;
import org.eclipse.graphiti.mm.algorithms.RoundedRectangle;
import org.eclipse.graphiti.mm.algorithms.Text;
import org.eclipse.graphiti.mm.algorithms.styles.Orientation;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.IGaService;
import org.eclipse.graphiti.services.IPeCreateService;
import org.eclipse.graphiti.util.ColorConstant;
import org.eclipse.graphiti.util.IColorConstant;

public class AddEntityFeature extends AbstractAddShapeFeature {
	private static final int WIDTH = 100;
	private static final int HEIGHT = 50;
	
	private static final IColorConstant CLASS_TEXT_FOREGROUND = new ColorConstant(51, 51, 153);
	private static final IColorConstant CLASS_FOREGROUND = new ColorConstant(255, 102, 0);
	private static final IColorConstant CLASS_BACKGROUND = new ColorConstant(255, 204, 153);

	public AddEntityFeature(IFeatureProvider fp) {super(fp);}

	public boolean canAdd(IAddContext context) {	
		return context.getTargetContainer() instanceof Diagram;
	}

	public PictogramElement add(IAddContext context) {
        IPeCreateService peCreateService = Graphiti.getPeCreateService();
        
        ContainerShape containerShape = peCreateService.createContainerShape(getDiagram(), true);
        peCreateService.createChopboxAnchor(containerShape);	// add a chopbox anchor to the shape
        link(containerShape, context.getNewObject());
 
		createRectangle(containerShape, context.getX(), context.getY());
		createLine(containerShape);
		createText(containerShape, "No Name");

        layoutPictogramElement(containerShape);
		return containerShape;
	}
	
	private void createRectangle(GraphicsAlgorithmContainer container, int x, int y) {
		IGaService gaService = Graphiti.getGaService();
		
		RoundedRectangle roundedRectangle = gaService.createRoundedRectangle(container, 5, 5);
		roundedRectangle.setForeground(manageColor(CLASS_FOREGROUND));
		roundedRectangle.setBackground(manageColor(CLASS_BACKGROUND));
		roundedRectangle.setLineWidth(2);
		
		gaService.setLocationAndSize(roundedRectangle, x, y, WIDTH, HEIGHT);
	}
	
	private void createLine(ContainerShape container) {
		IGaService gaService = Graphiti.getGaService();
		IPeCreateService peCreateService = Graphiti.getPeCreateService();
		
		Shape shape = peCreateService.createShape(container, false);
		Polyline polyline = gaService.createPolyline(shape, new int[] {0, 20, WIDTH, 20});
		polyline.setForeground(manageColor(CLASS_FOREGROUND));
		polyline.setLineWidth(2);
	}
	
	private void createText(ContainerShape container, String name) {
		IGaService gaService = Graphiti.getGaService();
		IPeCreateService peCreateService = Graphiti.getPeCreateService();
		
		Shape shape = peCreateService.createShape(container, false);
		Text text = gaService.createDefaultText(getDiagram(), shape, name);
		text.setForeground(manageColor(CLASS_TEXT_FOREGROUND));
		text.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		text.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);

		gaService.setLocationAndSize(text, 0, 0, WIDTH, 20);
	}
}
