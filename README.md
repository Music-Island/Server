## Welcome to Music-Island Server!
### What Is It?
The Music-Island® Server is an open source implementation of the Apache Tomcat
technologies. The Server responds to the [Music-Island® Client](https://github.com/Music-Island/Client) demand.
### Documentation
Tips：update at any time
#### Response status
- 0000：parameter error
- 1001：login successfully
- 1002：login failed
- 1003：registered successfully
- 1004：registration failed
#### Login
- Method：POST
- Header：Content-Type：application/json;charset=utf-8
- URL：http://134.175.99.234:8080/user/login
- Response：{"status":"1001"} OR {"status":"1002"}
- Case：{"passwd":"123456","userid":"111701265"}
#### Register
- Method：POST
- Header：Content-Type：application/json;charset=utf-8
- URL:http://134.175.99.234:8080/user/register
- Response：{"status":"1004"} OR {"status":"1003"}
- Case：{"passwd":"123456","userid":"111701205","usrname":"jimmy"}
#### Rank List
- Method：GET
- URL:http://134.175.99.234:8080/rankList/rankList
- Parameter：count（）
- Response：[{"rank":"1","usrname":"jimmy","points":0},{"rank":"2","usrname":"liyi","points":0}]
- Case：http://134.175.99.234:8080/rankList/rankList?count=5
