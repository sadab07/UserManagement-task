package com.example.User.Management.model;

public class Response {
	private String fileDownloadUri;
	private String fileName;
	private String contentType;
	private long fileSize;
	public String getFileDownloadUri() {
		return fileDownloadUri;
	}
	public void setFileDownloadUri(String fileDownloadUri) {
		this.fileDownloadUri = fileDownloadUri;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public long getFileSize() {
		return fileSize;
	}
	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}
	public Response(String fileDownloadUri, String fileName, String contentType, long fileSize) {
		super();
		this.fileDownloadUri = fileDownloadUri;
		this.fileName = fileName;
		this.contentType = contentType;
		this.fileSize = fileSize;
	}
	public Response() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "Response [fileDownloadUri=" + fileDownloadUri + ", fileName=" + fileName + ", contentType="
				+ contentType + ", fileSize=" + fileSize + "]";
	}
	
	
}