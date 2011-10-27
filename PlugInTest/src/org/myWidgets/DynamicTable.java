package org.myWidgets;

import java.util.Arrays;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.myWidgets.helper.ReflectionHelper;
import org.myWidgets.helper.TableViewerColumnSorter;
import org.myWidgets.helper.TextHelper;

public class DynamicTable extends Composite {
	private final TableViewer tableViewer;
	
	private int sumOfColumnWeight;
	private final int[] columnWeight;
	
	public DynamicTable(final Composite parent, final Class<?> dataType, final String[] capitalizedFields) {
		super(parent, SWT.NONE);
		setLayout(new FillLayout(SWT.HORIZONTAL));
		
		columnWeight = new int[capitalizedFields.length];
		Arrays.fill(columnWeight, 1);
		calculateSumOfColumnWeight();
		
		tableViewer = new TableViewer(this, SWT.BORDER | SWT.FULL_SELECTION);
		tableViewer.setContentProvider(new ArrayContentProvider());
		
		final Table table = tableViewer.getTable();
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		
		for(int i=0; i<capitalizedFields.length; ++i) {
			final String propertyName = capitalizedFields[i];
			//final Class<?> propertyType = ReflectionHelper.INSTANCE.getPropertyType(dataType, propertyName);
			
			TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
			tableViewerColumn.setLabelProvider(new ColumnLabelProvider() {
				@Override
				public String getText(Object element) {
					//TODO: only working for strings at the moment
					return ReflectionHelper.INSTANCE.getPropertyValue(element, propertyName);
				}
			});
			
			new TableViewerColumnSorter(tableViewerColumn) {
				@SuppressWarnings("unchecked")
				@Override
				protected int doCompare(Viewer viewer, Object e1, Object e2) {
					Object v1 = ReflectionHelper.INSTANCE.getPropertyValue(e1, propertyName);
					Object v2 = ReflectionHelper.INSTANCE.getPropertyValue(e2, propertyName);
					
					if(v1 instanceof Comparable) { //check for null values included in instanceof 
						Comparable<Object> c1 = (Comparable<Object>) v1;
						Comparable<Object> c2 = (Comparable<Object>) v2;
						return c1.compareTo(c2);
					} 
					
					return 0;
				}
			};
			
			
			TableColumn tableColumn = tableViewerColumn.getColumn();
			tableColumn.setText(TextHelper.INSTANCE.capitalizationSplit(capitalizedFields[i]));
			tableColumn.setResizable(false);
			//tableColumn.setMoveable(true);
		}
		
		table.addControlListener(new ControlAdapter() {
			public void controlResized(ControlEvent e) {
				resizeColumns();
				//super.controlResized(e);
			}
		});
		
		//resizeColumns();
	}
	
	public void setColumnWeights(int ...newWeights) {
		for(int i=0; i<columnWeight.length; ++i)
			columnWeight[i] = newWeights[i];
		calculateSumOfColumnWeight();
	}
	
	public Table getTable() {
		return tableViewer.getTable();
	}

	public TableViewer getTableViewer() {
		return tableViewer;
	}	
	
	//---------------------------------------------------------------------------------------
	private void resizeColumns() {
		Table table = getTable();
		Rectangle area = table.getClientArea();
		
		float normalWidth = area.width/(float)sumOfColumnWeight;
		
		TableColumn[] columns = table.getColumns();
		for(int i=0; i<columns.length; ++i) {
			columns[i].setWidth((int)(normalWidth*columnWeight[i]));
		}
	}
	
	private void calculateSumOfColumnWeight() {
		int sum=0;
		for(int v: columnWeight) sum+=v;
		sumOfColumnWeight = sum;
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
	

}
