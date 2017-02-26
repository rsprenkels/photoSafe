package com.sprenkels.photoSafe;

public class MainPhotoSafe {

	public static void main(String[] args) {
//		PhotoSafe ps = new PhotoSafe("I:\\FotoVideoMASTER\\master\\2004");
		PhotoSafe ps = new PhotoSafe("I:\\FotoVideoMASTER\\master");
		ps.showContents();
		ps.checkDiff("I:\\FotoVideoMASTER\\fotovideoToevoegen");
		// ps.checkDiff("../additions");
		// ps.showContents();
	}
}
