package com.luv2code.jobportal.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.web.multipart.MultipartFile;

public class FileUploadUtil {

	//Purpose is to helping us save the file
	
	public static void saveFile(String uploadDir, String fileName,
		MultipartFile multipartFile) throws IOException 
	{
	//multipart will contain an image that user uploaded	
		
		Path uploadPath = Paths.get(uploadDir);
		//if directory not exists create it
		if(!Files.exists(uploadPath)) {
			Files.createDirectories(uploadPath);
			
		}
		
		
		try (InputStream inputStream = multipartFile.getInputStream()) 
		{
		//simply setup paht and print it
		Path path =	uploadPath.resolve(fileName);
		System.out.println("File path: "+path);
		System.out.println("File name: "+fileName);
		Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
		
		}
		
		catch(IOException e ) {
	
			throw new IOException("COULD not save image file"+fileName, e);
		}
 	}
	
}
