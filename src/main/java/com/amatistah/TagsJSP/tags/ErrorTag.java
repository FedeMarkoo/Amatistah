package com.amatistah.TagsJSP.tags;

import java.util.ArrayList;

import com.amatistah.TagsJSP.tags.supes.TagManager;
import com.amatistah.keys.AmatistaKeys;
import com.amatistah.model.ErrorResult;

public class ErrorTag extends TagManager {

	private static final long serialVersionUID = 8035504815120819904L;

	@Override
	protected String appendOut() {
		String errorCode = "<div class=\"list-group\">";
		ArrayList<ErrorResult> errors = getErrors();
		for (ErrorResult error : errors) {
			if (error.getErrorLevel() == AmatistaKeys.ERROR)
				errorCode += "<a class=\"list-group-item list-group-item-action list-group-item-danger\">" + error.getError() + "</a>";
			else if (error.getErrorLevel() == AmatistaKeys.WARNING)
				errorCode += "<a class=\"list-group-item list-group-item-action list-group-item-warning\">" + error.getError() + "</div>";
		}
		return errorCode + "</div>";
	}

	@SuppressWarnings("unchecked")
	protected ArrayList<ErrorResult> getErrors() {
		Object obj = getRequest().getAttribute("errors");
		if (obj == null)
			return null;

		if (!(obj instanceof ArrayList<?>))
			return null;

		return (ArrayList<ErrorResult>) obj;
	}

}
