package br.com.tjgwp.business.entity.text;

public class BookMoreInfoVO {

	private String text;
	private String metric;
	private String ico;
	private boolean i18n = false;

	public BookMoreInfoVO() {
	}

	public BookMoreInfoVO(boolean i18n, String text) {
		this.text = text;
		this.i18n = i18n;
	}

	public BookMoreInfoVO(boolean i18n, String text, String ico) {
		this(i18n, text);
		this.ico = ico;
	}

	public BookMoreInfoVO(boolean i18n, String text, String ico, String metric) {
		this(i18n, text, ico);
		this.metric = metric;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getMetric() {
		return metric;
	}

	public void setMetric(String metric) {
		this.metric = metric;
	}

	public String getIco() {
		return ico;
	}

	public void setIco(String ico) {
		this.ico = ico;
	}

	public boolean isI18n() {
		return i18n;
	}

	public void setI18n(boolean i18n) {
		this.i18n = i18n;
	}

}
