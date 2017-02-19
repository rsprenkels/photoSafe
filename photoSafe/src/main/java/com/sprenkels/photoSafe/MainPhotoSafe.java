package com.sprenkels.photoSafe;

public class MainPhotoSafe {

	public static void main(String[] args) {
		PhotoSafe ps = new PhotoSafe("I:/FotoVideoMASTER");
		ps.checkDiff("../additions");
	}
}
