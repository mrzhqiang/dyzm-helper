# HellGate
This is an android application, used for supporting web game:haowanba.com
### 1. Dependencies
App module build.gradle file    
  
[WeChat](https://open.weixin.qq.com/)  
`
compile files('libs/libammsdk.jar')
`
  
[CustomLogger](https://github.com/mrzhqiang/CustomLogger)  
`
compile 'com.github.mrzhqiang:CustomLogger:1.4'
`
  
[Dagger2](https://github.com/google/dagger)  
`
compile 'com.google.dagger:dagger:2.10'
annotationProcessor 'com.google.dagger:dagger-compiler:2.10'
`
  
[AutoValue](https://github.com/google/auto)  
`
provided 'com.google.auto.value:auto-value:1.3'
annotationProcessor 'com.google.auto.value:auto-value:1.3'
`
  
[AutoValue Gson](https://github.com/rharter/auto-value-gson)  
`
provided 'com.ryanharter.auto.value:auto-value-gson:0.4.6'
annotationProcessor 'com.ryanharter.auto.value:auto-value-gson:0.4.6'
`
  
[AutoValue Parcel of Android](https://github.com/rharter/auto-value-gson)  
`
compile 'com.ryanharter.auto.value:auto-value-parcel-adapter:0.2.5'
annotationProcessor 'com.ryanharter.auto.value:auto-value-parcel:0.2.5'
`
  
[RxAndroid](https://github.com/ReactiveX/RxAndroid)  
`
compile 'io.reactivex:rxandroid:1.2.1'
`
  
[RxJava](https://github.com/ReactiveX/RxJava)  
`
compile 'io.reactivex:rxjava:1.1.6'
`
  
[Gson](https://github.com/google/gson)  
`
compile 'com.google.code.gson:gson:2.8.0'
`
  
[Picasso](https://github.com/square/picasso)  
`
compile 'com.squareup.picasso:picasso:2.5.2'
compile 'com.jakewharton.picasso:picasso2-okhttp3-downloader:1.1.0'
`
  
[SqlBrite](https://github.com/square/sqlbrite)  
`
compile 'com.squareup.sqlbrite:sqlbrite:1.1.1'
`
  
[Retrofit2](https://github.com/square/retrofit)  
`
compile 'com.squareup.retrofit2:retrofit:2.2.0'
compile 'com.squareup.retrofit2:converter-gson:2.2.0'
compile 'com.squareup.retrofit2:converter-scalars:2.2.0'
compile 'com.squareup.retrofit2:adapter-rxjava:2.2.0'
compile 'com.squareup.okhttp3:logging-interceptor:3.6.0'
`
  
[Easy Permissions](https://github.com/googlesamples/easypermissions)  
`
compile 'pub.devrel:easypermissions:0.3.1'
`
  
[Jsoup](https://github.com/jhy/jsoup)  
`
compile 'org.jsoup:jsoup:1.10.2'
`
  
[Luban](https://github.com/Curzibn/Luban)  
`
compile 'top.zibin:Luban:1.0.9'
`
  
[bubbleview](https://github.com/lguipeng/BubbleView)  
`
compile 'com.lguipeng.bubbleview:library:1.0.0'
`
  
[autofittextview](https://github.com/grantland/android-autofittextview)  
`
compile 'me.grantland:autofittextview:0.2.1'
`
  
[ticker](https://github.com/robinhood/ticker)  
`
compile 'com.robinhood.ticker:ticker:1.1.1'
`
  
[MagicIndicator](https://github.com/hackware1993/MagicIndicator)  
`
compile 'com.github.hackware1993:MagicIndicator:1.5.0'
`
  
[goodview](https://github.com/venshine/GoodView)
`
compile 'com.wx.goodview:goodview:1.0.0'
`
  
### 2. TODO
* AutoValue is Db model
* POJO or Game's content of Object is app model
* Custom converter for wml source code
