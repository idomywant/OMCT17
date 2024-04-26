package com.ifi.omct17.Classes.Common;

public class ValidateException extends Exception {
	public String validErrorMessage;
	public ValidateException(String message)
	{
			validErrorMessage = message;
	}
	
}
