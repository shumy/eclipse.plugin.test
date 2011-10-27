package org.myWidgets;

import java.util.Arrays;
import java.util.Date;

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
import org.myWidgets.helper.DateHelper;
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
			
			TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
			tableViewerColumn.setLabelProvider(new ColumnLabelProvider() {
				@Override
				public String getText(Object element) {
					Object value = ReflectionHelper.INSTANCE.getPropertyValue(element, propertyName);
	
					if(value instanceof Date)
						return DateHelper.INSTANCE.format((Date)value);
					else
						return value == null? null: value.toString();
				}
			});
			
			new TableViewerColumnSorter(tableViewerColumn) {
				@SuppressWarnings("unchecked")
				@Override
				protected int doCompare(Viewer viewer, Object e1, Object e2) {
					Object v1 = ReflectionHelper.INSTANCE.getPropertyValue(e1, propertyName);
					Object v2 = ReflectionHelper.INSTANCE.getPropertyValue(e2, propertyName);
					
					if(v1 == null) return 1;
					if(v1 instanceof Comparable) { //check for null values included in instanceof 
						Comparable<Object> c1 = (Comparable<Object>) v1;
						Comparable<Object> c2 = (Comparable<Object>) v2;
						if(c2 == null) return -1;
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
		for(int i=0; i<columnWeight.length; ++i) {
			if(i == newWeights.length) break;
			columnWeight[i] = newWeights[i];
		}
			
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
}
