package com.drsoft;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@Theme("runo")
public class LoginUI extends UI{

	@Override
	protected void init(VaadinRequest request) {
		final VerticalLayout layout = new VerticalLayout();
		layout.setMargin(true);
		setContent(layout);
		layout.setMargin(true);
		Panel panel = new Panel("Login");
		panel.setSizeUndefined(); // Shrink to fit content
		layout.addComponent(panel);
		        
		// Create the content
		FormLayout content = new FormLayout();
		content.addComponent(new TextField("Userame"));
		content.addComponent(new TextField("Password"));
		content.addComponent(new Button("Submit"));
		content.setSizeUndefined(); // Shrink to fit
		content.setMargin(true);
		panel.setContent(content);
	}
}
