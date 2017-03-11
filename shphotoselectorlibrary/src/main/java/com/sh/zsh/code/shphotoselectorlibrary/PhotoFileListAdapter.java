package com.sh.zsh.code.shphotoselectorlibrary;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class PhotoFileListAdapter extends BaseAdapter{

	Context context;
	String filecount="filecount";
	String filename="imageDire";
	String imgpath="imgpath";
	List<HashMap<String, String>> listdata;

	public PhotoFileListAdapter(Context context, List<HashMap<String, String>> listdata) {
		this.context=context;
		this.listdata=listdata;

	}
	
	@Override
	public int getCount() {
		return listdata.size();
	}

	@Override
	public Object getItem(int arg0) {
		return listdata.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(final int arg0, View arg1, ViewGroup arg2) {
		Holder holder;
		if (arg1==null) {
			holder=new Holder();
			arg1=LayoutInflater.from(context).inflate(R.layout.imgfileadapter, null);
			holder.photo_imgview=(ImageView) arg1.findViewById(R.id.filephoto_imgview);
			holder.filecount_textview=(TextView) arg1.findViewById(R.id.filecount_textview);
			holder.filename_textView=(TextView) arg1.findViewById(R.id.filename_textview);
			arg1.setTag(holder);

		}else{
			holder= (Holder)arg1.getTag();

		}
		File file = new File(listdata.get(arg0).get(imgpath));
		Glide.with(context)
				.load(file)
				.into(holder.photo_imgview);


		holder.filename_textView.setText(listdata.get(arg0).get(filename));
		holder.filecount_textview.setText(listdata.get(arg0).get(filecount));

		
		return arg1;
	}
	
	class Holder{
		public ImageView photo_imgview;
		public TextView filecount_textview;
		public TextView filename_textView;
	}

	
	
}
