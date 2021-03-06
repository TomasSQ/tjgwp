package br.com.tjgwp.business.service.search;

import java.util.Map;

import br.com.tjgwp.business.entity.text.Book;
import br.com.tjgwp.business.entity.user.UserEntity;
import br.com.tjgwp.business.utils.URLMaker;

public class SearchItemVO {

	private SearchItemType type;
	private String url;
	private String name;
	private String subtitle;
	private String imageUrl;
	private String description;
	private Map<String, String> moreInfo;

	public SearchItemVO(UserEntity userEntity) {
		type = SearchItemType.USER;
		url = URLMaker.urlForUser(userEntity);
		name = userEntity.getNickname();
		imageUrl = userEntity.getProfile().getUrl();
	}

	public SearchItemVO(Book book) {
		type = SearchItemType.BOOK;
		url = URLMaker.urlForBook(book);
		name = book.getTitle();
		subtitle = book.getOwner().get().getNickname();
		imageUrl = book.getCape().getUrl();
	}

	public SearchItemType getType() {
		return type;
	}

	public void setType(SearchItemType type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Map<String, String> getMoreInfo() {
		return moreInfo;
	}

	public void setMoreInfo(Map<String, String> moreInfo) {
		this.moreInfo = moreInfo;
	}

}
