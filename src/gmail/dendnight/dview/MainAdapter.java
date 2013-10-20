package gmail.dendnight.dview;

import gmail.dendnight.utils.DictParam;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 
 * <pre>
 * Description
 * Author:		dendnight
 * Version:		1.0  
 * Create at:	2013��10��20�� ����2:35:30  
 *  
 * �޸���ʷ:
 * ����    ����    �汾  �޸�����
 * ------------------------------------------------------------------
 * 
 * </pre>
 */
public class MainAdapter extends BaseAdapter {

	private List<Map<String, Object>> listItem;// ���ݼ�

	private LayoutInflater mainContainer; // ��ͼ����

	// �Զ���ؼ�����
	public final class ListItemView {
		public ImageView image;// ͼƬ
		public TextView title;// �ļ�����
		public TextView count;// ͼƬ����
		public TextView path;// ͼƬ���ļ���
		public TextView date;// ����޸�ʱ��
	}

	public MainAdapter(Context context, List<Map<String, Object>> listItem) {
		this.mainContainer = LayoutInflater.from(context);
		this.listItem = listItem;
	}

	@Override
	public int getCount() {
		return this.listItem != null ? this.listItem.size() : 0;
	}

	@Override
	public Object getItem(int position) {
		return this.listItem.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ListItemView listItemView = null;

		if (null == convertView) {
			// ��ȡ�����ļ���ͼ
			convertView = mainContainer.inflate(R.layout.activity_main_item, null);

			// �����Զ�����ͼ
			listItemView = new ListItemView();
			listItemView.image = (ImageView) convertView.findViewById(R.id.image);
			listItemView.title = (TextView) convertView.findViewById(R.id.title);

			listItemView.count = (TextView) convertView.findViewById(R.id.count);
			listItemView.path = (TextView) convertView.findViewById(R.id.path);
			listItemView.date = (TextView) convertView.findViewById(R.id.date);

			// �����Զ�����ͼ�������ļ�
			convertView.setTag(listItemView);

		} else {
			listItemView = (ListItemView) convertView.getTag();
		}

		// ����ֵ
		listItemView.image.setImageBitmap((Bitmap) listItem.get(position).get(DictParam.IMAGE));
		listItemView.title.setText((String) listItem.get(position).get(DictParam.TITLE));
		listItemView.path.setText((String) listItem.get(position).get(DictParam.PATH));

		listItemView.count.setText((String) listItem.get(position).get(DictParam.COUNT));
		listItemView.date.setText((String) listItem.get(position).get(DictParam.DATE));
		return convertView;
	}
}
