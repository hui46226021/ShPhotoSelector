package com.sh.zsh.code.shphotoselectorlibrary;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;


/**
 * Created by zhush on 2017/3/6
 * E-mail 405086805@qq.com
 * PS  文件目录
 */

public class DireInfo implements Parcelable {
	public String imageDire;//图片文件夹的名称
	public List<String> photoContent =new ArrayList<String>();
	
	@Override
	public int describeContents() {
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(imageDire);
		dest.writeList(photoContent);
	}
	
	public static final Creator<DireInfo> CREATOR=new Creator<DireInfo>() {
		
		@Override
		public DireInfo[] newArray(int size) {
			return null;
		}
		
		@Override
		public DireInfo createFromParcel(Parcel source) {
			DireInfo ft=new DireInfo();
			ft.imageDire = source.readString();
			ft.photoContent = source.readArrayList(DireInfo.class.getClassLoader());
			
			return ft;
		}
		
		
	};
}
