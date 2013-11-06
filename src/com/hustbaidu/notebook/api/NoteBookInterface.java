package com.hustbaidu.notebook.api;
import java.util.List;

import com.hustbaidu.notebook.model.Note;

public interface NoteBookInterface {
	
	/**
	 *  save note to file or database
	 * @param note
	 * @return
	 */
	public boolean save (Note note);
	
	/**
	 * get all note
	 * @return List
	 */
	public List<Note> findAll();
	
	/**
	 * find by id, for database implement only
	 * @param id
	 * @return a note
	 */
	public Note findWithId (int id);
	
	/**
	 * 
	 * @param name
	 * @return
	 */
	public Note findWithName (String name);
	
	public boolean remove (Note note);
}
