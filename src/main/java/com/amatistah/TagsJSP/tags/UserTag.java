package com.amatistah.TagsJSP.tags;

import java.io.File;

import com.amatistah.TagsJSP.tags.supes.TagManager;
import com.amatistah.model.User;
import com.google.common.base.Charsets;
import com.google.common.io.Files;

public class UserTag extends TagManager {
	private static final long serialVersionUID = -763387731624169997L;

	private static final String FILE_PATH = "C:/Amatista/Workspace/Amatistah/src/main/resources/userTagContent.tag";

	@Override
	public String appendOut() {
		User user = super.getUser();
		if (user != null && user.getUserName() != null) {
			return "<div class=\"login-dropdown\">" + user.getUserName() + "</div>";
		} else {
			return getFileContent();
		}
	}

	protected String getFileContent() {
		try {
			return Files.asCharSource(new File(FILE_PATH), Charsets.UTF_8).read().replaceAll("\\s\\s+", " ");
		} catch (Exception e) {
			return "";
		}
	}

}
