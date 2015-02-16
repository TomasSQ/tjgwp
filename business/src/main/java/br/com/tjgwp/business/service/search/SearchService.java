package br.com.tjgwp.business.service.search;

import java.util.ArrayList;
import java.util.List;

import br.com.tjgwp.business.entity.user.UserEntity;
import br.com.tjgwp.business.service.SuperService;
import br.com.tjgwp.domain.search.SearchDomain;

public class SearchService extends SuperService {

	private SearchDomain searchDomain = new SearchDomain();

	public List<SearchItemVO> search(String word) {
		List<SearchItemVO> items = new ArrayList<SearchItemVO>();

		for (UserEntity userEntity : searchDomain.searchUser(word))
			items.add(new SearchItemVO(userEntity));

		return items;
	}

}
