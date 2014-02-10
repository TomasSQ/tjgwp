package br.com.tjgwp.business.entity;

import br.com.tjgwp.business.service.image.ImageService;

import com.google.appengine.api.blobstore.BlobKey;
import com.googlecode.objectify.annotation.EntitySubclass;

@EntitySubclass(index = false)
public class Image extends SuperEntity {

	private BlobKey blob;
	private String url;

	public BlobKey getBlob() {
		return blob;
	}

	public void updateBlob(BlobKey blob) {
		ImageService imageService = new ImageService();
		if (this.blob != null)
			imageService.deleteBlob(this.blob);

		if (blob != null) {
			this.blob = blob;

			setUrl(imageService.getUrlForBlob(blob));
		}
	}


	public void setBlob(BlobKey blob) {
		this.blob = blob;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
