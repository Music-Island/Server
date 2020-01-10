package cn.itcast;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class UploadFile {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		uploadFile("C:\\Users\\xxxminminxxx\\Desktop\\kill_folder.7z");

	}

	public static void uploadFile(String fileName) {
		try {

			// 换行符
			final String newLine = "\r\n";
			// 数据分隔线
//			final String BOUNDARY = "【这里随意设置】";// 可以随意设置，一般是用 ---------------加一堆随机字符
			final String BOUNDARY = "--m-u-s-i-c-I-s-l-a-n-d--";// 可以随意设置，一般是用 ---------------加一堆随机字符
			// 文件结束标识
			final String boundaryPrefix = "--";

			// 服务器的域名
//			URL url = new URL("http://192.168.25.128:8080/getFileFromUser/FileUpload");
			URL url = new URL("http://134.175.99.234:8080/getFileFromUser/FileUpload");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			// 设置为POST情
			conn.setRequestMethod("POST");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			// conn.setDoInput(true);/不必加，默认为true
			// conn.setUseCaches(false);//用于设置缓存，默认为true，不改也没有影响（至少在传输单个文件这里没有）

			// 设置请求头参数
			// 关于keep-alive的说明：https://www.kafan.cn/edu/5110681.html
			// conn.setRequestProperty("connection",
			// "Keep-Alive");//现在的默认设置一般即为keep-Alive，因此此项为强调用，可以不加
			// conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0;
			// Windows Nt 5.1; SV1)");//用于模拟浏览器，非必须

			// 用于表示上传形式，必须
			conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
			
			// 这里是Charset，网上大多都是Charsert？？？我的天，笑哭。不过好像没什么影响...不知道哪位大佬解释一下
			conn.setRequestProperty("Charset", "UTF-8");

			// 获取conn的输出流用于向服务器输出信息
			OutputStream out = new DataOutputStream(conn.getOutputStream());

			// 构造文件的结构
			// 写参数头
			StringBuilder sb = new StringBuilder();
			sb.append(boundaryPrefix)// 表示报文开始
					.append(BOUNDARY)// 添加文件分界线
					.append(newLine);// 换行，换行方式必须严格约束
			
			File file = new File(fileName);
			// 固定格式,其中name的参数名可以随意修改，只需要在后台有相应的识别就可以，filename填你想要被后台识别的文件名，可以包含路径
			sb.append("Content-Disposition: form-data;name=\"file\";").append("filename=\"").append(file.getName())
					.append("\"").append(newLine);
			sb.append("Content-Type:application/octet-stream");
			// 换行，为必须格式
			sb.append(newLine);
			sb.append(newLine);

			// 将参数头的数据写入到输出流中
			out.write(sb.toString().getBytes());
			System.out.print(sb);

			// 写文件数据（通过数据输入流）
			DataInputStream in = new DataInputStream(new FileInputStream(file));
			byte[] bufferOut = new byte[1024];
			int bytes = 0;
			// 每次读1KB数据,并且将文件数据写入到输出流中
			while ((bytes = in.read(bufferOut)) != -1) {
				out.write(bufferOut, 0, bytes);
			}
			in.close();

			// 写参数尾
			out.write(newLine.getBytes());
			System.out.print(new String(newLine.getBytes()));

			// 定义最后数据分隔线，即--加上BOUNDARY再加上--。
			sb = new StringBuilder();
			sb.append(newLine).append(boundaryPrefix).append(BOUNDARY).append(boundaryPrefix).append(newLine);
			// 写上结尾标识
			out.write(sb.toString().getBytes());
			System.out.println(sb);
			// 输出结束，关闭输出流
			out.flush();
			out.close();

			// 定义BufferedReader输入流来读取URL的响应 ,注意必须接受来自服务器的返回，否则服务器不会对发送的post请求做处理！！这里坑了我好久
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = null;
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
			}

		} catch (Exception e) {
			System.out.println("发送POST请求出现异常！" + e);
			e.printStackTrace();
		}
	}

}
