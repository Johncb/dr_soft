package com.drsoft;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.InlineDateField;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

@SuppressWarnings("serial")
public class DrSoftUI extends UI {

	Button searchByName;
	TextField searchValue;
	Label searchParam;

	@Override
	protected void init(VaadinRequest request) {
		final HorizontalLayout layout = new HorizontalLayout();
		layout.setMargin(true);
		setContent(layout);
		layout.addComponent(menuPanel());
		layout.addComponent(resultPanel());
		layout.addComponent(widgetPanel());
	}

	private Component widgetPanel() {
		final VerticalLayout layout = new VerticalLayout();
		layout.setMargin(true);
		Panel panel = new Panel("Calendar");
		panel.setSizeUndefined(); // Shrink to fit content
		layout.addComponent(panel);
		VerticalLayout calendar = new VerticalLayout();
		panel.setContent(calendar);

		// Create a DateField with the default style
		final InlineDateField date = new InlineDateField();

		// Set the date and time to present
		date.setValue(new java.util.Date());

		calendar.addComponent(date);
		Button dateSelect = new Button("Select Date");
		final DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		final Label dateSelected = new Label("");
		dateSelect.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				dateSelected.setValue("You have selected : " + df.format(date.getValue()));
			}
		});

		layout.addComponent(dateSelect);
		layout.addComponent(dateSelected);

		return layout;
	}

	private Component menuPanel() {
		final VerticalLayout layout = new VerticalLayout();
		layout.setMargin(true);
		Label title = new Label("<h1>DrSoft</h1>", (ContentMode.HTML));
		layout.addComponent(title);
		Label menu = new Label("<h2>Menu</h2>", (ContentMode.HTML));
		layout.addComponent(menu);
		Label name = new Label("<b>Search By Name</b>", (ContentMode.HTML));
		layout.addComponent(name);
		searchValue = new TextField();
		layout.addComponent(searchValue);
		searchByName = new Button("Search");
		layout.addComponent(searchByName);
		searchParam = new Label("Your Search : ");
		layout.addComponent(searchParam);
		
		final VerticalLayout docLayout = new VerticalLayout();
		docLayout.addComponent(new DoctorsButtons().pMethod());
		docLayout.setVisible(false);
		layout.addComponent(docLayout);
		
		searchByName.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				searchParam.setValue("Your Search : " + searchValue.getValue());
				docLayout.setVisible(true);
			}
		});
		
		

		return layout;
	}

	private Component resultPanel() {
		final VerticalLayout layout = new VerticalLayout();
		final Label selection = new Label("Patient No. : None Selected");
		layout.addComponent(selection);
		Label tableName = new Label("<h2>Patients</h2>", (ContentMode.HTML));
		layout.addComponent(tableName);
		final Table myTable = new Table();
		myTable.addContainerProperty("ID", String.class, null);
		myTable.addContainerProperty("FirstName", String.class, null);
		myTable.addContainerProperty("SurName", String.class, null);
		myTable.addContainerProperty("DateOfBirth", String.class, null);

		MyDatabaseHandler newHandler = new MyDatabaseHandler();
		ResultSet rs;
		String idNo, DOB;
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		try {
			rs = newHandler.retQuery("select * from patient");
			while (rs.next()) {
				idNo = Integer.toString(rs.getInt(1));
				DOB = df.format(rs.getTime(6));
								
				myTable.addItem(new Object[] { idNo, rs.getString(2),
						rs.getString(3), DOB },
						new Integer(rs.getInt(1)));
			}

		} catch (Exception e) {
			System.out.println("ResultSet Error");
		}

		layout.addComponent(myTable);
		myTable.setPageLength(20);
		myTable.setSelectable(true);
		myTable.setImmediate(true);
		myTable.addValueChangeListener(new Property.ValueChangeListener() {
			@Override
			public void valueChange(ValueChangeEvent event) {
				;
				SharedValues.patientNo = myTable.getValue().toString();
				selection.setValue("Patient No. : " + myTable.getValue());
				layout.addComponent(new Label(SharedValues.patientNo));
			}
		});
		return layout;
	}
}
