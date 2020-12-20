package com.yalizada.democrud;


import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

 // bu sinif proyektimizin jar yaxud war fayl kimi export olunmasini teyin edir
public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(DemoCrudApplication.class);
	}

}