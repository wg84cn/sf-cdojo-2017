## 项目说明
>#### 各模块说明
 工程           			| 描述              | 项目类型
 -------------|-------------|-----  
 smart-basic-depengent   	| 项目模块,所有模块的基础依赖      			| java	  
 smart-common-model      	| 项目模块，公告模型模块			| java	  
 smart-common-tookit    	| 项目模块，工具类封装模块			| java	
 smart-service-email        | 项目模块，邮件应用    			| java	 
 smart-webapp-page     	    | 项目模块，前端展现应用			| web	  
 smart-webapp-survey     	| 项目模块，前端展现应用			| web	
 pom.xml                 	| 项目模块			| java	  
 readme.md               	| 项目描述文件		| md 	  
>#### 项目打包说明
* 本地：mvn clean package -Plocal -Dmaven.test.skip=true
* 测试环境：mvn clean package -Psit -Dmaven.test.skip=true
* 生产环境：mvn clean package -Pproduct -Dmaven.test.skip=true

>#### 官方演示

>#### 关于我们
* 详见模块文件resume.md

