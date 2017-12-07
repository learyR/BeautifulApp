package com.leary.littlefairy.beautifulapp.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leary.littlefairy.beautifulapp.R;
import com.leary.littlefairy.beautifulapp.utils.PixelUtil;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 常见页面的Toolbar,主要用于解决标题居中,和方便自定义操作
 * Created by long hai lin on 2017/10/16.
 */

public class FamiliarToolbar extends RelativeLayout {

    private final String TAG = getClass().getSimpleName();
    //设置左侧标题
    private String leftTitle;
    //设置左侧图标
    private Drawable leftImage;
    //设置左侧标题颜色
    private ColorStateList leftTitleColor;
    @SHOWLEFT
    private int showLeft = 1;
    //是否显示标题
    private boolean showTitle = true;
    //设置标题文本
    private String titleContent;
    //设置标题图标位置
    private int titleImagePosition = 2;
    //设置标题图标
    private Drawable titleImage;
    private Drawable titleArrowImage;
    private boolean showTitleArrowImage = false;
    //设置标题与图标距离
    private int titleImagePadding;
    //标题颜色
    private ColorStateList titleColor;
    //是否显示右侧控件
    @SHOWRIGHT
    private int showRight;
    //设置通知控件图标
    private Drawable rightNotifyImage;
    //设置通知控件提示文本
    private int rightNotifyText;
    //通知文本颜色
    private ColorStateList rightNotifyTextColor;
    //设置普通控件文本
    private String rightNormalText;
    //右侧文字颜色颜色
    private ColorStateList rightNormalTextColor;
    //设置图标位置：只能在文本左右设置，当文本为空时，只显示图标
    private int rightNormalImagePosition;
    //设置标题图标
    private Drawable rightNormalImage;
    //设置标题与图标距离
    private int rightNormalImagePadding;

    //控件声明
    LinearLayout base_top_back;
    ImageView base_top_back_img;
    TextView base_top_back_name;
    TextView base_top_title;
    RelativeLayout rl_notice_message;
    ImageView iv_toolbar_right;
    TextView tv_notice_count;
    TextView tv_title_right;
    ImageView base_top_arrow;
    RelativeLayout base_top_title_layout;

    public FamiliarToolbar(Context context) {
        super(context);
        init(context, null, 0);
    }

