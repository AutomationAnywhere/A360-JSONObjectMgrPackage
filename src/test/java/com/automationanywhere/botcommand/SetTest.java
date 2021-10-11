/*
 * Copyright (c) 2019 Automation Anywhere.
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
import java.util.HashMap;

import com.automationanywhere.botcommand.data.impl.StringValue;
import com.automationanywhere.botcommand.jsonhandler.Initialize;
import com.automationanywhere.botcommand.jsonhandler.Query;
import com.automationanywhere.botcommand.jsonhandler.Set;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;



public class SetTest {
  String goodJsonString = new String("{\"object1\":{\"object2\":\"value1\"},\"object3\":3,\"object4\":[{\"object5\":\"value5\"},{\"object6\":\"value6\"}]}");
  String failString = new String ("{ \"emotion\": { \"document\": { \"emotion\": { \"sadness\": 0.082208, "+
    "\"joy\": 0.074673,\"fear\": 0.050149,\"disgust\": 0.035402, \"anger\": 0.024366 } } } }");

  Map<String, Object> theMap = new HashMap<String, Object>();
  Initialize qqq = new Initialize();
  Initialize qq2 = new Initialize();
  Query q2 = new Query();
  Set iv = new Set();

  @BeforeTest
public void setUp() {
      qqq.setSessionMap(theMap);
      qq2.setSessionMap(theMap);
      q2.setSessionMap(theMap);
      iv.setSessionMap(theMap);
  }

  @Test
  public void setTest() {
    qqq.action("good", goodJsonString);
    qq2.action("failstring", failString);

    System.out.println(q2.query("good", "."));
    
    StringValue x = q2.query("good","object1['object2']");
    System.out.println(x);

    x = q2.query("good",".object4[*].object5");
    System.out.println(x);

    StringValue sv = iv.Set("good", ".object4[*].object5", "tim");
    System.out.println(sv);

     x = q2.query("good","object1.object2");
    System.out.println(x);

    System.out.println(q2.query("good", "."));
  }
}
