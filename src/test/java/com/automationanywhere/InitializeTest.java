package com.automationanywhere.botcommand.jsonhandler;

import java.util.HashMap;
import java.util.Map;

import com.automationanywhere.botcommand.jsonhandler.Initialize;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;

public class InitializeTest {
  
    String goodJsonString = new String("{\"object1\":{\"object2\":\"value1\"},\"object3\":\"jeff\"}");
    String badJsonString = new String("{\"object1\"-{\"object2\":\"value1\"},\"object3\":\"jeff\"}");

    Map<String, Object> theMap = new HashMap<String, Object>();
    Initialize qqq = new Initialize();

    @BeforeTest
	public void setUp() {
        qqq.setSessionMap(theMap);
    }

  @Test
  public void initializeTest() {
    qqq.action("good", goodJsonString);
    Assert.assertTrue(theMap.get("good")!=null);
    qqq.action("bad", badJsonString);
    Assert.assertTrue(theMap.get("bad") == null);
  }
}