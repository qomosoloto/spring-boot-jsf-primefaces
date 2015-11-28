#admin-springboot

#运行
1.  新建一个maven父工程,导入本工程及 data-model 作为子模块,启动运行

mvn install:install-file
-DgroupId=com.shenbian.ng
-DartifactId=data-model
-Dversion=0.1-SNAPSHOT
-Dpackaging=jar
-Dfile=data-model-0.1-SNAPSHOT.jar


#其他
1.  使用@ManagedBean注解，只是方便在xhtml中代码提示方便使用，真正起作用的是@Componet注解。
