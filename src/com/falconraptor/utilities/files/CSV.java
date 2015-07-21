package com.falconraptor.utilities.files;

import java.util.AbstractList;
import java.util.ArrayList;

public class CSV {
	private Write write = null;
	private Read read = null;
	private ArrayList<String> lines = null;
	private int line = 0;

	public CSV (String name, Boolean w) {
		if (w) {
			write = new Write();
			write.Write(name, false);
		} else {
			read = new Read();
			read.Read(name);
		}
	}

	public void close () throws Exception {
		if (write == null) throw new Exception("Writer not available");
		write.close();
	}

	public void addRow (Object[] obj) throws Exception {
		if (write == null) throw new Exception("Writer not available");
		for (int i = 0; i < obj.length; i++) write.writeln(obj[i].toString() + ((obj.length - 1 == i) ? "" : ","));
	}

	public <T extends AbstractList> void addRow (T obj) throws Exception {
		if (write == null) throw new Exception("Writer not available");
		for (int i = 0; i < obj.size(); i++)
			write.writeln(obj.get(i).toString() + ((obj.size() - 1 == i) ? "" : ","));
	}

	public String readLine () throws Exception {
		if (read == null) throw new Exception("Reader not available");
		if (lines == null) readAll();
		if (line >= lines.size() - 1) return null;
		return lines.get(line++);
	}

	public ArrayList<String> readAll () throws Exception {
		if (read == null) throw new Exception("Reader not available");
		lines = read.read();
		return lines;
	}
}
