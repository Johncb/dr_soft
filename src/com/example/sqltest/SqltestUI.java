package com.example.sqltest;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.vaadin.server.VaadinRequest;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.VerticalLayout;

/**
 * Main UI class
 */
@SuppressWarnings("serial")
public class SqltestUI extends UI {

	@Override
	protected void init(VaadinRequest request) {
		final VerticalLayout layout = new VerticalLayout();
		layout.setMargin(true);
		setContent(layout);

		Label patsHead = new Label("Patients Table");
		layout.addComponent(patsHead);

		Button button = new Button("Click for List of doctors");
		button.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {

			}
		});

		Table table = new Table("Patient Details");

		table = new Table();
		// table.setStyleName("iso3166");
		table.setPageLength(6);
		table.setSizeFull();
		table.setSelectable(true);
		table.setMultiSelect(true);
		table.setImmediate(true);
		table.setColumnReorderingAllowed(true);
		table.setColumnCollapsingAllowed(true);
		table.setWidth("1000px");
		table.setHeight("250px");

		table.addContainerProperty("PatientID", Integer.class, null);
		table.addContainerProperty("First Name", String.class, null);
		table.addContainerProperty("Surname", String.class, null);
		table.addContainerProperty("Address", String.class, null);
		table.addContainerProperty("TelNo", Integer.class, null);
		table.addContainerProperty("DOB", Date.class, null);
		table.addContainerProperty("Gender", String.class, null);

		layout.addComponent(table);

		try {
			Context ctx = new InitialContext();
			DataSource ds = (DataSource) ctx
					.lookup("java:comp/env/jdbc/Surgery");
			Connection conn = ds.getConnection();
			Statement stat = conn.createStatement();
			ResultSet rs = stat.executeQuery("select * from Patient");

			while (rs.next()) {
				int index = 1;
				table.addItem(
						new Object[] { rs.getInt(1), rs.getString(2),
								rs.getString(3), rs.getString(4), rs.getInt(5),
								rs.getDate(6), rs.getString(7) },
						rs.getInt(index));
				index++;
			}

			// Close the Result to use a new query later
			rs.close();

		} catch (Exception e) {

			layout.addComponent(new Label("Error"));
		}
		layout.addComponent(button);
		layout.setComponentAlignment(button, Alignment.BOTTOM_LEFT);

	}

}