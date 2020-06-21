package com.amatistah.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.stereotype.Repository;

import com.amatistah.AmatistahApplication;

/**
 * Allows extraction of contents of a JAR file. All files matching a given Ant
 * path pattern will be extracted into a specified path. Copied from
 * http://stackoverflow.com/questions/5013917/can-i-serve-jsps-from-inside-a-jar-in-lib-or-is-there-a-workaround
 */
@Repository
public class JspExtractor {

	private String jarFile;
	private List<File> listOfCopiedFiles = new ArrayList<File>();

	public JspExtractor jspSupport() {
		String name = getJarName();
		final JspExtractor extractor = new JspExtractor(name);
		return extractor;
	}

	protected String getJarName() {
		String path = AmatistahApplication.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		String name = new java.io.File(path).getName();
		return name;
	}

	/**
	 * Creates a new instance of the JarFileResourcesExtractor
	 * 
	 * @param resourcePathPattern The Ant style path pattern (supports wildcards) of
	 *                            the resources files to extract
	 * @param jarFile             The jar file (located inside WEB-INF/lib) to
	 *                            search for resources
	 * @param destination         Target folder of the extracted resources. Relative
	 *                            to the context.
	 */
	public JspExtractor(String jarFile) {
		this.jarFile = jarFile;
	}

	public JspExtractor() {
		this.jarFile = getJarName();
	}

	@PreDestroy
	public void removeAddedFiles() throws IOException {
		for (File fileToRemove : listOfCopiedFiles) {
			if (fileToRemove.delete()) {
			}
		}
	}

	/**
	 * Extracts the resource files found in the specified jar file into the
	 * destination path
	 * 
	 * @throws IOException           If an IO error occurs when reading the jar file
	 * @throws FileNotFoundException If the jar file cannot be found
	 */
	@PostConstruct
	public void extractFiles() {
		// String path = servletContext.getRealPath("/WEB-INF/lib/" + jarFile);
		JarFile jarFile;
		try {
			jarFile = new JarFile(this.jarFile);

			try {
				System.out.println("extracting files");
				Enumeration<JarEntry> entries = jarFile.entries();
				while (entries.hasMoreElements()) {
					JarEntry entry = entries.nextElement();
					if (entry.getName().contains("resources") && !entry.isDirectory()) {
						String fileName = entry.getName();
						InputStream inputStream = jarFile.getInputStream(entry);
						File materializedJsp = new File(fileName);
						listOfCopiedFiles.add(materializedJsp);
						System.out.println("extracting files" + materializedJsp.getAbsolutePath());
						materializedJsp.getParentFile().mkdirs();
						materializedJsp.createNewFile();
						FileOutputStream outputStream = new FileOutputStream(materializedJsp);
						copyAndClose(inputStream, outputStream);
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				jarFile.close();
			}
		} catch (Exception e1) {
			//e1.printStackTrace();
		}
	}

	public static int IO_BUFFER_SIZE = 8192;

	private static void copyAndClose(InputStream in, OutputStream out) throws IOException {
		try {
			byte[] b = new byte[IO_BUFFER_SIZE];
			int read;
			while ((read = in.read(b)) != -1) {
				out.write(b, 0, read);
			}
		} finally {
			in.close();
			out.close();
		}
	}
}