package com.mkyong.dto;

import java.util.List;

public class BarChart {

	private List<String> labels;
	
	private List<List<Double>> series;

	public List<String> getLabels() {
		return labels;
	}

	public void setLabels(List<String> labels) {
		this.labels = labels;
	}

	public List<List<Double>> getSeries() {
		return series;
	}

	public void setSeries(List<List<Double>> series) {
		this.series = series;
	}
	
}
