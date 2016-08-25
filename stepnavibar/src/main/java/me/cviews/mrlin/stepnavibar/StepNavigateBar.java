package me.cviews.mrlin.stepnavibar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;


/**
 * Created by mrlin on 2016/8/24.
 * @author mrlin
 * 自定义的步骤导航控件
 */
public class StepNavigateBar extends View {

    private int totalStep = 2, curStep = 1;
    private Paint borderPainter, fillPainter, completeTextPainter, noCompleteTextPainter;
    private Path frontPath, behindPath;
    private String[] stepNames = new String[]{"default1", "default2"};
    private int textPadding, stepPadding, borderPadding = 2, radius;
    private int fillColor, noFillColor;
    private RectF arcRect;

    public StepNavigateBar(Context context) {
        super(context);
    }

    public StepNavigateBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.StepNavigateBar);
        borderPainter = new Paint();
        fillPainter = new Paint();
        completeTextPainter = new Paint();
        noCompleteTextPainter = new Paint();
        frontPath = new Path();
        behindPath = new Path();
        arcRect = new RectF();
        int labelSize = typedArray.getDimensionPixelSize(R.styleable.StepNavigateBar_labelSize, 25);
        textPadding = typedArray.getInteger(R.styleable.StepNavigateBar_textMarginStart, 10);
        borderPainter.setColor(typedArray.getColor(R.styleable.StepNavigateBar_borderColor, Color.GRAY));
        fillColor = typedArray.getColor(R.styleable.StepNavigateBar_fillColor, Color.CYAN);
        radius = typedArray.getInteger(R.styleable.StepNavigateBar_radius, 5);
        stepPadding = typedArray.getInteger(R.styleable.StepNavigateBar_stepSpace, 0);
        noFillColor = typedArray.getColor(R.styleable.StepNavigateBar_noFillColor, Color.WHITE);
        borderPainter.setStyle(Paint.Style.STROKE);
        borderPainter.setStrokeWidth(2);
        borderPainter.setAntiAlias(true);
        fillPainter.setStyle(Paint.Style.FILL);
        fillPainter.setAntiAlias(true);
        completeTextPainter.setColor(typedArray.getColor(
                R.styleable.StepNavigateBar_labelColor, Color.LTGRAY));
        completeTextPainter.setTextSize(labelSize);
        completeTextPainter.setAntiAlias(true);
        noCompleteTextPainter.setAntiAlias(true);
        noCompleteTextPainter.setColor(typedArray.getColor(
                R.styleable.StepNavigateBar_pendingLabelColor, Color.LTGRAY));
        noCompleteTextPainter.setTextSize(labelSize);
        typedArray.recycle();
    }

    public void setStepNames(String... names) {
        stepNames = names;
        totalStep = stepNames.length;
    }

    public void setCurrentStep(int stepIndex) {
        curStep = stepIndex;
        behindPath = new Path();
        frontPath = new Path();
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private int measureWidth(int measureSpec) {
        int resultWidth = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            // We were told how big to be
            resultWidth = specSize;
        } else {
            // Measure the text
            for (String step : stepNames) {
                resultWidth += textPadding*2 + completeTextPainter.measureText(step);
            }
            resultWidth += (stepNames.length - 1) * stepPadding;
            resultWidth += getPaddingLeft() + getPaddingRight();
            if (specMode == MeasureSpec.AT_MOST) {
                // Respect AT_MOST value if that was what is called for by
                // measureSpec
                resultWidth = Math.min(resultWidth, specSize);// 60,480
            }
        }

        return resultWidth;
    }

    private int measureHeight(int measureSpec) {
        int resultHeight;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            // We were told how big to be
            resultHeight = specSize;
        } else {
            // Measure the text (beware: ascent is a negative number)
            resultHeight = (int) (-completeTextPainter.ascent() + completeTextPainter.descent()) + getPaddingTop() + getPaddingBottom();
            if (specMode == MeasureSpec.AT_MOST) {
                // Respect AT_MOST value if that was what is called for by
                // measureSpec
                resultHeight = Math.min(resultHeight, specSize);
            }
        }
        return resultHeight;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int itemWidth = getMeasuredWidth() / totalStep;
        int itemHeight = getMeasuredHeight();
        int margin = 15;

        for (int i = 0;i < curStep; i++) {
            calcPath(frontPath, i, itemWidth, itemHeight, margin);
        }

        for (int i = curStep;i < stepNames.length; i++) {
            calcPath(behindPath, i, itemWidth, itemHeight, margin);
        }
        canvas.drawPath(frontPath, borderPainter);
        canvas.drawPath(behindPath, borderPainter);
        fillPainter.setColor(fillColor);
        canvas.drawPath(frontPath, fillPainter);
        fillPainter.setColor(noFillColor);
        canvas.drawPath(behindPath, fillPainter);

        for (int i = 0;i < curStep; i++) {
            drawText(canvas, completeTextPainter, i, itemWidth, margin);
        }

        for (int i = curStep;i < stepNames.length; i++) {
            drawText(canvas, noCompleteTextPainter, i, itemWidth, margin);
        }
    }

    private void calcPath(Path path, int stepIndex, int itemWidth, int itemHeight, int margin) {
        //画左侧
        //第一个Step矩形左边要画圆角
        if (stepIndex == 0){
            path.moveTo(radius + borderPadding, borderPadding);
            arcRect.set(borderPadding, borderPadding,
                    radius*2 + borderPadding, radius*2 +borderPadding);
            path.arcTo(arcRect, 270, -90);
            path.lineTo(itemWidth*stepIndex + borderPadding, itemHeight - borderPadding - radius);
            arcRect.set(borderPadding, itemHeight - borderPadding - radius*2,
                    radius*2 - borderPadding, itemHeight - borderPadding);
            path.arcTo(arcRect, 180, -90);
        } else {
            path.moveTo(itemWidth*stepIndex + borderPadding, borderPadding);
            path.lineTo(itemWidth*stepIndex + margin + borderPadding, itemHeight/2);
            path.lineTo(itemWidth*stepIndex + borderPadding, itemHeight - borderPadding);
        }

        //画右侧
        //最后一个矩形右边也要画圆角
        if (stepIndex == stepNames.length-1) {
            path.lineTo(itemWidth*(stepIndex+1) - borderPadding - radius, itemHeight-borderPadding);
            arcRect.set(itemWidth*(stepIndex+1) - borderPadding - radius*2, itemHeight - borderPadding - radius*2,
                    itemWidth*(stepIndex+1) - borderPadding, itemHeight - borderPadding);
            path.arcTo(arcRect, 90, -90);
            path.lineTo(itemWidth*(stepIndex+1) - borderPadding, borderPadding + radius);
            arcRect.set(itemWidth*(stepIndex+1) - borderPadding - radius*2, borderPadding,
                    itemWidth*(stepIndex+1) - borderPadding, radius*2 +borderPadding);
            path.arcTo(arcRect, 0, -90);
        } else {
            path.lineTo(itemWidth * (stepIndex + 1) - stepPadding, itemHeight-borderPadding);
            path.lineTo(itemWidth * (stepIndex + 1) - stepPadding + margin, itemHeight / 2);
            path.lineTo(itemWidth * (stepIndex + 1) - stepPadding, borderPadding);
        }
        path.close();
    }

    private void drawText(Canvas canvas, Paint paint, int stepIndex, int itemWidth, int margin) {
        //设置字体垂直居中的计算方法
        Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
        float textY = (getMeasuredHeight() - fontMetrics.top - fontMetrics.bottom) / 2;

        if (stepIndex == 0)
            canvas.drawText(stepNames[stepIndex], (float)(itemWidth*stepIndex) + textPadding, textY, paint);
        else
            canvas.drawText(stepNames[stepIndex], (float)(itemWidth*stepIndex) + margin + textPadding, textY, paint);
    }
}
