package com.sprenkels.photoSafe;

import java.io.File;
import java.util.HashSet;

import org.apache.log4j.Logger;

public class PhotoSafe {
	String baseDir;
	final static Logger log = Logger.getLogger(PhotoSafe.class);
	HashSet files; 
	
	PhotoSafe() {
		baseDir =  "../fotos";
		files = new HashSet();
		init(baseDir);
	}
	
	void
	init(String baseDir) {
		File[] faFiles = new File(baseDir).listFiles();
		for(File file: faFiles) {
			if(file.isDirectory()){
				init(file.getAbsolutePath());
			} else {
				if(file.getName().matches("^(.*?)")){
					System.out.println(file.getAbsolutePath());
				}				
			}
		}
	}
	
	private void init() {
		log.debug("init " + baseDir);
	}
}
