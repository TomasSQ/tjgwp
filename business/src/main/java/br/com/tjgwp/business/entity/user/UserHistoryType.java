package br.com.tjgwp.business.entity.user;

public enum UserHistoryType {

	NEW_BOOK {
		@Override
		protected String superType() {
			return "book";
		}
	}, CHANGED_BOOK {
		@Override
		protected String superType() {
			return "book";
		}
	}, NEW_CHAPTER {
		@Override
		protected String superType() {
			return "chapter";
		}
	}, CHANGED_CHAPTER {
		@Override
		protected String superType() {
			return "chapter";
		}
	}, CHANGED_BOOK_CAPE {
		@Override
		protected String superType() {
			return "book";
		}
	}, CHANGED_CHAPTER_CAPE {
		@Override
		protected String superType() {
			return "chapter";
		}
	},
	NEW_FOLLOWER {
		@Override
		protected String superType() {
			return "social";
		}
	}, NEW_FOLLOWING {
		@Override
		protected String superType() {
			return "social";
		}
	},
	CHANGED_PROFILE_PICTURE {
		@Override
		protected String superType() {
			return "social";
		}
	}, CHANGED_CAPE_PICTURE {
		@Override
		protected String superType() {
			return "social";
		}
	},
	SIGNED_IN {
		@Override
		protected String superType() {
			return "system";
		}
	}, SIGNED_OUT {
		@Override
		protected String superType() {
			return "system";
		}
	};

	protected abstract String superType(); 

	public boolean isBook() {
		return "book".equals(superType());
	}

	public boolean isChapter() {
		return "chapter".equals(superType());
	}

	public boolean hasPic() {
		return !equals(SIGNED_IN) && !equals(SIGNED_OUT);
	}
}
