package com.hustbaidu.notebook.file;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import com.hustbaidu.notebook.api.NoteBookInterface;
import com.hustbaidu.notebook.model.Note;

import android.content.Context;

public class NoteFileAPI implements NoteBookInterface {

	// 和activity交互的上下文对象
	private Context context;

	// 存储note的文件夹
	private File noteDir = null;

	// 工厂模式的私有对象
	private static NoteFileAPI mApi;

	// 格式化日期的format
	private SimpleDateFormat format;

	//
	private HashMap<String, Note> noteMap;

	private NoteFileAPI(Context context) {
		this.context = context;
		format = new SimpleDateFormat("yyyy年MM月dd日 hh:mm:ss", Locale.CHINA);
	}

	/**
	 * 最好传递ApplicationContext给NoteAPi
	 * 
	 * @param context
	 * @return
	 */
	public static NoteFileAPI getInstance(Context context) {
		if (mApi != null) {
			mApi.context = context;
			return mApi;
		} else {
			mApi = new NoteFileAPI(context);
			mApi.noteDir = mApi.getNoteDir();
			return mApi;
		}
	}

	private File getNoteDir() {
		File file = this.context.getDir("notes", Context.MODE_PRIVATE);
		return file;
	}

	@Override
	public boolean save(Note note) {
		
		if(noteMap != null){
			if(!noteMap.containsKey(note.getTitle()))
				noteMap.put(note.getTitle(), note);
		} else {
			noteMap = new HashMap<String, Note>();
			noteMap.put(note.getTitle(), note);
		}
		
		try {
			File noteFile = new File(this.noteDir, note.getTitle());
			if(!noteFile.exists())
				noteFile.createNewFile();
			FileOutputStream outputStream = new FileOutputStream(noteFile);
			outputStream.write(note.getContent().getBytes());
			Date date = new Date();
			note.setModifyDate(format.format(date));
			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public List<Note> findAll() {
		// TODO Auto-generated method stub

		if (noteMap != null) {
			ArrayList<Note> list = new ArrayList<Note>();
			for (String key : noteMap.keySet()) {
				list.add(noteMap.get(key));
			}
			return list;
		} else {
			noteMap = new HashMap<String, Note>();
		}
		try {
			File[] files = noteDir.listFiles();
			Date date = new Date();
			Note note;
			StringBuffer content;
			Scanner scanner;
			ArrayList<Note> notes = new ArrayList<Note>(files.length);
			for (File file : files) {
				note = new Note();
				content = new StringBuffer();
				content.toString();
				note.setTitle(file.getName());
				date.setTime(file.lastModified());
				note.setModifyDate(format.format(date));
				scanner = new Scanner(file);
				while (scanner.hasNext()) {
					content.append(scanner.next());
				}
				note.setContent(content.toString());
				scanner.close();
				notes.add(note);
				noteMap.put(note.getTitle(), note);
				return notes;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
		return null;
	}

	/**
	 * 这个方法不适用与用文件实现存储的API
	 * 
	 * @return null
	 */
	@Deprecated
	@Override
	public Note findWithId(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Note findWithName(String name) {
		// TODO Auto-generated method stub
		if (name == null || name.equals("")) {
			return null;
		}

		if (noteMap != null && noteMap.containsKey(name)) {
			return noteMap.get(name);
		}

		Note note = new Note();
		try {
			File file = new File(noteDir, name);
			note.setTitle(name);
			Scanner scanner = new Scanner(file);
			StringBuffer buffer = new StringBuffer();
			while (scanner.hasNext()) {
				buffer.append(scanner.next());
			}
			scanner.close();
			note.setContent(buffer.toString());
			Date date = new Date();
			date.setTime(file.lastModified());
			note.setModifyDate(format.format(date));

			noteMap.put(name, note);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
		return note;
	}

	@Override
	public boolean remove(Note note) {
		// TODO Auto-generated method stub

		if (noteMap.containsKey(note.getTitle()))
			noteMap.remove(note);
		try {
			File file = new File(noteDir, note.getTitle());
			if (file.exists()) {
				file.delete();
				return true;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
		return false;
	}

}
