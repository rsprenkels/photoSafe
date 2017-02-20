package com.sprenkels.photoSafe;

public class MainPhotoSafe {

	public static void main(String[] args) {
		PhotoSafe ps = new PhotoSafe("I:/FotoVideoMASTER/master");
		ps.checkDiff("../additions");
		ps.checkDiff("I:/FotoVideoMASTER/fotovideoToevoegen");
	}
}
