package cn.itcast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

public class Submit {
	/**
	 * 向指定URL发送GET方法的请求
	 * 
	 * @param url   发送请求的URL
	 * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return URL 所代表远程资源的响应结果
	 */

	/**
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url   发送请求的 URL
	 * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendPost(String param, String url) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
//			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
//			conn.setRequestProperty("emailToken", "shVNlj");
//			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 1.获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 2.中文有乱码的需要将PrintWriter改为如下
			// out=new OutputStreamWriter(conn.getOutputStream(),"UTF-8")
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		System.out.println("post推送结果：" + result);
		return result;
	}

	public static void main(String[] args) {

		JSONObject jsonObject2 = new JSONObject();
		jsonObject2.put("stuNo", "111701207");
		jsonObject2.put("stuName", "陈志明");
		jsonObject2.put("chinese", "90");
		jsonObject2.put("math", "90");
		jsonObject2.put("foreignLanguage", "90");
		jsonObject2.put("elseObj", "90");
		jsonObject2.put("sumG", 360);
		// 发送 POST 请求
		System.out.println(jsonObject2.toJSONString());
		sendPost(jsonObject2.toJSONString(), "http://192.168.25.128:8080/gradeManagement/submit");
//		String sr = Register.sendPost(jsonObject2.toJSONString(), "http://localhost:8080/register");
//		String sr = Register.sendPost(jsonObject2.toJSONString(), "http://192.168.195.128:8080/user/register"); 
//		String sr = Submit.sendPost(jsonObject2.toJSONString(), "http://134.175.99.234:8080/user/register"); 
//		System.out.println(sr);
	}
}