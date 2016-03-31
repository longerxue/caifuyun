package com.zepan.caifuyun.adapter;import io.rong.imlib.RongIMClient;import io.rong.imlib.RongIMClient.ErrorCode;import io.rong.imlib.RongIMClient.ResultCallback;import io.rong.imlib.model.Conversation;import io.rong.imlib.model.Conversation.ConversationType;import io.rong.imlib.model.Discussion;import io.rong.message.ImageMessage;import io.rong.message.TextMessage;import java.util.ArrayList;import java.util.HashMap;import java.util.List;import java.util.Map;import android.content.Context;import android.text.TextUtils;import android.view.View;import android.view.ViewGroup;import android.widget.BaseAdapter;import android.widget.TextView;import com.zepan.android.util.DateUtil;import com.zepan.caifuyun.R;import com.zepan.caifuyun.cache.CompanyRespository;import com.zepan.caifuyun.widget.PolymerImageView;/** * 会话列表Adapter * */public class ConversationAdapter extends BaseAdapter {	public Context context = null;	public List<Conversation> list = null;	/** 定义一个map存储会话与会话中用户号的对应关系，避免不断地根据会话targetId获取讨论组对象 */	public Map<String, List<String>> conversationUserIdMap = new HashMap<String, List<String>>();		public ConversationAdapter(Context context, List<Conversation> list) {		this.context = context;		this.list = list;	}	@Override	public int getCount() {		// TODO Auto-generated method stub		return list == null ? 0 : list.size();	}	@Override	public Object getItem(int position) {		// TODO Auto-generated method stub		return list == null ? null : list.get(position);	}	@Override	public long getItemId(int position) {		// TODO Auto-generated method stub		return position;	}	@Override	public View getView(int position, View convertView, ViewGroup parent) {		final HolderView holderView;		if (convertView == null) {			holderView = new HolderView();			convertView = View					.inflate(context, R.layout.listitem_message, null);			holderView.imageView = (PolymerImageView) convertView					.findViewById(R.id.iv_message);			holderView.nameView = (TextView) convertView					.findViewById(R.id.title);			holderView.resumeView = (TextView) convertView					.findViewById(R.id.content);			holderView.timeView = (TextView) convertView					.findViewById(R.id.time);			holderView.unReadMsgCountView = (TextView) convertView					.findViewById(R.id.tv_unread_msg_count);			convertView.setTag(holderView);		} else {			holderView = (HolderView) convertView.getTag();		}		final Conversation cov = list.get(position);		if (cov != null) {			// holderView.imageView.setImageResource(R.drawable.rc_group_default_portrait);			List<String> imageList = new ArrayList<String>();			holderView.imageView.setImageList(imageList);//			if (cov.getConversationType() == ConversationType.PRIVATE) {//				holderView.nameView.setText(cov.getSenderUserName());//			} else if (TextUtils.isEmpty(cov.getConversationTitle())) {// 如果讨论组名称为空//				// 显示讨论组内成员名称//				holderView.nameView.setText(CompanyRespository//						.getNameList(conversationUserIdMap.get(cov//								.getTargetId())));//			}			// 显示讨论组内成员名称			holderView.nameView.setText(cov.getConversationTitle());			if (cov.getLatestMessage() instanceof TextMessage) {				TextMessage msg = (TextMessage) cov.getLatestMessage();				holderView.resumeView.setText(msg.getContent());			} else if (cov.getLatestMessage() instanceof ImageMessage) {				holderView.resumeView.setText("[图片]");			} else {				holderView.resumeView.setText("");			}			holderView.timeView.setText(DateUtil.transfromTimeToPeriod(cov					.getSentTime()));			if (cov.getUnreadMessageCount() > 0) {				holderView.unReadMsgCountView.setVisibility(View.VISIBLE);				holderView.unReadMsgCountView.setText(cov						.getUnreadMessageCount() + "");			} else {				holderView.unReadMsgCountView.setVisibility(View.GONE);			}			// 设置讨论组头像			holderView.imageView.setTag(cov.getTargetId());			if (conversationUserIdMap.containsKey(cov.getTargetId())) {				holderView.imageView.setImageList(CompanyRespository						.getAvatarList(conversationUserIdMap.get(cov								.getTargetId())));			} else {				// 向融云获取讨论组信息				RongIMClient.getInstance().getDiscussion(cov.getTargetId(),						new ResultCallback<Discussion>() {							@Override							public void onError(ErrorCode arg0) {							}							@Override							public void onSuccess(Discussion arg0) {								String id = (String) holderView.imageView										.getTag();								conversationUserIdMap.put(arg0.getId(),										arg0.getMemberIdList());								if (id.equals(arg0.getId())) {									holderView.imageView.setImageList(CompanyRespository											.getAvatarList(arg0													.getMemberIdList()));								}							}						});			}		}		return convertView;	}	@Override	public void notifyDataSetChanged() {		super.notifyDataSetChanged();		conversationUserIdMap.clear();	}		private static final float[][] sizes2 = { new float[] { 0.9f, 0.9f },			new float[] { 0.5f, 0.65f }, new float[] { 0.35f, 0.8f },			new float[] { 0.45f, 0.91f }, new float[] { 0.38f, 0.80f } };	public static float[] size2(int count) {		return count > 0 && count <= sizes2.length ? sizes2[count - 1] : null;	}		class HolderView {		PolymerImageView imageView;		TextView nameView;		TextView resumeView;		TextView timeView;		TextView unReadMsgCountView;	}}