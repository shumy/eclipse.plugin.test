package org.myWidgets;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.myWidgets.helper.ReflectionHelper;
import org.myWidgets.helper.TextHelper;


public class DynamicTable extends Composite {
	private final TableViewer tableViewer;
	//private Table table;

	
	public DynamicTable(final Composite parent, final Class<?> dataType, final String[] capitalizedFields, final int[] widths) {
		super(parent, SWT.NONE);
		setLayout(new FillLayout(SWT.HORIZONTAL));
		
		tableViewer = new TableViewer(this, SWT.BORDER | SWT.FULL_SELECTION);
		tableViewer.setContentProvider(new ArrayContentProvider());
		
		Table table = tableViewer.getTable();
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		
		for(int i=0; i<capitalizedFields.length; ++i) {
			final String propertyName = capitalizedFields[i];
			final Class<?> propertyType = ReflectionHelper.INSTANCE.getPropertyType(dataType, propertyName);
			
			TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
			tableViewerColumn.setLabelProvider(new ColumnLabelProvider() {
				@Override
				public String getText(Object element) {
					return ReflectionHelper.INSTANCE.getPropertyValue(element, propertyName);
				}
			});
			
			//TODO: provide column sorter with property type information
			
			
			TableColumn tableColumn = tableViewerColumn.getColumn();
			tableColumn.setText(TextHelper.INSTANCE.capitalizationSplit(capitalizedFields[i]));
			tableColumn.setWidth(widths[i]);
			tableColumn.setResizable(true);
			tableColumn.setMoveable(true);
		}
	}
	
	/*public TestComposite(Composite parent, int style) {
		super(parent, style);
		setLayout(new FillLayout(SWT.HORIZONTAL));
		
		{
			tableViewer = new TableViewer(this, SWT.BORDER | SWT.FULL_SELECTION);
			tableViewer.setContentProvider(new ArrayContentProvider());
			table = tableViewer.getTable();
			table.setHeaderVisible(true);
			table.setLinesVisible(true);
			{
				TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
				new TableViewerColumnSorter(tableViewerColumn) {
					@Override
					protected int doCompare(Viewer viewer, Object e1, Object e2) {
						Person person1 = (Person) e1;
						Person person2 = (Person) e2;
						
						return person1.getFullName().compareTo(person2.getFullName());
					}
				};
				tableViewerColumn.setLabelProvider(new ColumnLabelProvider() {
					public String getText(Object element) {
						return ((Person)element).getFullName();
					}
				});
				TableColumn tblclmnName = tableViewerColumn.getColumn();
				tblclmnName.setWidth(100);
				tblclmnName.setText("Name");
			}
			
			{
				TableViewerColumn tableViewerColumn_1 = new TableViewerColumn(tableViewer, SWT.NONE);
				tableViewerColumn_1.setLabelProvider(new ColumnLabelProvider() {
					public String getText(Object element) {
						return ((Person)element).getAddress();
					}
				});
				TableColumn tblclmnAddress = tableViewerColumn_1.getColumn();
				tblclmnAddress.setWidth(100);
				tblclmnAddress.setText("Address");
			}
		}
	}*/
	
	public Table getTable() {
		return tableViewer.getTable();
	}

	public TableViewer getTableViewer() {
		return tableViewer;
	}
	
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
