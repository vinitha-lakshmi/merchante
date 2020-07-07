package com.merchante;

public enum PageObjects {
	
	HeaderTitle("h2#page_title"),
	UserTab("ul.header-item > li#users"),
	NewUser("span.action_item > a"),
	InputUserName("input#user_username"),
	InputPassword("input#user_password"),
	InputEmail("input#user_email"),
	InputSubmit("input[type='submit']"),
	FlashNotice("div.flash_notice"),
	UserTableRows("table#index_table_users > tbody > tr"),
	UserError("li.string.input.error > p.inline-errors"),
	PasswordError("li.password.input.error > p.inline-errors"),
	EmailError("li.email.input.error > p.inline-errors"),
	SelectUserNameFilterOptions("div#q_username_input > select"),
	SelectEmailFilterOptions("div#q_email_input > select"),
	InputDateFrom("input[placeholder='From']"),
	InputDateTo("input[placeholder='To']")
	;	
	
	private String property;

	private PageObjects(String property) {
		this.setProperty(property);
	}

	private void setProperty(String property) {
		this.property = property;
	}

	public String getProperty() {
		return property;
	}

}
