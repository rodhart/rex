package edu.udel.cis.cisc475.rex.source.uefstubs;

import java.io.PrintWriter;

import edu.udel.cis.cisc475.rex.source.IF.SourceIF;

public class Source implements SourceIF {

	private String text;
	private String filename;
	private int startColumn;
	private int startLine;
	private int lastColumn;
	private int lastLine;

	public Source(String filename) {
		this.filename = filename;
	}

	@Override
	public void addText(String text) {
		this.text = text;
	}

	@Override
	public String filename() {
		return this.filename;
	}

	@Override
	public int lastColumn() {
		return this.lastColumn;
	}

	@Override
	public int lastLine() {
		return this.lastLine;
	}

	@Override
	public void setLastColumn(int column) {
		this.lastColumn = column;
	}

	@Override
	public void setLastLine(int line) {
		this.lastLine = line;
	}

	@Override
	public void setStartColumn(int column) {
		this.startColumn = column;
	}

	@Override
	public void setStartLine(int line) {
		this.startLine = line;
	}

	@Override
	public int startColumn() {
		return this.startColumn;
	}

	@Override
	public int startLine() {
		return this.startLine;
	}

	@Override
	public String text() {
		return this.text;
	}

	@Override
	public void write(PrintWriter out) {
		out.println(text());
	}

}
