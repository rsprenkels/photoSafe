package com.sprenkels.photoSafe;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;

import org.apache.log4j.Logger;

public class PhotoSafe {
	String baseDir;
	final static Logger log = Logger.getLogger(PhotoSafe.class);
	HashMap<Long, File> filesBySize;
	
	PhotoSafe() {
		this("../theSafe");
	}
	
	PhotoSafe(String baseDir) {
		this.baseDir = baseDir;		
		filesBySize = new HashMap<Long, File>();
		init();
	}
		
	public void
	checkDiff(String diffDir) {
		File[] faFiles = new File(diffDir).listFiles();
		for(File file: faFiles) {
			if(file.isDirectory()){
				checkDiff(file.getPath());
			} else {
				if(isRelevantFile(file) && !containsFile(file)) {
					log.debug("NEW      " + file.getPath());
				} else {				
					log.debug("EXISTING " + file.getPath());
				}
			}
		}
	}
	
	boolean
	isRelevantFile(File f) {
		return f.getName().matches("^(.*?)");
	}

	boolean
	containsFile(File f) {
		return filesBySize.containsKey(f.length());
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
					filesBySize.put(file.length(), file);
				}				
			}
		}
	}
	
	private void init() {
		log.debug("initializing from baseDir " + baseDir);
		init(baseDir);
	}
}
