package com.falconraptor.utilities.files;

import java.util.AbstractList;
import java.util.ArrayList;

public class CSV {
    private Writer writer = null;
    private Reader reader = null;
    private ArrayList<String> lines = null;
	private int line = 0;

	public CSV (String name, Boolean w) {
		if (w) {
            writer = new Writer(name);
        } else {
            reader = new Reader(name);
        }
	}

	public CSV(String n){
		this(n,false);
	}

    public CSV close() throws Exception {
        if (writer == null) throw new Exception("Writer not available");
        writer.close();
        return this;
    }

    public CSV addRow(Object[] obj) throws Exception {
        if (writer == null) throw new Exception("Writer not available");
        for (int i = 0; i < obj.length; i++) writer.writeln(obj[i].toString() + ((obj.length - 1 == i) ? "" : ","));
        return this;
    }

    public <T extends AbstractList> CSV addRow(T obj) throws Exception {
        if (writer == null) throw new Exception("Writer not available");
        for (int i = 0; i < obj.size(); i++)
            writer.writeln(obj.get(i).toString() + ((obj.size() - 1 == i) ? "" : ","));
        return this;
    }

	public String readLine () throws Exception {
        if (reader == null) throw new Exception("Reader not available");
        if (lines == null) readAll();
		if (line >= lines.size() - 1) return null;
		return lines.get(line++);
	}

	public ArrayList<String> readAll () throws Exception {
        if (reader == null) throw new Exception("Reader not available");
        lines = reader.read();
        return lines;
	}
}
