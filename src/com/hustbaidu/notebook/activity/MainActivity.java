package com.hustbaidu.notebook.activity;

import java.util.List;

import com.hustbaidu.notebook.R;
import com.hustbaidu.notebook.api.NoteBookInterface;
import com.hustbaidu.notebook.file.NoteFileAPI;
import com.hustbaidu.notebook.model.Note;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	public static final String TAG = "MainActivity";
	public static final String DIR = "mydir";
	
	Button addBtn;
	ListView titleLv;
	List<Note> titles;
	TitleAdapter adapter;
	NoteBookInterface mApi;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		mApi = NoteFileAPI.getInstance(this.getApplicationContext());
		findViews();
		titles = mApi.findAll();
		addBtn.setOnClickListener(new AddBtnListener());
		adapter =  new TitleAdapter(this, titles, R.layout.title_item);
		titleLv.setAdapter(adapter);
		titleLv.setOnItemClickListener(new TitleItemListener());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	void findViews(){
		this.addBtn = (Button) findViewById(R.id.add_note_btn);
		this.titleLv = (ListView) findViewById(R.id.titile_listview);
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		titles = mApi.findAll();
		adapter.setData(titles);
		adapter.notifyDataSetChanged();
	}
	
	class AddBtnListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO 跳转到编辑界面
			Intent intent = new Intent(MainActivity.this, EditorActivity.class);
			startActivity(intent);
		}
		
	}
	
	class TitleAdapter extends BaseAdapter {
		
		List<Note> data;
		int layoutId;
		Context context;
		LayoutInflater inflater;
		
		public TitleAdapter(Context context, List<Note> data, int layoutId) {
			super();
			this.data = data;
			this.layoutId = layoutId;
			this.context = context;
			this.inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
		}
		
		public void setData(List<Note> data) {
			this.data = data;
		}
		
		public List<Note> getData() {
			return data;
		}

		// 返回的是ListView里面现在有多少个item
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return data == null ? 0: data.size();
		}

		
		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return data.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// 如果convertView是null
			LinearLayout layout;
			ViewHolder holder;
			if(convertView == null) {
				layout = (LinearLayout) inflater.inflate(layoutId, null);
				holder = new ViewHolder();
				holder.name = (TextView) layout.findViewById(R.id.title_item_name);
				holder.date = (TextView) layout.findViewById(R.id.title_item_date);
				layout.setTag(holder);
			} else {
				layout = (LinearLayout) convertView;
				holder = (ViewHolder) layout.getTag();
			}
			
			holder.name.setText(data.get(position).getTitle());
			holder.date.setText(data.get(position).getModifyDate());
			return layout;
		}
		
		class ViewHolder {
			TextView name;
			TextView date;
		}
		
	}
	
	class TitleItemListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			Intent intent = new Intent(MainActivity.this, EditorActivity.class);
			String titleString = adapter.data.get(arg2).getTitle();
			intent.putExtra("title", titleString);
			startActivity(intent);
		}
	}

}
