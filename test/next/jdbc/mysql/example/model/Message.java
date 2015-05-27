package next.jdbc.mysql.example.model;

import java.util.Date;

import next.jdbc.mysql.annotation.Column;
import next.jdbc.mysql.annotation.Key;
import next.jdbc.mysql.annotation.Table;

@Table
public class Message {

	@Key(AUTO_INCREMENT = true)
	private Integer id;
	private Integer from;
	private Integer to;
	private Boolean checked;
	private String head;
	@Column(DATA_TYPE = "text", hasDefaultValue = false)
	private String message;
	private Date date;

	@Override
	public String toString() {
		return "Message [id=" + id + ", from=" + from + ", to=" + to + ", checked=" + checked + ", head=" + head + ", message=" + message + ", date="
				+ date + "]";
	}

	public Message() {

	}

	public Message(Integer from, Integer to, Boolean checked, String head, String message, Date date) {
		this.from = from;
		this.to = to;
		this.checked = checked;
		this.head = head;
		this.message = message;
		this.date = date;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setFrom(Integer from) {
		this.from = from;
	}

	public void setTo(Integer to) {
		this.to = to;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public void setHead(String head) {
		this.head = head;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getHead() {
		return head;
	}

	public Integer getId() {
		return id;
	}

	public Integer getFrom() {
		return from;
	}

	public Integer getTo() {
		return to;
	}

	public Boolean getChecked() {
		return checked;
	}

	public String getMessage() {
		return message;
	}

	public Date getDate() {
		return date;
	}

}
