package br.com.tjgwp.business.entity;

import br.com.tjgwp.business.service.image.ImageService;

import com.google.appengine.api.blobstore.BlobKey;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Parent;
import com.googlecode.objectify.annotation.Subclass;

@Subclass(index = false)
public class Image extends SuperEntity {

	private BlobKey blob;
	@Parent
	private Ref<? extends SuperEntity> owner;

	public Image() {
	}

	public Image(Ref<? extends SuperEntity> owner) {
		this.owner = owner;
	}

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

	public Ref<? extends SuperEntity> getOwner() {
		return owner;
	}

	public void setOwner(Ref<? extends SuperEntity> owner) {
		this.owner = owner;
	}

	public void createUrl() {
	}

	@SuppressWarnings("unchecked")
	public Ref<Image> getRef() {
		return Ref.create(this);
	}
}
