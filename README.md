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
- 1005：add points successfully
- 1006：add points failed
- 1007：logout successfully
- 1008：logout failed
#### Login
- Method：POST
- Header：Content-Type：application/json;charset=utf-8
- URL：http://134.175.99.234:8080/user/login
- Parameter：{"passwd":"password","userid":"userid"} (json)
- Response：{"usrname":"xxx","token":"token","status":"1001"} OR {"status":"1002"}
#### Register
- Method：POST
- Header：Content-Type：application/json;charset=utf-8
- Parameter：{"passwd":"password","userid":"userid","usrname":"username"}
- URL:http://134.175.99.234:8080/user/register
- Response：{"status":"1004"} OR {"status":"1003"}
#### Rank List
- Method：POST
- Header：Content-Type：application/json;charset=utf-8
- URL:http://134.175.99.234:8080/rankList/getRankList
- Parameter：{"count":"5","mapname":"mapname"}(json)
- Response：[{"rank":"1","usrname":"jimmy","points":"0"},{"rank":"2","usrname":"liyi","points":"0"}]
#### Logout
- Method：POST
- Header：Auth-Token：token
- URL：http://134.175.99.234:8080/user/logout
- Response：{"userid":"111701205","status":"1008"} OR {"status":"1009"}
- Case：setHeader("Auth-Token", token)
#### UpdatePoint
- Method：POST
- Header：Auth-Token：token
- URL：http://134.175.99.234:8080/rankList/updatePoints
- Parameter：{"mapname":"mapname","points":"65"}
- Response：{"status":"1005"} OR {"status":"1006"}
- Case：setHeader("Auth-Token", token);{"points":"5"}