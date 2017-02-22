package com.sprenkels.photoSafe;

import java.io.File;

public class FileData {

	File f;

	FileData(File f) {
		this.f = f;
	}
	
	@Override
	public boolean equals(Object o) {
	    // self check
	    if (this == o)
	        return true;
	    // null check
	    if (o == null)
	        return false;
	    // type check and cast
	    if (getClass() != o.getClass())
	        return false;
	    FileData fd = (FileData) o;
	    // field comparison
	    if (fd.f.length() != f.length()) {
	    	return false;
	    }
	    return true;
	}
	
}
