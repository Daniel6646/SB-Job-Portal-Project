package com.luv2code.jobportal.config;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

	//Purpose :
	// This config class will map requests for /photos to serve file from a directory on our filesystem

	private static String UPLOAD_DIR = "photos";
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {

// OVerride default implementation to setup a custom resource handler.

	exposedDirectory(UPLOAD_DIR,registry);
	
	}

	private void exposedDirectory(String uPLOAD_DIR2, ResourceHandlerRegistry registry) {

		Path path = Paths.get(UPLOAD_DIR);
		registry.addResourceHandler("/" + UPLOAD_DIR + "/**").addResourceLocations("file:"+path.toAbsolutePath()+"/");
//converts upload dir string to a path
// Maps requests starting with "/photos/**" to a file systemn location file: <absolute path to photos directory>
//** will match sub-directories		
//  /** match all sub directories
	}

	
	
	
}
