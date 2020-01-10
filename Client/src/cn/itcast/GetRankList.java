/**
 * 
 */
package cn.itcast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

/**
 * @author xxxminminxxx
 *
 */
public class GetRankList {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JSONObject jsonObject2 = new JSONObject();
		jsonObject2.put("count", "5");
		jsonObject2.put("mapname", "陈志明");
//		String result = Login.post(jsonObject2, "http://192.168.195.128:8080/rankList/getRankList");
		System.out.println(jsonObject2.toString());
		String result = Login.post(jsonObject2, "http://134.175.99.234:8080/rankList/getRankList");
		System.out.println(result);
	}

}
