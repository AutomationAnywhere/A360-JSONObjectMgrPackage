/*
 * Copyright (c) 2020 Automation Anywhere.
 * All rights reserved.
 *
 * This software is the proprietary information of Automation Anywhere.
 * You shall use it only in accordance with the terms of the license agreement
 * you entered into with Automation Anywhere.
 */

/**
 * 
 */
package com.automationanywhere.botcommand.jsonhandler;

import java.util.Map;

import static com.automationanywhere.commandsdk.model.AttributeType.TEXT;

import com.automationanywhere.botcommand.data.impl.StringValue;
import com.automationanywhere.commandsdk.annotations.BotCommand;
import com.automationanywhere.commandsdk.annotations.CommandPkg;
import com.automationanywhere.commandsdk.annotations.Execute;
import com.automationanywhere.commandsdk.annotations.Idx;
import com.automationanywhere.commandsdk.annotations.Pkg;
import com.automationanywhere.commandsdk.annotations.Sessions;
import com.automationanywhere.commandsdk.annotations.rules.NotEmpty;
import com.automationanywhere.commandsdk.i18n.Messages;
import com.automationanywhere.commandsdk.i18n.MessagesFactory;
import com.automationanywhere.commandsdk.model.DataType;
import com.jayway.jsonpath.*;


/**
 * @author JP Morgenthal
 *
 */
@BotCommand
@CommandPkg(label = "[[Initialize.label]]", 
description = "[[Initialize.description]]", icon = "iconblue.svg", name = "Initialize" ,
return_label = "[[Initialize.return_label]]", return_type = DataType.STRING, return_required = true)
public class Initialize {
	@Sessions
	private Map<String, Object> sessionMap;
    
	//Messages read from full qualified property file name and provide i18n capability.
	private static final Messages MESSAGES = MessagesFactory
			.getMessages("com.automationanywhere.botcommand.messages");

	// Ensure that a public setter exists.
	public void setSessionMap(Map<String, Object> sessionMap) {
		this.sessionMap = sessionMap;
		System.out.println("Session set");
	}

	@Execute
	public StringValue action(
			@Idx(index = "1", type = TEXT)
			@Pkg(label = "[[Initialize.session]]", default_value_type = DataType.STRING, 
			default_value = "Default", description = "e.g. Session1")
			@NotEmpty
			String sessionName, 
	
			@Idx(index="2", type = TEXT)
			@Pkg(label = "[[Initialize.value]]")
			@NotEmpty
			String jsonString)
		{
			try {
				//Object document = Configuration.defaultConfiguration().jsonProvider().parse(jsonString);
				DocumentContext document = JsonPath.parse(jsonString);
				System.out.println(document.toString());

				sessionMap.put(sessionName, document);
				return new StringValue("Success");
			} catch (Exception e) {
				System.out.println("parse failed "+sessionName);
				System.out.println(e.getMessage());
				return new StringValue(e.getMessage());
			}
		}
}
