package gmail.dendnight.dview.ui;

import gmail.dendnight.dview.R;
import gmail.dendnight.dview.dict.DictParam;

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
 * �Զ�������
 * 
 * <pre>
 * Description
 * Author:		dendnight
 * Version:		1.0  
 * Create at:	2013��10��21�� ����3:26:33  
 *  
 * �޸���ʷ:
 * ����    ����    �汾  �޸�����
 * ------------------------------------------------------------------
 * 
 * </pre>
 */
public class GridAdapter extends BaseAdapter {

	private List<Map<String, Object>> listItem;// ���ݼ�

	private LayoutInflater listContainer; // ��ͼ����

	/**
	 * �Զ���ؼ�����
	 * 
	 * <pre>
	 * Description
	 * Author:		dendnight
	 * Version:		1.0  
	 * Create at:	2013��10��21�� ����2:25:48  
	 *  
	 * �޸���ʷ:
	 * ����    ����    �汾  �޸�����
	 * ------------------------------------------------------------------
	 * 
	 * </pre>
	 */
	public final class ListItemView {
		/** ͼƬ */
		public ImageView image;
		/** ʱ�� */
		public TextView date;
		/** ͼƬ·�� */
		private String path;

		public String getPath() {
			return path;
		}

		public void setPath(String path) {
			this.path = path;
		}

	}

	public GridAdapter(Context context, List<Map<String, Object>> listItem) {
		this.listContainer = LayoutInflater.from(context);
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
			convertView = listContainer.inflate(R.layout.activity_grid_item, null);

			// �����Զ�����ͼ
			listItemView = new ListItemView();
			listItemView.image = (ImageView) convertView.findViewById(R.id.grid_image);
			listItemView.date = (TextView) convertView.findViewById(R.id.list_title);
			// �����Զ�����ͼ�������ļ�
			convertView.setTag(listItemView);

		} else {
			listItemView = (ListItemView) convertView.getTag();
		}

		// ����ֵ
		listItemView.image.setImageBitmap((Bitmap) listItem.get(position).get(DictParam.IMAGE));
		// listItemView.date.setText((String)
		// listItem.get(position).get(DictParam.DATE));
		listItemView.setPath((String) listItem.get(position).get(DictParam.PATH));
		return convertView;
	}
}