package org.eclipse.graphiti.tutorial.diagram;

import org.eclipse.graphiti.dt.AbstractDiagramTypeProvider;
import org.eclipse.graphiti.tb.IToolBehaviorProvider;
import org.graphitiArch.Elements;

public class DiagramTypeProvider extends AbstractDiagramTypeProvider {

	private final IToolBehaviorProvider[] toolBehaviorProviders = new IToolBehaviorProvider[] {
			new ToolBehaviorProvider(this)
	};
	
	public DiagramTypeProvider() {
		super();
		
		Elements elements = Elements.createInstance();
		FeatureProvider fProvider = new FeatureProvider(this);

		Elements.getInstance().setup(fProvider);
		fProvider.setup(elements);
			
		setFeatureProvider(fProvider);	
	}
	
    @Override
    public IToolBehaviorProvider[] getAvailableToolBehaviorProviders() {return toolBehaviorProviders;}
}
