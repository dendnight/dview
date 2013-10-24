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
 * �Զ����б�
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
public class ListAdapter extends BaseAdapter {

	private List<Map<String, Object>> listItem;// ���ݼ�

	private LayoutInflater mainContainer; // ��ͼ����

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
		/** �ļ����� */
		public TextView folder;
		/** ͼƬ���� */
		public TextView count;
		/** ͼƬ���ļ��� */
		public TextView path;
		/** ����޸�ʱ�� */
		public TextView date;
		/** �ļ��б�� */
		private String folderId;

		public String getFolderId() {
			return folderId;
		}

		public void setFolderId(String folderId) {
			this.folderId = folderId;
		}

	}

	public ListAdapter(Context context, List<Map<String, Object>> listItem) {
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
			convertView = mainContainer.inflate(R.layout.activity_list_item, null);

			// �����Զ�����ͼ
			listItemView = new ListItemView();
			listItemView.image = (ImageView) convertView.findViewById(R.id.list_image);
			listItemView.folder = (TextView) convertView.findViewById(R.id.list_folder);

			listItemView.count = (TextView) convertView.findViewById(R.id.list_count);
			listItemView.path = (TextView) convertView.findViewById(R.id.list_path);
			listItemView.date = (TextView) convertView.findViewById(R.id.list_date);

			// �����Զ�����ͼ�������ļ�
			convertView.setTag(listItemView);

		} else {
			listItemView = (ListItemView) convertView.getTag();
		}

		// ����ֵ
		listItemView.image.setImageBitmap((Bitmap) listItem.get(position).get(DictParam.IMAGE));
		listItemView.folder.setText((String) listItem.get(position).get(DictParam.FOLDER));
		listItemView.path.setText((String) listItem.get(position).get(DictParam.PATH));

		listItemView.count.setText((String) listItem.get(position).get(DictParam.COUNT));
		listItemView.date.setText((String) listItem.get(position).get(DictParam.DATE));
		listItemView.setFolderId((String) listItem.get(position).get(DictParam.FOLDER_ID));
		return convertView;
	}
}
