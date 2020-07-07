package com.merchante.dto;

public class CommentDTO {

	private String user;
	private String comment;
	private String sport;
	private String id;

	public CommentDTO(String user, String comment, String sport) {
		this.user = user;
		this.comment = comment;
		this.sport = sport;
	}

	public String getuser() {
		return user;
	}

	public void setuser(String user) {
		this.user = user;
	}

	public String getcomment() {
		return comment;
	}

	public void setcomment(String comment) {
		this.comment = comment;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSport() {
		return sport;
	}

	public void setSport(String sport) {
		this.sport = sport;
	}

}
