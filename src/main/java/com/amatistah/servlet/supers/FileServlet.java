package com.amatistah.servlet.supers;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Servlet implementation class AjaxController2
 */
@Controller()
@RequestMapping("file")
public class FileServlet {
	private static final int DEFAULT_BUFFER_SIZE = 1024;
	private String filePath;
	private static HashMap<String, ResponseEntity<InputStreamResource>> cache = new HashMap<>();

	public FileServlet() {
		super();
		this.filePath = "C:\\Amatista\\Workspace\\Amatista\\Images";
	}

	@GetMapping(value = "/{fileName}", produces = MediaType.IMAGE_PNG_VALUE)
	public @ResponseBody ResponseEntity<InputStreamResource> getImageWithMediaType(@PathVariable String fileName)
			throws IOException {
		ResponseEntity<InputStreamResource> image;
		if (cache.containsKey(fileName)) {
			image = cache.get(fileName);
		} else {
			try {
			File file = new File(filePath, fileName);
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(file), DEFAULT_BUFFER_SIZE);
			image = ResponseEntity.ok().contentLength(file.length())
					.contentType(MediaType.parseMediaType(MediaType.IMAGE_PNG_VALUE)).body(new InputStreamResource(in));
			//cache.put(fileName, image); //comentado por fallas en 
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		return image;

	}

}