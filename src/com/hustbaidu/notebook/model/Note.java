package com.hustbaidu.notebook.model;

import java.io.Serializable;

public class Note implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8319892292202358625L;

	private int id;
	private String title;
	private String modifyDate;
	private String content;
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + id;
		result = prime * result
				+ ((modifyDate == null) ? 0 : modifyDate.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Note other = (Note) obj;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (id != other.id)
			return false;
		if (modifyDate == null) {
			if (other.modifyDate != null)
				return false;
		} else if (!modifyDate.equals(other.modifyDate))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	/**
	 * 
	 * @return the title of note
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * 
	 * @param title
	 * @return this to chain setters
	 */
	public Note setTitle(String title) {
		this.title = title;
		return this;
	}

	/**
	 * 
	 * @return modify date in format of <strong>YYYY年MM月dd日 hh:mm:ss</strong>
	 */
	public String getModifyDate() {
		return modifyDate;
	}

	public Note setModifyDate(String modifyDate) {
		this.modifyDate = modifyDate;
		return this;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content
	 *            the content to set
	 * @return this to chain setters
	 */
	public Note setContent(String content) {
		this.content = content;
		return this;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 * @return this to chain setters
	 */
	public Note setId(int id) {
		this.id = id;
		return this;
	}

}
