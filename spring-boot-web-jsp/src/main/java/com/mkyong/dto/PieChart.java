package com.mkyong.dto;

import java.util.List;

public class PieChart {

	private List<String> labels;
	
	private List<Double> series;

	public List<String> getLabels() {
		return labels;
	}

	public void setLabels(List<String> labels) {
		this.labels = labels;
	}

	public List<Double> getSeries() {
		return series;
	}

	public void setSeries(List<Double> series) {
		this.series = series;
	}
}
