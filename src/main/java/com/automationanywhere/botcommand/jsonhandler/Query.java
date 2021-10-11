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

import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.text.Document;

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
import com.jayway.jsonpath.internal.filter.ValueNode.JsonNode;
import com.jayway.jsonpath.spi.json.JsonOrgJsonProvider;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
/**
 * @author JP Morgenthal
 *
 */
@BotCommand
@CommandPkg(label = "[[Query.label]]", 
description = "[[Query.description]]", icon = "iconblue.svg", name = "Query" ,
return_label = "[[Query.return_label]]", return_type = DataType.STRING, return_required = true)
public class Query {
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
	public StringValue query(
			@Idx(index = "1", type = TEXT)
			@Pkg(label = "[[Query.session]]", default_value_type = DataType.STRING, 
				default_value = "Default", description = "e.g. Session1")
			@NotEmpty final
			String sessionName, 
	
			@Idx(index="2", type = TEXT)
			@Pkg(label = "[[Query.value]]")
			@NotEmpty final
			String jsonQuery)
		{
			Object o = sessionMap.get(sessionName);
			if (o == null)
				return new StringValue("No Session Name "+sessionName+" found");

			DocumentContext doc = (DocumentContext) o;
			Object val1;
			
            try {
				if (jsonQuery == ".")
					val1 = doc.jsonString();
				else
                	val1 = doc.read("$."+jsonQuery);
            } catch (com.jayway.jsonpath.PathNotFoundException e) {
                return new StringValue("Path not found");
			}

			System.out.println(val1.getClass().getName());

			String retStr=null;

			switch(val1.getClass().getName()) {
				case "java.util.LinkedHashMap":
					JSONObject j = new JSONObject((LinkedHashMap)val1);
					retStr = j.toJSONString();
					System.out.println("X-"+retStr);
					break;
				case "java.lang.String":
					retStr = (String)val1;
					System.out.println("X-"+retStr);
					break;
				case "java.lang.Integer":
				case "java.lang.Double":
					retStr = val1.toString();
					break;
				case "net.minidev.json.JSONArray":
					JSONArray jj = (JSONArray) val1;
					retStr = jj.toJSONString();
					System.out.println("X-"+retStr);
			
			}
			return new StringValue(retStr);
		}
}
