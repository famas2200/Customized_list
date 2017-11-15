package com.example.asuspc.customized_list.circle_view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ScrollerCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.example.asuspc.customized_list.btn1_page1Activity;

/**
 * Created by ASUS PC on 2017/10/7.
 */

public class RotatePan extends View {
    public static final int FLING_VELOCITY_DOWNSCALE = 4;
    private static final long ONE_WHEEL_TIME = 500;//旋轉一圈所需要的時間
    private Context context;
    private int panNum = 0;
    private Paint Paintlist[] = new Paint[50];
    private int[] setColor = {
            Color.rgb(255, 133, 132)
            , Color.rgb(254, 174, 105)
            , Color.rgb(249, 144, 104)
            , Color.rgb(252, 151, 132)
            , Color.rgb(253, 176, 136)
            , Color.rgb(249, 151, 39)
            , Color.rgb(252, 168, 71)
            , Color.rgb(252, 185, 109)
            , Color.rgb(245, 172, 111)
            , Color.rgb(255, 208, 152)
            , Color.rgb(255, 198, 189)
            , Color.rgb(255, 211, 164)
            , Color.rgb(255, 165, 92)
            , Color.rgb(92, 176, 180)
            , Color.rgb(155, 174, 69)
            , Color.rgb(255, 212, 109)
            , Color.rgb(255, 203, 150)
            , Color.rgb(255, 164, 103)
            , Color.rgb(255, 169, 169)
            , Color.rgb(253, 136, 136)
            , Color.rgb(214, 115, 130)
            , Color.rgb(240, 133, 114)
            , Color.rgb(246, 157, 109)
            , Color.rgb(253, 219, 115)
            , Color.rgb(252, 185, 109)
            , Color.rgb(255, 133, 132)
            , Color.rgb(254, 104, 105)
            , Color.rgb(249, 114, 94)
            , Color.rgb(252, 131, 132)
            , Color.rgb(253, 156, 156)
            , Color.rgb(249, 151, 39)
            , Color.rgb(249, 161, 59)
            , Color.rgb(252, 168, 76)
            , Color.rgb(245, 182, 111)
            , Color.rgb(255, 208, 152)
            , Color.rgb(255, 198, 189)
            , Color.rgb(255, 211, 164)
            , Color.rgb(255, 165, 92)
            , Color.rgb(92, 176, 180)
            , Color.rgb(155, 174, 69)
            , Color.rgb(255, 212, 109)
            , Color.rgb(255, 203, 150)
            , Color.rgb(255, 164, 103)
            , Color.rgb(255, 169, 169)
            , Color.rgb(253, 136, 136)
            , Color.rgb(214, 115, 130)
            , Color.rgb(240, 133, 114)
            , Color.rgb(246, 157, 109)
            , Color.rgb(251, 187, 108)
            , Color.rgb(253, 219, 115)};
    private Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private float InitAngle = 0;//起始角度
    private int radius = 0;//圓盤半徑
    private float verPanRadius;
    private float diffRadius;
//    private RotateAnimation anim;
//    private Integer[] images;
//    private String[] strs = {"a", "a", "a", "a", "a", "a"};
//    private ArrayList<CharSequence> cus_food_list = new ArrayList();
//    private List<Bitmap> bitmaps = new ArrayList<>();
    private GestureDetectorCompat mDetector;
    private ScrollerCompat scroller;
    private int screenWidth, screeHeight;

    public RotatePan(Context context) {
        this(context, null);
    }

