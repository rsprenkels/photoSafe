package com.sprenkels.photoSafe;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.log4j.Logger;

public class PhotoSafe {
	String baseDir;
	final static Logger log = Logger.getLogger(PhotoSafe.class);
	HashMap<Long, ArrayList<FileData>> filesBySize;
	
	PhotoSafe() {
		this("../theSafe");
	}
	
	PhotoSafe(String baseDir) {
		this.baseDir = baseDir;		
		filesBySize = new HashMap<Long, ArrayList<FileData>>();
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
					ArrayList<FileData> alf = filesBySize.get(file.length());
					if (alf == null ) {
						alf = new ArrayList<FileData>();
						filesBySize.put(file.length(), alf);
					}
					if (alf.size() == 0) {
						alf.add(new FileData(file));
					} else {
						
					}
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
	containsFile(File thisFile) {
		if (!filesBySize.containsKey(thisFile.length())) {
			return false;
		}
		FileData tfd = new FileData(thisFile);
		for (FileData fd : filesBySize.get(thisFile.length())) {
			if (fd.equals(tfd)) {
				return true;
			}
		}
		return false;
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
			for (FileData f : filesBySize.get(size)) {
				String message = String.format("size %9d file %s", f.f.length(), f.f.getPath());
				log.debug(message);
			}
		}
	}
}
