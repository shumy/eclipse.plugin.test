package org.eclipse.graphiti.tutorial;

import org.eclipse.graphiti.ui.platform.AbstractImageProvider;

public class ImageProvider extends AbstractImageProvider {
	private static final String IMG = Activator.PLUGIN_ID + ".image.";
	
	public static final String IMG_LINK = IMG + "link";

	@Override
	protected void addAvailableImages() {
		addImageFilePath(IMG_LINK, "icons/link.png");
	}
}
