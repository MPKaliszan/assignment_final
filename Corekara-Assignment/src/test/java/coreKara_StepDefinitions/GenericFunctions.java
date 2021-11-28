package coreKara_StepDefinitions;

import java.nio.charset.StandardCharsets;

public class GenericFunctions {
//This file contains several generic methods that could be used across multiple step definitions to help prevent rewriting the same code over and over again

	public String convertStringToUtf(String convertThisText) {
		//For converting Japanese text in strings into UTF-8
		byte[] convertedStringByte = convertThisText.getBytes(StandardCharsets.UTF_8);
		String convertedString = new String(convertedStringByte, StandardCharsets.UTF_8);
		return convertedString;
	}
}
