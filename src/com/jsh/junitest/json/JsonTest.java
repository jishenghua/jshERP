package com.jsh.junitest.json;

import junit.framework.TestCase;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JsonTest extends TestCase 
{
    public void testJson()
    {
        JSONObject outer = new JSONObject();
        outer.put("total", 8);
        JSONArray json = new JSONArray();
//      {"total":28,"rows":[
//      {"productid":"AV-CB-01","attr1":"Adult Male","itemid":"EST-18"}
//  ]}
        
        for(int i = 0 ;i < 10 ;i ++)
        {
            JSONObject jo = new JSONObject();
            jo.put("name", "andy" + i);
            jo.put("sex", "false" + i);
            json.add(jo);
        }
        outer.put("rows", json);
        System.out.println(outer.toString());
    }
}