    public RotatePan(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RotatePan(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        this.context = context;

        screeHeight = getResources().getDisplayMetrics().heightPixels;
        screenWidth = getResources().getDisplayMetrics().widthPixels;

        mDetector = new GestureDetectorCompat(context, new RotatePanGestureListener());

        scroller = ScrollerCompat.create(context);

        checkPanState(context, attrs);

        InitAngle = 360.0f / panNum;
        verPanRadius = 360.0f / panNum;
        diffRadius = verPanRadius / 2;

        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(Util.dip2px(context, 16));

        setClickable(true);

//        for (int i = 0; i < panNum; i++) {
//            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), images[i]);
//            bitmaps.add(bitmap);
//        }
    }

    private void checkPanState(Context context, AttributeSet attrs) {
//        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.luckpan);

        /*
        圓切片的數量由activity_main.xml的luckpan:pannum決定
        typedArray.getInteger(R.styleable.luckpan_pannum, 0)
        */

//        panNum = strs.length;

        //從MainActivity取得食物陣列
        panNum = btn1_page1Activity.getCus_food_list().size();

//        if (360 % panNum != 0) throw new RuntimeException("can't split pan for all icon.");
//
//        int nameArray = typedArray.getResourceId(R.styleable.luckpan_names, -1);
//
//        if (nameArray == -1) throw new RuntimeException("Can't find pan name.");
//
//        strs = context.getResources().getStringArray(nameArray);
//        int iconArray = typedArray.getResourceId(R.styleable.luckpan_icons, -1);
//
//        if (iconArray == -1) throw new RuntimeException("Can't find pan icon.");
//
//        String[] iconStrs = context.getResources().getStringArray(iconArray);
//
//        List<Integer> iconLists = new ArrayList<>();
//
//        for (int i = 0; i < iconStrs.length; i++) {
//            iconLists.add(context.getResources().getIdentifier(iconStrs[i], "mipmap", context.getPackageName()));
//        }
//
//        images = iconLists.toArray(new Integer[iconLists.size()]);
//
//        Log.d("images", Arrays.toString(images));
//
//        typedArray.recycle();
//
//        if (strs == null /*|| images == null*/) throw new RuntimeException("Can't find string or icon resources.");
//        if (strs.length != panNum /*|| images.length != panNum*/) throw new RuntimeException("The string length or icon length isn't equals panNum.");
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //wrap_content value
        int mHeight = Util.dip2px(context, 300);
        int mWidth = Util.dip2px(context, 300);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(mWidth, mHeight);
        } else if (widthSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(mWidth, heightSpecSize);
        } else if (heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSpecSize, mHeight);
        }

        int MinValue = Math.min(screenWidth, screeHeight);
        MinValue -= Util.dip2px(context, 38) * 2;
        setMeasuredDimension(MinValue, MinValue);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 獲得寬高當中最小的
        final int paddingLeft = getPaddingLeft();
        final int paddingRight = getPaddingRight();
        final int paddingTop = getPaddingTop();
        final int paddingBottom = getPaddingBottom();
        int width = getWidth() - paddingLeft - paddingRight;
        int height = getHeight() - paddingTop - paddingBottom;
        int MinValue = Math.min(width, height);

        // 獲得圓的半徑
        radius = MinValue / 2;

        RectF rectF = new RectF(getPaddingLeft(), getPaddingTop(), width, height);

        float angle;

        if (panNum % 2 != 0) {
            angle =/* (panNum % 4 == 0) ? 90-InitAngle : */ 90 - (InitAngle - diffRadius);
        } else {
            angle =/* (panNum % 4 == 0) ? 90-InitAngle : */ 90 - InitAngle;
        }

        Log.d("angle", String.valueOf(angle));

        for (int i = 0; i < panNum; i++) {
            /*
            if (i % 2 == 0) {
                //畫弧angle是起始角度,verPanRadius是弧的角度
                canvas.drawArc(rectF, angle, verPanRadius, true, dPaint);
            }
            else {
                canvas.drawArc(rectF, angle, verPanRadius, true, sPaint);
            }
            */

            Paintlist[i] = new Paint(Paint.ANTI_ALIAS_FLAG);
            Paintlist[i].setColor(setColor[i]);
            canvas.drawArc(rectF, angle, verPanRadius, true, Paintlist[i]);

            angle += verPanRadius;
        }

