package org.graphitiArch.has;

import java.util.Collection;

import org.eclipse.graphiti.features.custom.ICustomFeature;

public interface IHasCustomFeatures {
	Collection<ICustomFeature> getCustomFeatures();
	<T extends ICustomFeature> T getCustom(Class<T> customFeatureClass);
}
