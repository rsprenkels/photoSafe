package com.sprenkels.photoSafe;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.log4j.Logger;

public class PhotoSafe {
	String baseDir;
	String newFilesStore = "additions";
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
					addFile(file);
				}				
			}
		}
	}

	private void addFile(File file) {
		ArrayList<FileData> alf = filesBySize.get(file.length());
		if (alf == null ) {
			alf = new ArrayList<FileData>();
			filesBySize.put(file.length(), alf);
		}
		if (alf.size() == 0) {
			alf.add(new FileData(file));
		} else {
			alf.add(new FileData(file));
			for (FileData fd : alf) {
				fd.fillCrc();
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
					copyFile(file);
					addFile(file);
					String message = String.format("ADDED size %9d file %s",
							file.length(), file.getPath());
					log.debug(message);
				} else {				
					String message = String.format("EXIST size %9d file %s",
							file.length(), file.getPath());
					log.debug(message);
				}
			}
		}
	}
	
	private void copyFile(File file) {
		String fileName = baseDir + File.separator + newFilesStore + File.separator + file.getName();
		try {
			Files.copy(Paths.get(file.getAbsolutePath()), Paths.get(fileName));
			log.debug("copied " + fileName);
		} catch (IOException e) {
			log.error("copy of " + fileName + " FAILED");
			e.printStackTrace();
		}
	}

	public void
	showContents() {
		log.debug("Contents:");
		for (Long size : filesBySize.keySet()) {
			if (filesBySize.get(size).size() < 1) {
				continue;
			}
			for (FileData f : filesBySize.get(size)) {
				log.debug(f.toString());
			}
		}
	}
}
