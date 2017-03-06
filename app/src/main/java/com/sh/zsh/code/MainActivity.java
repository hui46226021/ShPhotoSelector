package com.sh.zsh.code;

import java.util.ArrayList;



import android.os.Bundle;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.sh.zsh.code.shphotoselectorlibrary.PhotoActivity;

/**
 * Created by zhush on 2017/3/6
 * E-mail 405086805@qq.com
 * PS  图片选择器
 */

public class MainActivity extends AppCompatActivity {
	public static final int IMGSACTIVITY_REQUEST =10001;

	ListView listView;
	ArrayList<String> listfile=new ArrayList<String>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main2);
		listView=(ListView) findViewById(R.id.listView1);

		
	}

	public void chise(View v){
		Intent intent = new Intent();
		intent.putExtra(PhotoActivity.IMAGE_COUNT,9); //可选择图片的最大数量
		intent.putExtra(PhotoActivity.BAR_COLORS,R.color.colorPrimary);//标题栏背景颜色
		intent.setClass(this,PhotoActivity.class);
		startActivityForResult(intent,IMGSACTIVITY_REQUEST);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode==IMGSACTIVITY_REQUEST&&resultCode==RESULT_OK){
			Bundle bundle= data.getExtras();

			if (bundle!=null) {
				if (bundle.getStringArrayList("files")!=null) {
					listfile= bundle.getStringArrayList("files");
					listView.setVisibility(View.VISIBLE);
					ArrayAdapter<String> arryAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listfile);
					listView.setAdapter(arryAdapter);
				}
			}
		}
	}
}
