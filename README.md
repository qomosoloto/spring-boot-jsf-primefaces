#一个后台管理系统，刚接触jsf一个月左右时间做的，做的还不完善．
项目spring boot ,  jpa ,jsf , primefaces ,druid等．有文件导入，七牛图片上传，二维码生成等功能．

#运行
1.  新建一个maven父工程,导入本工程及 data-model[再libs文件夹下] 作为子模块,

>
  mvn install:install-file
  -DgroupId=com.shenbian.ng
  -DartifactId=data-model
  -Dversion=0.1-SNAPSHOT
  -Dpackaging=jar
  -Dfile=data-model-0.1-SNAPSHOT.jar


#其他
1.  使用@ManagedBean注解，只是方便在xhtml中代码提示方便使用，真正起作用的是@Componet注解。
