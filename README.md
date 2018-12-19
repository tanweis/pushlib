[TOC]

#集成步骤  
>1.依赖pushLib  
>2.build.gradle 设置manifestPlaceholders 的appid,appkey等 见示例  
>3.设置小米魅族推送appId,key...  
>4.onTerminate调用  
>5.调用华为推送连接  HMSAgent.connect()  HMSAgent.Push.getToken()  方法,示例见sample  
>6.继承TanPushReceiver 自定义广播接收器  
>>           <receiver   
>>               android:exported="false"  
>>               android:name=".Receiver">  
>>               <intent-filter>  
>>                   <action android:name="com.ytsk.pushlib.message"/>  
>>                   <action android:name="com.ytsk.pushlib.token"/>  
>>               </intent-filter>  
>>            </receiver>  