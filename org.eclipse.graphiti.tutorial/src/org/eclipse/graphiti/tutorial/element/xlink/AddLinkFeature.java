package org.eclipse.graphiti.tutorial.element.xlink;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IAddConnectionContext;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.impl.AbstractAddFeature;
import org.eclipse.graphiti.mm.GraphicsAlgorithmContainer;
import org.eclipse.graphiti.mm.algorithms.Polyline;
import org.eclipse.graphiti.mm.algorithms.Text;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.mm.pictograms.ConnectionDecorator;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.IGaService;
import org.eclipse.graphiti.services.IPeCreateService;
import org.eclipse.graphiti.tutorial.element.XLink;
import org.eclipse.graphiti.util.IColorConstant;
import org.graphitiArch.Elements;

public class AddLinkFeature extends AbstractAddFeature {
 
    public AddLinkFeature (IFeatureProvider fp) {super(fp);}
    
    public boolean canAdd(IAddContext context) {
    	XLink xlink = Elements.getInstance().get(XLink.class);
        return context instanceof IAddConnectionContext && 
        		xlink.getBusinessClass().isAssignableFrom(context.getNewObject().getClass());
    }

 
    public PictogramElement add(IAddContext context) {
        IPeCreateService peCreateService = Graphiti.getPeCreateService();
       
        IAddConnectionContext addConContext = (IAddConnectionContext) context;
        
        Connection connection = peCreateService.createFreeFormConnection(getDiagram());
        connection.setStart(addConContext.getSourceAnchor());
        connection.setEnd(addConContext.getTargetAnchor());
        link(connection, context.getNewObject());
 
        createLine(connection);
        createText(connection);
        createArrow(connection);
        
        return connection;
    }
    
    private void createLine(GraphicsAlgorithmContainer container) {
    	IGaService gaService = Graphiti.getGaService();
    	
        Polyline polyline = gaService.createPolyline(container);
        polyline.setLineWidth(2);
        polyline.setForeground(manageColor(IColorConstant.BLACK));
    }
 
    private void createArrow(Connection connection) {
        IGaService gaService = Graphiti.getGaService();
        IPeCreateService peCreateService = Graphiti.getPeCreateService();
        
        ConnectionDecorator cd = peCreateService.createConnectionDecorator(connection, false, 1.0, true);
        Polyline polyline = gaService.createPolyline(cd, new int[] { -15, 10, 0, 0, -15, -10 });
        polyline.setForeground(manageColor(IColorConstant.BLACK));
        polyline.setLineWidth(2);
    }
    
    private void createText(Connection connection) {
    	IGaService gaService = Graphiti.getGaService();
    	IPeCreateService peCreateService = Graphiti.getPeCreateService();
    	
    	ConnectionDecorator cd = peCreateService.createConnectionDecorator(connection, true, 0.5, true);
        Text text = gaService.createDefaultText(getDiagram(), cd);
        text.setForeground(manageColor(IColorConstant.BLACK));
        text.setValue("link");
        gaService.setLocation(text, 10, 0);
    }

}
