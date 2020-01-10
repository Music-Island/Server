package cn.itcast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.alibaba.fastjson.JSONObject;

public class LogOut {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String result = LogOut.post("FF79EmDYtKfZHziqRJR9wHSN4DtyhT", "http://134.175.99.234:8080/user/logout");
//		String result = LogOut.post("JHZrvOKlgq0MIgcoGxfxblhUIOTRzS", "http://192.168.145.128:8080/user/logout");
		System.out.println(result);
	}
	public static String post(String token, String url){
		String result = "";
		HttpPost post = new HttpPost(url);
		try{
			CloseableHttpClient httpClient = HttpClients.createDefault();
        
			post.setHeader("Content-Type","application/json;charset=utf-8");
			post.setHeader("Auth-Token", token);
//			StringEntity postingString = new StringEntity(json.toJSONString(),"utf-8");
//			post.setEntity(postingString);
			HttpResponse response = httpClient.execute(post);
			
			InputStream in = response.getEntity().getContent();
			InputStreamReader isr = new InputStreamReader(in,"UTF-8");
			BufferedReader br = new BufferedReader(isr);
			StringBuffer sb= new StringBuffer();
			String line = null;
			while((line = br.readLine())!=null){
				sb.append(line);
			}
			br.close();
			isr.close();
			in.close();
			result = sb.toString();
			if(response.getStatusLine().getStatusCode()!=HttpStatus.SC_OK){
				result = "服务器异常";
			}
		} catch (Exception e){
			System.out.println("请求异常");
			throw new RuntimeException(e);
		} finally{
			post.abort();
		}
		return result;
	}
}
