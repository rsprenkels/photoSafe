package com.sprenkels.photoSafe;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import org.apache.log4j.Logger;

public class PhotoSafe {
	String baseDir;
	final static Logger log = Logger.getLogger(PhotoSafe.class);
	HashMap<Long, ArrayList<File>> filesBySize;
	
	PhotoSafe() {
		this("../theSafe");
	}
	
	PhotoSafe(String baseDir) {
		this.baseDir = baseDir;		
		filesBySize = new HashMap<Long, ArrayList<File>>();
		init();
	}
			
	void
	init(String baseDir) {
		File[] faFiles = new File(baseDir).listFiles();
		for(File file: faFiles) {
			if(file.isDirectory()){
				init(file.getPath());
			} else {
				if(isRelevantFile(file)){
					log.debug("found file " + file.length() + " " + file.getPath());
					ArrayList<File> alf = filesBySize.get(file.length());
					if (alf == null ) {
						alf = new ArrayList<File>();
						filesBySize.put(file.length(), alf);
					}
					alf.add(file);
				}				
			}
		}
	}
	
	private void init() {
		log.debug("initializing from baseDir " + baseDir);
		init(baseDir);
	}

	boolean
	isRelevantFile(File f) {
		return f.getName().matches("^(.*?)");
	}

	boolean
	containsFile(File f) {
		return filesBySize.containsKey(f.length());
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
	
	public void
	showContents() {
		for (Long size : filesBySize.keySet()) {
			if (filesBySize.get(size).size() < 1) {
				continue;
			}
			for (File f : filesBySize.get(size)) {
				String message = String.format("size %9d file %s", f.length(), f.getPath());
				log.debug(message);
			}
		}
	}
}