        /*
        for (int i = 0; i < panNum; i++) {
            drawIcon(width / 2, height / 2, radius, (panNum % 4 == 0) ? InitAngle + diffRadius : InitAngle, i, canvas);

            InitAngle += verPanRadius;
        }
        */

        for (int i = 0; i < panNum; i++) {
            if (panNum % 2 != 0) {
                drawText(/* (panNum % 4 == 0) ? 90 - (InitAngle + diffRadius + (diffRadius * 3 / 4)) : */ 90 - (InitAngle + diffRadius), String.valueOf(i + 1), 2 * radius, textPaint, canvas, rectF);
            } else {
                drawText(/* (panNum % 4 == 0) ? 90 - (InitAngle + diffRadius + (diffRadius * 3 / 4)) : */ 90 - InitAngle, String.valueOf(i + 1), 2 * radius, textPaint, canvas, rectF);
            }

            InitAngle += verPanRadius;
        }
    }

    private void drawText(float startAngle, String string, int mRadius, Paint mTextPaint, Canvas mCanvas, RectF mRange) {
        Path path = new Path();
        path.addArc(mRange, startAngle, verPanRadius);

        float textWidth = mTextPaint.measureText(string);

        //圓弧的水平偏移
        float hOffset = (panNum % 4 == 0) ? ((float) (mRadius * Math.PI / panNum / 2)) : ((float) (mRadius * Math.PI / panNum / 2 - textWidth / 2));

        //圓弧的垂直偏移
        float vOffset = mRadius / 2 / 6;

        mCanvas.drawTextOnPath(string, path, hOffset, vOffset, mTextPaint);
    }

    /*
    private void drawIcon(int xx, int yy, int mRadius, float startAngle, int i, Canvas mCanvas) {
        int imgWidth = mRadius / 4;
        float angle = (float) Math.toRadians(verPanRadius + startAngle);

        //確定圖片在圓弧中心點的位置
        float x = (float) (xx + (mRadius / 2 + mRadius / 12) * Math.cos(angle));
        float y = (float) (yy + (mRadius / 2 + mRadius / 12) * Math.sin(angle));

        //確定繪製圖片的位置
        try
        {
            RectF rect = new RectF(x - imgWidth * 2 / 3, y - imgWidth * 2 / 3, x + imgWidth * 2 / 3, y + imgWidth * 2 / 3);

            Bitmap bitmap = bitmaps.get(i);

            mCanvas.drawBitmap(bitmap, null, rect, null);
        }
        catch(Exception e)
        {

        }finally {
            if(mCanvas != null)
            {

            }
        }
    }
    */

    /*
    public void setImages(List<Bitmap> bitmaps) {
        this.bitmaps = bitmaps;

        this.invalidate();
    }

    public void setStr(String... strs) {
        this.strs = strs;

        this.invalidate();
    }
    */

    /**
     * 開始轉動
     *
     * @param pos 如果pos = -1則隨機,如果指定某個值,則會轉到某個指定區域
     */
    protected void startRotate(int pos) {
        //Rotate lap.
        int lap = (int) (Math.random() * 12) + 4;

        //Rotate angle.
        float angle = 0;

        if (pos < 0) {
            angle = (float) (Math.random() * 360.f);
        } else {
            float initPos = queryPosition();

            if (pos > initPos) {
                angle = (pos - initPos) * verPanRadius;
                lap -= 1;
                angle = 360.f - angle;
            } else if (pos < initPos) {
                angle = (initPos - pos) * verPanRadius;
            } else {
                //nothing to do.
            }
        }

        //All of the rotate angle.
        float increaseDegree = lap * 360 + angle;
        long time = (lap + (int) angle / 360) * ONE_WHEEL_TIME;
        float DesRotate = increaseDegree + InitAngle;

        //TODO 為了每次都能旋轉到轉盤正中間
        float offRotate = DesRotate % 360.f % verPanRadius;
        DesRotate -= offRotate;
        DesRotate += diffRadius;

        ValueAnimator animator = ValueAnimator.ofFloat(InitAngle, DesRotate);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setDuration(time);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float updateValue = (float) animation.getAnimatedValue();
                InitAngle = (updateValue % 360.f + 360.f) % 360.f;

                ViewCompat.postInvalidateOnAnimation(RotatePan.this);
            }
        });

        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                if (((LuckPanLayout) getParent()).getAnimationEndListener() != null) {
                    ((LuckPanLayout) getParent()).setStartBtnEnable(true);

                    ((LuckPanLayout) getParent()).setDelayTime(LuckPanLayout.DEFAULT_TIME_PERIOD);

                    ((LuckPanLayout) getParent()).getAnimationEndListener().endAnimation(queryPosition());
                }
            }
        });

        animator.start();
    }


    private int queryPosition() {
        InitAngle = (InitAngle % 360 + 360) % 360;

        /*
        int pos;

        if (panNum%2==0) {
            pos= (int) (InitAngle / verPanRadius);
        }
        else {
            pos = (int) Math.ceil(InitAngle / verPanRadius);
        }
        */

        int pos = (int) (InitAngle / verPanRadius);

        /*
        if (panNum == 4) pos++;

        if (panNum == 2) {
            if (pos == 1) {
                pos = 0;
            } else {
                pos = 1;
            }
        }
        */

        return calcumAngle(pos);
    }

    private int calcumAngle(int pos) {
        if (pos >= 0 && pos <= panNum / 2) {
            pos = panNum / 2 - pos;
        } else {
            pos = (panNum - pos) + panNum / 2;
        }

        return pos;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        clearAnimation();
    }

    //TODO 手勢處理
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean consume = mDetector.onTouchEvent(event);

        if (consume) {
            getParent().getParent().requestDisallowInterceptTouchEvent(true);

            return true;
        }

        return super.onTouchEvent(event);
    }

    public void setRotate(float rotation) {
        rotation = (rotation % 360 + 360) % 360;
        InitAngle = rotation;

        ViewCompat.postInvalidateOnAnimation(this);
    }

    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset()) {
            setRotate(scroller.getCurrY());
        }

        super.computeScroll();
    }

    private class RotatePanGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDown(MotionEvent e) {
            return super.onDown(e);
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            float centerX = (RotatePan.this.getLeft() + RotatePan.this.getRight()) * 0.5f;
            float centerY = (RotatePan.this.getTop() + RotatePan.this.getBottom()) * 0.5f;
            float scrollTheta = vectorToScalarScroll(distanceX, distanceY, e2.getX() - centerX, e2.getY() - centerY);
            float rotate = InitAngle - (int) scrollTheta / FLING_VELOCITY_DOWNSCALE;
            setRotate(rotate);

            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            float centerX = (RotatePan.this.getLeft() + RotatePan.this.getRight()) * 0.5f;
            float centerY = (RotatePan.this.getTop() + RotatePan.this.getBottom()) * 0.5f;
            float scrollTheta = vectorToScalarScroll(velocityX, velocityY, e2.getX() - centerX, e2.getY() - centerY);

            scroller.abortAnimation();

            scroller.fling(0, (int) InitAngle, 0, (int) scrollTheta / FLING_VELOCITY_DOWNSCALE, 0, 0, Integer.MIN_VALUE, Integer.MAX_VALUE);

            return true;
        }
    }

    //TODO 判斷滑動的方向
    private float vectorToScalarScroll(float dx, float dy, float x, float y) {
        float l = (float) Math.sqrt(dx * dx + dy * dy);
        float crossX = -y;
        float crossY = x;
        float dot = (crossX * dx + crossY * dy);
        float sign = Math.signum(dot);

        return l * sign;
    }
}
