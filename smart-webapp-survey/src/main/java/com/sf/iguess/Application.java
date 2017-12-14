package com.sf.iguess;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * ClassName: Application <br/>
 * Function: 主程序入口 <br/>
 * date: 2017年12月14日 下午8:52:28 <br/>
 *
 * @author 618721
 * @version 
 * @since JDK 1.8
 */
@SpringBootApplication  
@EnableTransactionManagement
@ServletComponentScan
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);	
	}
}