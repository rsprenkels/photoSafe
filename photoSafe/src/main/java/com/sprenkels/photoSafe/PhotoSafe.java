package com.sprenkels.photoSafe;

import java.io.File;
import java.util.HashSet;

import org.apache.log4j.Logger;

public class PhotoSafe {
	String baseDir;
	final static Logger log = Logger.getLogger(PhotoSafe.class);
	HashSet<File> files; 
	
	PhotoSafe() {
		this("../fotos");
	}
	
	PhotoSafe(String baseDir) {
		this.baseDir =  baseDir;		
		files = new HashSet<File>();
		init();
	}
	
	public void
	checkDiff(String diffDir) {
		File[] faFiles = new File(diffDir).listFiles();
		for(File file: faFiles) {
			if(file.isDirectory()){
				checkDiff(file.getPath());
			} else {
				if(file.getName().matches("^(.*?)")){
					File foundPsFile = null;
					for (File psFile : files) {
						if(file.length() == psFile.length()) {
							foundPsFile = psFile;
							break;
						}
					}
					log.debug("Diffing file " + (foundPsFile == null ? "not found" : foundPsFile.getPath()) + " " + file.length() + " " + file.getPath());
				}				
			}
		}
	}

	void
	init(String baseDir) {
		File[] faFiles = new File(baseDir).listFiles();
		for(File file: faFiles) {
			if(file.isDirectory()){
				init(file.getPath());
			} else {
				if(file.getName().matches("^(.*?)")){
					log.debug("found file " + file.length() + " " + file.getPath());
					files.add(file);
				}				
			}
		}
	}
	
	private void init() {
		log.debug("initializing from baseDir " + baseDir);
		init(baseDir);
	}
}
