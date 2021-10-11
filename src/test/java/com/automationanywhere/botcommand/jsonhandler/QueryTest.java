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

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;



public class QueryTest {
  String goodJsonString = new String("{\"object1\":{\"object2\":\"value1\"},\"object3\":3}");
  String arrayString = new String ("{'permissions':[{'object1':'object2'},{'object3':'object4'}]}");
  String failString = new String ("{ \"emotion\": { \"document\": { \"emotion\": { \"sadness\": 0.082208, "+
    "\"joy\": 0.074673,\"fear\": 0.050149,\"disgust\": 0.035402, \"anger\": 0.024366 } } } }");

  Map<String, Object> theMap = new HashMap<String, Object>();
  Initialize qqq = new Initialize();
  Initialize qqa = new Initialize();
  Initialize qqb = new Initialize();
  Query q2 = new Query();

  @BeforeTest
public void setUp() {
      qqq.setSessionMap(theMap);
      qqa.setSessionMap(theMap);
      qqb.setSessionMap(theMap);
      q2.setSessionMap(theMap);
  }

  @Test
  public void queryTest() {
    qqq.action("good", goodJsonString);
    qqa.action("array", arrayString);
    qqb.action("failstring", failString);

    StringValue x = q2.query("good","object1");
    System.out.println(x.toString());
    System.out.println(q2.query("good","object3"));
    Assert.assertEquals(x.get(), "{\"object2\":\"value1\"}");

    System.out.println(q2.query("good", "blah"));

    StringValue x3 = q2.query("failstring", "emotion.document.emotion.sadness");
    System.out.println("X3="+x3);

    System.out.println(q2.query("array","permissions"));
    System.out.println(q2.query("array","permissions.length()"));

    StringValue x2 = q2.query("bad","object1.object2");
    Assert.assertTrue(x2.get().startsWith("No"));
  }
}
