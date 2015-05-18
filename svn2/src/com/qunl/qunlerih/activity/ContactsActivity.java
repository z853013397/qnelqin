package com.qunl.qunlerih.activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.Photo;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.qunl.qunlerih.apis.API;
import com.qunl.qunlerih.sortlistview.CharacterParser;
import com.qunl.qunlerih.sortlistview.ClearEditText;
import com.qunl.qunlerih.sortlistview.Contacts;
import com.qunl.qunlerih.sortlistview.PinyinComparator;
import com.qunl.qunlerih.sortlistview.SideBar;
import com.qunl.qunlerih.sortlistview.SideBar.OnTouchingLetterChangedListener;
import com.qunl.qunlerih.sortlistview.SortAdapter;
import com.qunl.qunlerih.util.SharePrefUtil;
import com.qunlerih.R;

public class ContactsActivity extends Activity implements OnItemClickListener {
	private static final String[] PHONES_PROJECTION = new String[] {
			Phone.DISPLAY_NAME, Phone.NUMBER, Photo.PHOTO_ID, Phone.CONTACT_ID };
	private static final int PHONES_DISPLAY_NAME_INDEX = 0;
	private static final int PHONES_NUMBER_INDEX = 1;// 这是一些字段

	private ListView sortListView = null;
	private SideBar sideBar;
	private TextView dialog;
	private SortAdapter adapter;
	private ClearEditText mClearEditText;

	/**
	 * 汉字转换成拼音的类
	 */
	private CharacterParser characterParser;
	private List<Contacts> contactsList;

	/**
	 * 根据拼音来排列ListView里面的数据类
	 */
	private PinyinComparator pinyinComparator;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contacts);
		initData();
		Log.i("contactsList", contactsList.toString());
		initViews();
	}

	private void initData() {
		characterParser = CharacterParser.getInstance();
		pinyinComparator = new PinyinComparator();
		contactsList= new ArrayList<Contacts>();
		getPhoneContacts();
		getSIMContacts();
		sortList(contactsList);
	}

	private void sortList(List<Contacts> Contacts) {
		for(Contacts c :Contacts) {
			String pinyin = characterParser.getSelling(c.getName());
			String sortString = pinyin.substring(0, 1).toUpperCase();
			
			// 正则表达式，判断首字母是否是英文字母
			if(sortString.matches("[A-Z]")){
				c.setSortLetters(sortString.toUpperCase());
			}else{
				c.setSortLetters("#");
			}
		}
	}
	
	
	private void initViews() {
		sideBar = (SideBar) findViewById(R.id.sidrbar);
		dialog = (TextView) findViewById(R.id.dialog);
		sideBar.setTextView(dialog);

		sideBar.setOnTouchingLetterChangedListener(new OnTouchingLetterChangedListener() {

			@Override
			public void onTouchingLetterChanged(String s) {
				// 该字母首次出现的位置
				int position = adapter.getPositionForSection(s.charAt(0));
				if (position != -1) {
					sortListView.setSelection(position);
				}

			}
		});
		sortListView = (ListView) findViewById(R.id.country_lvcountry);
//		sortListView.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view,
//					int position, long id) {
//				// 这里要利用adapter.getItem(position)来获取当前position所对应的对象
//				Toast.makeText(getApplication(),
//						((Contacts) adapter.getItem(position)).getNumber(),
//						Toast.LENGTH_SHORT).show();
//				
//				sendMessage(((Contacts) adapter.getItem(position)).getNumber());
//				
//				
//			}
//		});
		sortListView.setOnItemClickListener(this);
		Collections.sort(contactsList, pinyinComparator);
		adapter = new SortAdapter(this, contactsList);
		sortListView.setAdapter(adapter);

		mClearEditText = (ClearEditText) findViewById(R.id.filter_edit);

		// 根据输入框输入值的改变来过滤搜索
		mClearEditText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// 当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
				filterData(s.toString());
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});
	}

	
	
	/**
	 * @param number
	 * 传递信息给服务器
	 */
	protected void sendMessage(String number) {
		RequestParams params = new RequestParams();
		Log.i("tid",SharePrefUtil.getString(this, "tid", null));
		params.addBodyParameter("num", number.replaceAll(" ", ""));
		Log.i("number", number.replaceAll(" ", ""));
		params.addBodyParameter("content", SharePrefUtil.getString(this, "tid", null));
		HttpUtils http = new HttpUtils();
		http.configCurrentHttpCacheExpiry(0);
		http.send(HttpRequest.HttpMethod.POST, API.sendMessage, params, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				Toast.makeText(ContactsActivity.this, "发送失败", 0).show();
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				String str = arg0.result;
				Log.i("onSuccess", str);
				finish();
			}
		});
	}

	/**
	 * 根据输入框中的值来过滤数据并更新ListView
	 * @param filterStr
	 */
	private void filterData(String filterStr){
		List<Contacts> filterDateList = new ArrayList<Contacts>();
		
		if(TextUtils.isEmpty(filterStr)){
			filterDateList = contactsList;
		}else{
			filterDateList.clear();
			for(Contacts sortModel : contactsList){
				String name = sortModel.getName();
				if(name.indexOf(filterStr.toString()) != -1 || characterParser.getSelling(name).startsWith(filterStr.toString())){
					filterDateList.add(sortModel);
				}
			}
		}
		Collections.sort(filterDateList, pinyinComparator);
		adapter.updateListView(filterDateList);
	}

	/**
	 * 得到手机联系人
	 */
	private void getPhoneContacts() {
		ContentResolver resolver = this.getContentResolver();
		Cursor phoneCursor = resolver.query(Phone.CONTENT_URI,
				PHONES_PROJECTION, null, null, null);
		if (phoneCursor != null) {
			while (phoneCursor.moveToNext()) {
				String phoneNumber = phoneCursor.getString(PHONES_NUMBER_INDEX);
				if (TextUtils.isEmpty(phoneNumber))
					continue;

				String contactName = phoneCursor
						.getString(PHONES_DISPLAY_NAME_INDEX);
				Contacts contact = new Contacts();
				contact.setName(contactName);
				contact.setNumber(phoneNumber);
				contactsList.add(contact);
			}

			phoneCursor.close();
		}
	}

	/**
	 * 得到sim卡联系人
	 */
	private void getSIMContacts() {
		ContentResolver resolver = this.getContentResolver();
		Uri uri = Uri.parse("content://icc/adn");
		Cursor phoneCursor = resolver.query(uri, PHONES_PROJECTION, null, null,
				null);
		if (phoneCursor != null) {
			while (phoneCursor.moveToNext()) {
				String phoneNumber = phoneCursor.getString(PHONES_NUMBER_INDEX);
				if (TextUtils.isEmpty(phoneNumber))
					continue;
				String contactName = phoneCursor
						.getString(PHONES_DISPLAY_NAME_INDEX);
				Contacts contact = new Contacts();
				contact.setName(contactName);
				contact.setNumber(phoneNumber);
				contactsList.add(contact);
			}
			phoneCursor.close();
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// 这里要利用adapter.getItem(position)来获取当前position所对应的对象
		Toast.makeText(getApplication(),
				((Contacts) adapter.getItem(position)).getNumber(),
				Toast.LENGTH_SHORT).show();
		
		sendMessage(((Contacts) adapter.getItem(position)).getNumber());
	}
}