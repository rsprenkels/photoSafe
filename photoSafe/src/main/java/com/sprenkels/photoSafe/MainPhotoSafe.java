package com.sprenkels.photoSafe;

public class MainPhotoSafe {

	public static void main(String[] args) {
		PhotoSafe ps = new PhotoSafe();
		ps.showContents();
		ps.checkDiff("../additions");
		ps.showContents();
		// ps.checkDiff("I:/FotoVideoMASTER/fotovideoToevoegen");
	}
}
