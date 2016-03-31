package com.zepan.android.widget;import android.content.Context;import android.graphics.drawable.Drawable;import android.util.AttributeSet;import android.widget.ImageView;/** * 当控件点击后，alpha值发生变化 * */public class AlphaImageView extends ImageView {	// 点击后改变的alpha值	private int clickAlpha;	private boolean isImage;	private boolean isBackground;	public AlphaImageView(Context context, AttributeSet attrs) {		super(context, attrs);		// TODO Auto-generated constructor stub	}	/**	 * 设置图片或背景的透明度	 * 	 * @param alpha	 * @param isImage	 * @param isBackground	 * */	public void setClickAlpha(int alpha, boolean isImage, boolean isBackground) {		this.clickAlpha = alpha;		this.isImage = isImage;		this.isBackground = isBackground;	}	@Override	public void setPressed(boolean pressed) {		super.setPressed(pressed);		int alpha = pressed ? clickAlpha : 255;		// 如果改变图片		if (isImage) {			Drawable background = getDrawable();			if (background != null) {				getDrawable().setAlpha(alpha);			}		}		// 如果改变背景		if (isBackground) {			Drawable background = getBackground();			if (background != null) {				background.setAlpha(alpha);			}		}	}}