    public FamiliarToolbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public FamiliarToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        initViews(context);
        initAttrs(context, attrs, defStyleAttr);
        setup(context);
    }

    private void setup(Context context) {

    }

    private void initViews(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.familiar_toolbar, this);
        base_top_back = (LinearLayout) findViewById(R.id.base_top_back);
        base_top_back_img = (ImageView) findViewById(R.id.base_top_back_img);
        base_top_back_name = (TextView) findViewById(R.id.base_top_back_name);
        base_top_title = (TextView) findViewById(R.id.base_top_title);
        rl_notice_message = (RelativeLayout) findViewById(R.id.rl_notice_message);
        iv_toolbar_right = (ImageView) findViewById(R.id.iv_toolbar_right);
        tv_notice_count = (TextView) findViewById(R.id.tv_notice_count);
        tv_title_right = (TextView) findViewById(R.id.tv_title_right);
        base_top_arrow = (ImageView) findViewById(R.id.base_top_arrow);
        base_top_title_layout = (RelativeLayout) findViewById(R.id.base_top_title_layout);
        base_top_back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null)
                    mListener.clickLeft();
            }
        });
        base_top_title_layout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null)
                    mListener.clickTitle();
            }
        });
        rl_notice_message.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null)
                    mListener.clickRight();
            }
        });
        tv_title_right.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null)
                    mListener.clickRight();
            }
        });
    }
    private ClickListener mListener;

    public void setClickListener(ClickListener mListener) {
        this.mListener = mListener;
    }

    public interface ClickListener{
        void clickLeft();
        void clickTitle();
        void clickRight();
    }
    @SuppressLint("WrongConstant")
    private void initAttrs(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FamiliarToolbar, defStyleAttr, 0);
        if (a != null){
            leftTitle = a.getString(R.styleable.FamiliarToolbar_leftTitle);
            leftImage = a.getDrawable(R.styleable.FamiliarToolbar_leftImage);
            leftTitleColor = a.getColorStateList(R.styleable.FamiliarToolbar_leftTitleColor);
            int left = a.getInt(R.styleable.FamiliarToolbar_showLeft, NAVIGATIONIMG);
            if (left == 0)
                showLeft = ALL;
            else if (left == 1)
                showLeft = NAVIGATIONIMG;
            else if (left == 2)
                showLeft = NAVIGATIONTXT;
            else
                showLeft = NONE;
            showTitle = a.getBoolean(R.styleable.FamiliarToolbar_showTitle, true);
            titleContent = a.getString(R.styleable.FamiliarToolbar_titleContent);
            titleImagePosition = a.getInt(R.styleable.FamiliarToolbar_titleImagePosition, 2);
            titleImage = a.getDrawable(R.styleable.FamiliarToolbar_titleImage);
            titleImagePadding = (int) a.getDimension(R.styleable.FamiliarToolbar_titleImagePadding, PixelUtil.dp2px(context, 3));
            titleColor = a.getColorStateList(R.styleable.FamiliarToolbar_titleColor);
            int right = a.getInt(R.styleable.FamiliarToolbar_showRight, NOT_ALL);
            if (right == 0)
                showRight = NOTIFY;
            else if (right == 1)
                showRight = NORMAL;
            else
                showRight = NOT_ALL;
            rightNotifyImage = a.getDrawable(R.styleable.FamiliarToolbar_rightNotifyImage);
            rightNormalImage = a.getDrawable(R.styleable.FamiliarToolbar_rightNormalImage);
            rightNotifyText = a.getInt(R.styleable.FamiliarToolbar_rightNotifyText, 0);
            rightNormalText = a.getString(R.styleable.FamiliarToolbar_rightNormalText);
            rightNotifyTextColor = a.getColorStateList(R.styleable.FamiliarToolbar_rightNotifyTextColor);
            rightNormalTextColor = a.getColorStateList(R.styleable.FamiliarToolbar_rightNormalTextColor);
            rightNormalImagePosition = a.getInt(R.styleable.FamiliarToolbar_rightNormalImagePosition, 2);
            rightNormalImagePadding = (int) a.getDimension(R.styleable.FamiliarToolbar_rightNormalImagePadding, PixelUtil.dp2px(context, 3));
            titleArrowImage = a.getDrawable(R.styleable.FamiliarToolbar_titleArrowImage);
            showTitleArrowImage = a.getBoolean(R.styleable.FamiliarToolbar_showTitleArrowImage, false);
            a.recycle();
        }
        if (isInEditMode()) { return; }
        //设置显示左侧导航栏相关信息
        showLeftNav(showLeft);
        //设置标题显示相关信息
        showToolbarTitle();
        //显示右侧相关信息
        showRightNav(showRight);

        //设置左侧内容
        setUpLeftContent();

        //设置标题内容相关
        setUpTitleContent();

        //设置右侧内容相关
        setUpRightContent();
    }


    public void setUpRightContent() {
        //设置通知相关
        if (rl_notice_message.getVisibility() == View.VISIBLE)
            setUpNotify();
        if (tv_title_right.getVisibility() == View.VISIBLE)
            setUpNormalRight();
    }

    public void setUpNormalRight() {
        if (rightNormalImage != null){
            tv_title_right.setCompoundDrawablePadding(rightNormalImagePadding);
            //非常重要，必须设置，否则图片不会显示
            rightNormalImage.setBounds(0, 0, rightNormalImage.getMinimumWidth(), rightNormalImage.getMinimumHeight());
            switch (rightNormalImagePosition){
                case 0:
                    tv_title_right.setCompoundDrawables(rightNormalImage, null,null,null);
                    break;
                case 1:
                    tv_title_right.setCompoundDrawables(null, null,rightNormalImage,null);
                    break;
                case 2:
                    tv_title_right.setCompoundDrawables(null, null,null,null);
                    break;
                default:
                    break;
            }
        }else{
            tv_title_right.setCompoundDrawablePadding(0);
            tv_title_right.setCompoundDrawables(null, null,null,null);
        }
        tv_title_right.setText(TextUtils.isEmpty(rightNormalText)? "":rightNormalText );
        if (rightNormalTextColor != null)
            tv_title_right.setTextColor(rightNormalTextColor);
    }

    public TextView getTvTitleRight() {
        return tv_title_right;
    }

    public void setUpNotify() {
        if (rightNotifyImage != null)
            iv_toolbar_right.setImageDrawable(rightNotifyImage);
        if (rightNotifyText > 0){
            tv_notice_count.setVisibility(View.VISIBLE);
            tv_notice_count.setText(rightNotifyText+"");
            tv_notice_count.setTextColor(rightNotifyTextColor);
        }
        else
            tv_notice_count.setVisibility(View.GONE);

    }

    public void setUpTitleContent() {
//        Log.e("","titleContent========="+titleContent);
        base_top_title.setText(TextUtils.isEmpty(titleContent)? "":titleContent);
//        Log.e("","titleContent=====AA===="+base_top_title.getText().toString());
        if (titleColor != null)
            base_top_title.setTextColor(titleColor);
        if (titleArrowImage != null){
            base_top_arrow.setVisibility(View.VISIBLE);
            base_top_arrow.setImageDrawable(titleArrowImage);
        }else{
            base_top_arrow.setVisibility(View.GONE);
        }
        if (titleImage != null){
            base_top_arrow.setVisibility(View.GONE);
            base_top_title.setCompoundDrawablePadding(titleImagePadding);
            //非常重要，必须设置，否则图片不会显示
            titleImage.setBounds(0, 0, titleImage.getMinimumWidth(), titleImage.getMinimumHeight());
            switch (titleImagePosition){
                case 0:
                    base_top_title.setCompoundDrawables(titleImage, null,null,null);
                    break;
                case 1:
                    base_top_title.setCompoundDrawables(null, null,titleImage,null);
                    break;
                case 2:
                    base_top_title.setCompoundDrawables(null, null,null,null);
                    break;
                default:
                    break;
            }
        }else{
            base_top_title.setCompoundDrawablePadding(0);
            base_top_title.setCompoundDrawables(null, null,null,null);
        }




    }

    public void setUpLeftContent() {
        base_top_back_name.setText(TextUtils.isEmpty(leftTitle) ? "" : leftTitle);
        if (leftTitleColor != null)
            base_top_back_name.setTextColor(leftTitleColor);
        if (leftImage != null)
            base_top_back_img.setImageDrawable(leftImage);
    }

    public void showRightNav(@SHOWRIGHT int type) {
        switch (type){
            case NOTIFY:
                if (rl_notice_message.getVisibility() != View.VISIBLE) rl_notice_message.setVisibility(View.VISIBLE);
                if (tv_title_right.getVisibility() != View.GONE) tv_title_right.setVisibility(View.GONE);
                break;
            case NORMAL:
                if (rl_notice_message.getVisibility() != View.GONE) rl_notice_message.setVisibility(View.GONE);
                if (tv_title_right.getVisibility() != View.VISIBLE) tv_title_right.setVisibility(View.VISIBLE);
                break;
            case NOT_ALL:
                if (rl_notice_message.getVisibility() != View.GONE) rl_notice_message.setVisibility(View.GONE);
                if (tv_title_right.getVisibility() != View.GONE) tv_title_right.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }


    public void showToolbarTitle() {
        if (showTitle)
        {
            if (base_top_title.getVisibility() != View.VISIBLE) {
                base_top_title.setVisibility(View.VISIBLE);
            }
        }
        else
            if (base_top_title.getVisibility() != View.GONE) base_top_title.setVisibility(View.GONE);
        if (showTitleArrowImage){
            base_top_arrow.setVisibility(View.VISIBLE);
        }else{
            base_top_arrow.setVisibility(View.GONE);
        }
    }

    public void showLeftNav(@SHOWLEFT int type) {
        /*
        * 设置显示左侧导航栏相关信息
        * */
        switch (type){
            case ALL:
                if (base_top_back.getVisibility() != View.VISIBLE) base_top_back.setVisibility(View.VISIBLE);
                if (base_top_back_img.getVisibility() != View.VISIBLE) base_top_back_img.setVisibility(View.VISIBLE);
                if (base_top_back_name.getVisibility() != View.VISIBLE) base_top_back_name.setVisibility(View.VISIBLE);
                break;
            case NAVIGATIONIMG:
                if (base_top_back.getVisibility() != View.VISIBLE) base_top_back.setVisibility(View.VISIBLE);
                if (base_top_back_img.getVisibility() != View.VISIBLE) base_top_back_img.setVisibility(View.VISIBLE);
                if (base_top_back_name.getVisibility() != View.GONE) base_top_back_name.setVisibility(View.GONE);
                break;
            case NAVIGATIONTXT:
                if (base_top_back.getVisibility() != View.VISIBLE) base_top_back.setVisibility(View.VISIBLE);
                if (base_top_back_img.getVisibility() != View.GONE) base_top_back_img.setVisibility(View.GONE);
                if (base_top_back_name.getVisibility() != View.VISIBLE) base_top_back_name.setVisibility(View.VISIBLE);
                break;
            case NONE:
                if (base_top_back.getVisibility() != View.GONE) base_top_back.setVisibility(View.GONE);
                break;
            default:
                if (base_top_back.getVisibility() != View.VISIBLE) base_top_back.setVisibility(View.VISIBLE);
                if (base_top_back_img.getVisibility() != View.VISIBLE) base_top_back_img.setVisibility(View.VISIBLE);
                if (base_top_back_name.getVisibility() != View.GONE) base_top_back_name.setVisibility(View.GONE);
                break;
        }
    }

    ///=======================================
    //============左侧区=======================
    //========================================

    /**
     * 设置左侧Nav 文本
     */
    public void setLeftText(@StringRes int str){
        base_top_back_name.setText(str);
    }
    public void setLeftText(@NonNull CharSequence str){base_top_back_name.setText(str);}
    public void setLeftTextColor(@ColorRes int color){base_top_back_name.setTextColor(getResources().getColor(color));}
    public void setLeftTextColor(@NonNull ColorStateList color){base_top_back_name.setTextColor(color);}

    public void setLeftImage(@DrawableRes int res){
        base_top_back_img.setImageResource(res);
    }
    public void setLeftImage(@NonNull Drawable drawable){
        base_top_back_img.setImageDrawable(drawable);
    }

    public void setLeftNavShowType(@SHOWLEFT int type){
        showLeftNav(type);
    }
    ///=======================================
    //============左侧区=======================
    //========================================

    ///=======================================
    //============标题区=======================
    //========================================
    /**
     * 设置标题文字
     */
    public void setTitleText(@StringRes int str){
        base_top_title.setText(str);
    }
    public void setTitleText(CharSequence str){
        if (TextUtils.isEmpty(str)){
            base_top_title.setText("");
        }else {
            base_top_title.setText(str);
        }

    }
    /**
     * 设置标题文字颜色
     */
    public void setTitleTextColor(@ColorRes int color){
        base_top_title.setTextColor(getResources().getColor(color));
    }

    public void setTitleTextColor(@NonNull ColorStateList color){
        base_top_title.setTextColor(color);
    }

    /**
     * 设置标题文字图标
     * @param res
     * @param direction
     */
    public void setTitleImage(@DrawableRes int res, @DIRECTION int direction ){
        if (direction == NOTHING)
            return;
        Drawable drawable = getResources().getDrawable(res);
        setTextDrawable(base_top_title, drawable, direction);
    }

    public void setTitleImage(@NonNull Drawable res, @DIRECTION int direction ){
        if (direction == NOTHING)
            return;
        setTextDrawable(base_top_title, res, direction);
    }

    /**
     * 右侧文字与图标之前的距离
     * @param padding
     */
    public void setTitleImagePadding(int padding){
        base_top_title.setCompoundDrawablePadding(padding);
    }
    ///=======================================
    //============标题区=======================
    //========================================

    ///=======================================
    //============右侧区=======================
    //========================================

    /**
     * 设置通知颜色
     * @param color
     */
    public void setNotifyCountColor(@ColorRes int color){
        tv_notice_count.setTextColor(getResources().getColor(color));
    }

    public void setNotifyCountColor(@NonNull ColorStateList color){
        tv_notice_count.setTextColor(color);
    }

    /**
     * 设置通知计数，当且仅当count大于0，才显示出来
     * @param count 通知计数
     */
    public void setNotifyCountText(int count){
        if (count > 0){
            if (tv_notice_count.getVisibility() != View.VISIBLE) tv_notice_count.setVisibility(View.VISIBLE);
            tv_notice_count.setText(count+"");
        }
        else{
            if (tv_notice_count.getVisibility() != View.GONE) tv_notice_count.setVisibility(View.GONE);
        }
    }

    /**
     * 设置通知图标
     * @param drawable
     */
    public void setNotifyImage(@NonNull Drawable drawable){
        iv_toolbar_right.setImageDrawable(drawable);
    }

    public void setNotifyImage(@DrawableRes int drawableRes){
        iv_toolbar_right.setImageResource(drawableRes);
    }

    /**
     * 设置右侧Nav文字
     */
    public void setNormalRightText(@StringRes int str){
        tv_title_right.setText(str);
    }
    public void setNormalRightText(@NonNull CharSequence str){
        tv_title_right.setText(str);
    }
    /**
     * 设置右侧Nav文字颜色
     */
    public void setRightNormalTextColor(@ColorRes int color){
        tv_title_right.setTextColor(getResources().getColor(color));
    }

    public void setRightNormalTextColor(@NonNull ColorStateList color){
        tv_title_right.setTextColor(color);
    }

    /**
     * 设置右侧文字图标
     * @param res
     * @param direction
     */
    public void setRightNormalImage(@DrawableRes int res, @DIRECTION int direction ){
        if (direction == NOTHING)
            return;
        Drawable drawable = getResources().getDrawable(res);
        setTextDrawable(tv_title_right, drawable, direction);
    }

    public void setRightNormalImage(@NonNull Drawable res, @DIRECTION int direction ){
        if (direction == NOTHING)
            return;
        setTextDrawable(tv_title_right, res, direction);
    }

    /**
     * 右侧文字与图标之前的距离
     * @param padding
     */
    public void setRightNormalImagePadding(int padding){
        tv_title_right.setCompoundDrawablePadding(padding);
    }

    public void setRightShowType(@SHOWRIGHT int type){
        showRightNav(type);
    }
    ///=======================================
    //============右侧区=======================
    //========================================

    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    public static final int NOTHING = 2;
    @IntDef({LEFT, RIGHT, NOTHING})
    @Retention(RetentionPolicy.SOURCE)
    public @interface DIRECTION {

    }

    public static final int NOTIFY = 0;
    public static final int NORMAL = 1;
    public static final int NOT_ALL = 2;
    @IntDef({NOTIFY, NORMAL, NOT_ALL})
    @Retention(RetentionPolicy.SOURCE)
    public @interface SHOWRIGHT {

    }

    public static final int ALL = 0;
    public static final int NAVIGATIONIMG = 1;
    public static final int NAVIGATIONTXT = 2;
    public static final int NONE = 3;
    @IntDef({ALL, NAVIGATIONIMG, NAVIGATIONTXT, NONE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface SHOWLEFT {

    }


    private void setTextDrawable(@NonNull TextView tv, @NonNull Drawable res, @DIRECTION int direction ) {
        res.setBounds(0, 0, res.getMinimumWidth(), res.getMinimumHeight());
        switch (direction){
            case LEFT:
                tv.setCompoundDrawables(res, null,null,null);
                break;
            case RIGHT:
                tv.setCompoundDrawables(null, null, res,null);
                break;
            case NOTHING:
            default:
                break;
        }
    }

    public ImageView getBaseTopArrow() {
        return base_top_arrow;
    }

    /**
     * 设置标题右侧图标
     * @param drawable
     */
    public void setTitleArrowImage(@NonNull Drawable drawable){
        base_top_title.setCompoundDrawables(null, null,null,null);
        base_top_arrow.setVisibility(View.VISIBLE);
        base_top_arrow.setImageDrawable(drawable);
    }

    public void setTitleArrowImage(@DrawableRes int drawableRes){
        base_top_title.setCompoundDrawables(null, null,null,null);
        base_top_arrow.setVisibility(View.VISIBLE);
        base_top_arrow.setImageResource(drawableRes);
    }

}
