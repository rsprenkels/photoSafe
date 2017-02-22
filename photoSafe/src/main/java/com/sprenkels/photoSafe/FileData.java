package com.sprenkels.photoSafe;

import java.io.File;
import java.io.IOException;

import net.boeckling.crc.CRC64;

public class FileData {

	private File f;
    private CRC64 crc;

	FileData(File f) {
		this.f = f;
	}
	
	@Override
	public String toString() {
		String crcAsString = crc == null ? "----------------" : String.format("%08X", crc.getValue());
		return String.format("%s %9d %s", crcAsString, f.length(), f.getPath());
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
	    fillCrc();
	    fd.fillCrc();
	    return crc.getValue() == fd.crc.getValue();
	}

	protected void fillCrc() {
		if (crc == null) {
			try {
				crc = CRC64.fromFile(f);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
