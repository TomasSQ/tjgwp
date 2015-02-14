package br.com.tjgwp.business.service.image;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import br.com.tjgwp.business.entity.Image;
import br.com.tjgwp.business.service.SuperService;
import br.com.tjgwp.domain.SuperDomain;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.ServingUrlOptions;
import com.googlecode.objectify.Ref;

public class ImageService extends SuperService {

	private ImagesService imagesService = ImagesServiceFactory.getImagesService();
	private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

	public String getUploadUrl(String callbackUrl) {
		if (getUser() != null)
			return blobstoreService.createUploadUrl(callbackUrl);

		return "";
	}

	public List<String> getUploadUrls(List<String> callbacksUrls) {
		List<String> uploadUrls = new ArrayList<String>();

		if (callbacksUrls != null && getUser() != null)
			for (String callbackUrl : callbacksUrls)
				uploadUrls.add(blobstoreService.createUploadUrl(callbackUrl));

		return uploadUrls;
	}

	public String getUrlForBlob(BlobKey blob) {
		return imagesService.getServingUrl(ServingUrlOptions.Builder.withBlobKey(blob));
	}

	public void deleteBlob(BlobKey blob) {
		blobstoreService.delete(blob);
	}

	public BlobKey getBlobFromRequest(String name, HttpServletRequest req) {
		return blobstoreService.getUploads(req).get(name).get(0);
	}

	@SuppressWarnings("unchecked")
	public Ref<Image> newImageRef() {
		return (Ref<Image>) Ref.create(new SuperDomain().save(new Image()));
	}
}
