package br.com.tjgwp.business.service.image;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

import br.com.tjgwp.business.service.Service;

public class ImageService extends Service {

	public String getUploadUrl(String callback) {
		if (getUser() != null)
			return BlobstoreServiceFactory.getBlobstoreService().createUploadUrl(callback);
		return "";
	}

	public void getImage(String image, HttpServletResponse response) throws IOException {
		BlobstoreServiceFactory.getBlobstoreService().serve(new BlobKey(image), response);
	}
}
