package com.jsh.junitest.json;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ChannelUtil
{
	/**
	 * 解析页面json字符串为IPStream对象列表
	 * @param ipStreamStr json字符串
	 * @return IPStream对象列表
	 */
	public static List<IPStream> parseJson2IPStream(String ipStreamStr)
	{
		List<IPStream> ipList = new ArrayList<IPStream>();
		JSONArray ipJson = JSONObject.fromObject(ipStreamStr).getJSONArray("ipJsonData");
		if(null != ipJson)
		{
			for(int i = 0;i < ipJson.size(); i ++)
			{
				JSONObject tempJson = JSONObject.fromObject(ipJson.get(i));
				IPStream ip = new IPStream();
				ip.setIPformat(Short.parseShort(tempJson.getString("ipFomate")));
				ip.setIPnorms(Short.parseShort(tempJson.getString("IPnorms")));
				ip.setIPUrl(tempJson.getString("IPAddress"));
				ip.setPort(tempJson.getInt("port"));
				ip.setProtocol(Short.parseShort(tempJson.getString("protocol")));
				ip.setRate(Float.parseFloat(tempJson.getString("iprate")));
				ip.setType(Short.parseShort(tempJson.getString("inOrout")));
				ipList.add(ip);
			}
		}
		return ipList;
	}
	
	/**
	 * 解析页面json字符串为cableStream对象列表
	 * @param cableStreamStr json字符串
	 * @return cableStream对象列表
	 */
	public static List<CableStream> parseJson2CableStream(String cableStreamStr)
	{
		List<CableStream> cableList = new ArrayList<CableStream>();
		JSONArray cableJson = JSONObject.fromObject(cableStreamStr).getJSONArray("cableJsonData");
		if(null != cableJson)
		{
			for(int i = 0;i < cableJson.size(); i ++)
			{
				JSONObject tempJson = JSONObject.fromObject(cableJson.get(i));
				CableStream cable = new CableStream();
				cable.setCablehz(tempJson.getInt("cablehz"));
				cable.setCableMode(tempJson.getInt("cableMode"));
				cable.setCablenorms(Short.parseShort(tempJson.getString("cablenorms")));
				cable.setRate(Float.parseFloat(tempJson.getString("cablerate")));
				cable.setServiceNum(tempJson.getInt("serviceNum"));
				cable.setSymbolrate(tempJson.getInt("symbolrate"));
				cable.setTransNum(tempJson.getInt("transNum"));
				cableList.add(cable);
			}
		}
		return cableList;
	}
	
	public static void main(String[] args)
	{
		String ipStreamStr = "{ipJsonData : [{\"iprate\":\"512.00\",\"IPnorms\":\"0\",\"ipFomate\":\"0\",\"inOrout\":\"0\",\"port\":\"512\",\"IPAddress\":\"11.03.12.23\",\"protocol\":\"0\",\"ipJsonIndex\":0}]}";
		ChannelUtil.parseJson2IPStream(ipStreamStr);
		
		String cableStreamStr = "{cableJsonData:[{\"cablenorms\":\"0\",\"cablerate\":\"54\",\"serviceNum\":\"45\",\"transNum\":\"57\",\"cableMode\":\"64\",\"symbolrate\":\"56\",\"cablehz\":\"45\",\"cableJsonIndex\":0},{\"cablenorms\":\"0\",\"cablerate\":\"548\",\"serviceNum\":\"236\",\"transNum\":\"256\",\"cableMode\":\"64\",\"symbolrate\":\"56\",\"cablehz\":\"85\",\"cableJsonIndex\":1}]}";
		ChannelUtil.parseJson2CableStream(cableStreamStr);
	}
}
