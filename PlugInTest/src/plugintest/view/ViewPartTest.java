package plugintest.view;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.part.ViewPart;
import org.myWidgets.DynamicTable;

import plugintest.dataprovider.PersonData;
import plugintest.model.Person;

public class ViewPartTest extends ViewPart {
	public static final String ID = "plugintest.view.ViewPartTest"; //$NON-NLS-1$
	private final FormToolkit toolkit = new FormToolkit(Display.getCurrent());
	
	
	public ViewPartTest() {}

	/**
	 * Create contents of the view part.
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {
		//TestComposite testComposite = new TestComposite(parent, SWT.NONE);
		DynamicTable testComposite = new DynamicTable(parent, Person.class, new String[]{"FullName", "Address"}, new int[]{100, 200});
		testComposite.getTableViewer().setInput(PersonData.INNSTANCE.getPersons());
		//toolkit.adapt(testComposite);
		//toolkit.paintBordersFor(testComposite);
		
		createActions();
		initializeToolBar();
		initializeMenu();
	}

	public void dispose() {
		toolkit.dispose();
		super.dispose();
	}

	/**
	 * Create the actions.
	 */
	private void createActions() {
		// Create the actions
	}

	/**
	 * Initialize the toolbar.
	 */
	private void initializeToolBar() {
		//IToolBarManager tbm = getViewSite().getActionBars().getToolBarManager();
	}

	/**
	 * Initialize the menu.
	 */
	private void initializeMenu() {
		//IMenuManager manager = getViewSite().getActionBars().getMenuManager();
	}

	@Override
	public void setFocus() {
		//tableViewer.getControl().setFocus();
	}
}
