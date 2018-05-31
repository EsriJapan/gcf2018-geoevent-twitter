package com.esrij.geoevent.solutions.processor.lightgeocode;

public interface Address {

	public String createInsSql();

	public String getAddressText();

	public Double getX();

	public Double getY();

	public String getKey_code();

	public Short getAddressLevel();
}
