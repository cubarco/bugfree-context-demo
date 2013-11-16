package com.hustbaidu.notebook.activity;

import com.hustbaidu.notebook.R;
import com.hustbaidu.notebook.api.NoteBookInterface;
import com.hustbaidu.notebook.file.NoteFileAPI;
import com.hustbaidu.notebook.model.Note;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditorActivity extends Activity {
	
	public static final String TAG = "EditorActivity";
	
	Button confirm;
	EditText title;
	EditText content;
	String oldName = null;
	NoteBookInterface mApi;
	Note note;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_editor);
		findViews();
		confirm.setOnClickListener(new ConfirmBtnListener());
		mApi = NoteFileAPI.getInstance(getApplicationContext());
		Intent intent = getIntent();
		oldName = intent.getStringExtra("title");
		if(oldName != null) {
			note = mApi.findWithName(oldName);
			title.setText(note.getTitle());
			content.setText(note.getContent());
			title.setEnabled(false);
		} else {
			note = new Note();
		}
	}
	
	void findViews(){
		confirm  = (Button) findViewById(R.id.editor_confirm);
		title	 = (EditText) findViewById(R.id.editor_title);
		content  = (EditText) findViewById(R.id.editor_content);
	}
	
	class ConfirmBtnListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			// TODO 把文本写入sd卡;
			String titleString = title.getText().toString();
			String contentString = content.getText().toString();
			
			if(titleString.length() == 0 || contentString.length() == 0){
				Toast.makeText(EditorActivity.this, "输入信息不全", Toast.LENGTH_LONG).show();
				return;
			}
			note.setTitle(titleString)
				.setContent(contentString);
			mApi.save(note);
			finish();
		}
	}
	
}
