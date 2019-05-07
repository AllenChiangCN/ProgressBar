package cn.allen.progressbarview;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

/**
 * Circle style progressbar
 */
public class CircleProgressBar extends View {

    public static String TAG = CircleProgressBar.class.getSimpleName();

    /**
     * current progress
     */
    private float mCurrentProgress;

    /**
     * max progress
     */
    private float mMaxProgress;

    /**
     * color of the ring
     */
    private int mRingColor;

    /**
     * color of the ring's background
     */
    private int mRingBackgroundColor;

    /**
     * width of the ring
     */
    private float mRingWidth;

    /**
     * measured width
     */
    private int mMeasuredWidth;

    /**
     * measured height
     */
    private int mMeasuredHeight;

    /**
     * actual progress bar's size
     */
    private int mActualSize;

    /**
     * paint for the ring's background
     */
    private Paint mRingBackgroundPaint;

    /**
     * paint for the ring
     */
    private Paint mRingPaint;

    private float mStartAngle;

    private float mSweepAngle;

    public CircleProgressBar(Context context) {
        this(context, null);
    }

    public CircleProgressBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CircleProgressBar, 0, 0);
        mCurrentProgress = typedArray.getFloat(R.styleable.CircleProgressBar_currentProgress, 0F);
        mMaxProgress = typedArray.getFloat(R.styleable.CircleProgressBar_maxProgress, 100F);
        mRingColor = typedArray.getColor(R.styleable.CircleProgressBar_ringColor, getResources().getColor(android.R.color.holo_red_dark));
        mRingBackgroundColor = typedArray.getColor(R.styleable.CircleProgressBar_ringBackgroundColor, getResources().getColor(android.R.color.darker_gray));
        mRingWidth = typedArray.getDimension(R.styleable.CircleProgressBar_ringWidth, 5F);
        typedArray.recycle();
        mStartAngle = -90F;
        mSweepAngle = mCurrentProgress / mMaxProgress * 360;

        init();
    }

    private void init() {
        mRingBackgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mRingBackgroundPaint.setStyle(Paint.Style.STROKE);
        mRingBackgroundPaint.setColor(mRingBackgroundColor);
        mRingBackgroundPaint.setStrokeWidth(mRingWidth);
        mRingPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mRingPaint.setStyle(Paint.Style.STROKE);
        mRingPaint.setColor(mRingColor);
        mRingPaint.setStrokeWidth(mRingWidth);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int desiredWidth = getSuggestedMinimumWidth() + getPaddingStart() + getPaddingEnd();
        int desiredHeight = getSuggestedMinimumHeight() + getPaddingTop() + getPaddingBottom();
        mMeasuredWidth = measureDimension(desiredWidth, widthMeasureSpec);
        mMeasuredHeight = measureDimension(desiredHeight, heightMeasureSpec);
        mActualSize = Math.min(mMeasuredWidth - getPaddingStart() - getPaddingEnd(), mMeasuredHeight - getPaddingTop() - getPaddingBottom());
        setMeasuredDimension(mMeasuredWidth, mMeasuredHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // draw ring's background
        canvas.drawCircle(getPaddingStart() + (getMeasuredWidth() - getPaddingStart() - getPaddingEnd()) / 2F, getPaddingTop() + (getMeasuredHeight() - getPaddingTop() - getPaddingBottom()) / 2F, mActualSize / 2F - mRingWidth, mRingBackgroundPaint);
        // draw done part of ring
        canvas.drawArc(getPaddingStart() + (getMeasuredWidth() - getPaddingStart() - getPaddingEnd()) / 2F - mActualSize / 2F + mRingWidth,
                getPaddingTop() + (getMeasuredHeight() - getPaddingTop() - getPaddingBottom()) / 2F - mActualSize / 2F + mRingWidth,
                getPaddingStart() + (getMeasuredWidth() - getPaddingStart() - getPaddingEnd()) / 2F + mActualSize / 2F - mRingWidth,
                getPaddingTop() + (getMeasuredHeight() - getPaddingTop() - getPaddingBottom()) / 2F + mActualSize / 2F - mRingWidth,
                mStartAngle,
                mSweepAngle,
                false,
                mRingPaint);
    }

    /**
     * Measure dimension of this view
     *
     * @param desiredSize desired size
     * @param measureSpec MeasureSpec
     * @return measured dimension
     */
    private int measureDimension(int desiredSize, int measureSpec) {
        int result;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = desiredSize;
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        if (result < desiredSize) {
            Log.e(TAG, "The view is too small, the content might get cut");
        }
        return result;
    }

    public float getCurrentProgress() {
        return mCurrentProgress;
    }

    public void setCurrentProgress(float currentProgress) {
        if (mCurrentProgress <= mMaxProgress && mCurrentProgress >= 0) {
            mCurrentProgress = currentProgress;
            mSweepAngle = mCurrentProgress / mMaxProgress * 360;
            invalidate();
        } else {
            Log.d(TAG, "incorrect progress");
        }
    }

    public void setCurrentProgressWithAnimation(float currentProgress) {
        Animator animator = ObjectAnimator.ofFloat(this, "currentProgress", currentProgress);
        animator.setDuration(1000L);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.start();
    }
}
