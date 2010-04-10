package edu.udel.cis.cisc475.rex.source.examstubs;

import java.io.PrintWriter;

import edu.udel.cis.cisc475.rex.source.IF.SourceIF;


/**
 * @author hboyd
 *
 */
public class SourceStub implements SourceIF {
	String s_filename;
	String s_text;
	int s_startLine = 0;
	int s_startColumn = 0;
	int s_lastLine = 0;
	int s_lastColumn = 0;
	
	
	public SourceStub(String filename) {
		this.s_filename = filename;
	}

	@Override
	public void addText(String text) {
		// TODO Auto-generated method stub
		this.s_text = text;
	}

	@Override
	public String filename() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int lastColumn() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int lastLine() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setLastColumn(int column) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setLastLine(int line) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setStartColumn(int column) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setStartLine(int line) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int startColumn() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int startLine() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String text() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void write(PrintWriter out) {
		// TODO Auto-generated method stub
		
	}

}
