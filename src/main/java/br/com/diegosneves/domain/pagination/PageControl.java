package br.com.diegosneves.domain.pagination;

public record PageControl(
	String search,
	int page,
	int perPage,
	String sort,
	String direction
) {

	public static PageControl of(
		final String search,
		final int page,
		final int perPage,
		final String sort,
		final String direction
	) {
		return new PageControl(search, page, perPage, sort, direction);
	}

}
