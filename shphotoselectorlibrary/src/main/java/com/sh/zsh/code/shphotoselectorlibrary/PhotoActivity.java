package com.sh.zsh.code.shphotoselectorlibrary;

import java.util.ArrayList;
import java.util.List;


import android.content.Intent;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.view.View;
import android.view.View.OnClickListener;

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;

import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
/**
 * Created by zhush on 2017/3/6
 * E-mail 405086805@qq.com
 * PS  图片选择主界面
 */

public class PhotoActivity extends AppCompatActivity implements AlbumDialog.SelectedCall {

	public static final String IMAGE_COUNT ="image_count";
	public static final String BAR_COLORS ="bar_colors";

	Bundle bundle;
	DireInfo direInfo;
	GridView imgGridView;
	PhotoAdapter photoAdapter;

	BitmapUtil bitmapUtil;
	RelativeLayout relativeLayout2;

	ArrayList<String> filelist;
	Button finishButton ;
	TextView otherAlbum;
	List<String>filecountents  = new ArrayList<>();
	RelativeLayout relativeLayout;
	TextView title;

	int maxCount;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.photogrally);
		relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout1);
		bitmapUtil =new BitmapUtil(this);
		direInfo = bitmapUtil.LocalImgFileList().get(0);
		maxCount = getIntent().getIntExtra(IMAGE_COUNT,0);
		imgGridView=(GridView) findViewById(R.id.gridView1);
		bundle= getIntent().getExtras();
		filecountents.clear();
		filecountents.addAll(direInfo.photoContent);
		photoAdapter =new PhotoAdapter(this,filecountents,onItemClickClass);
		imgGridView.setAdapter(photoAdapter);
		title= (TextView) findViewById(R.id.title);
		relativeLayout2=(RelativeLayout) findViewById(R.id.relativeLayout2);
		finishButton = (Button) findViewById(R.id.finishButton);
		otherAlbum = (TextView) findViewById(R.id.other_album);

		filelist=new ArrayList<String>();
		otherAlbum.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				AlbumDialog albumDialog = new AlbumDialog(PhotoActivity.this);
				albumDialog.createLoadingDialog(PhotoActivity.this).show();
			}
		});


		relativeLayout.setBackgroundResource(getIntent().getIntExtra(BAR_COLORS,0));

		finishButton.setText("完成("+filelist.size()+"/"+maxCount+")");
		title.setText(direInfo.imageDire);
	}

	@Override
	public void selectedCall(DireInfo direInfo) {
		this.direInfo=direInfo;
		filecountents.clear();
		filecountents.addAll(direInfo.photoContent);
		photoAdapter =new PhotoAdapter(this,filecountents,onItemClickClass);
		imgGridView.setAdapter(photoAdapter);
		filelist.clear();
		finishButton.setText("完成("+filelist.size()+"/"+maxCount+")");
		title.setText(direInfo.imageDire);
	}


	PhotoAdapter.OnItemClickClass onItemClickClass=new PhotoAdapter.OnItemClickClass() {
		@Override
		public void OnItemClick(View v, int Position, CheckBox checkBox) {
			String filapath= direInfo.photoContent.get(Position);


			if (checkBox.isChecked()) {
				checkBox.setChecked(false);
				filelist.remove(filapath);
				finishButton.setText("完成("+filelist.size()+"/"+maxCount+")");
			}else {
				try {
					if(filelist.size()>=maxCount){
						Toast.makeText(PhotoActivity.this,"最多只能选择"+maxCount+"张图片",Toast.LENGTH_SHORT).show();
						return;
					}

					checkBox.setChecked(true);
					Log.i("img", "img choise position->"+Position);
					filelist.add(filapath);

					finishButton.setText("完成("+filelist.size()+"/"+maxCount+")");

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	};
	
	public void tobreak(View view){
		finish();
	}

	/**
	 * 回调方法
	 * @param view
     */
	public void sendfiles(View view){
		Intent intent =getIntent();
		Bundle bundle=new Bundle();
		bundle.putStringArrayList("files", filelist);
		intent.putExtras(bundle);
		setResult(RESULT_OK,intent);
		finish();
	}
}
