package fr.yodrack.matriceled;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import java.util.ArrayList;
import java.util.List;

public class CustomGridView extends View {
    private static final int NUM_ROWS = 18;
    private static final int NUM_COLS = 11;
    private static final float GRID_STROKE_WIDTH = 2f;
    private static final float CIRCLE_STROKE_WIDTH = 10f;

    private Paint gridLinePaint;
    private Paint circlePaint;
    private List<Point> selectedPoints;

    private int cellWidth = 50;  // Default cell width
    private int cellHeight = 50;  // Default cell height
    private int x_start = 0;
    private int y_start = 0;

    public CustomGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        gridLinePaint = new Paint();
        gridLinePaint.setColor(Color.BLACK);
        gridLinePaint.setStrokeWidth(GRID_STROKE_WIDTH);
        gridLinePaint.setStyle(Paint.Style.STROKE);

        circlePaint = new Paint();
        circlePaint.setColor(Color.RED);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeWidth(CIRCLE_STROKE_WIDTH);

        selectedPoints = new ArrayList<>();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Draw the grid lines
        for (int i = 0; i <= NUM_COLS; i++) {
            canvas.drawLine(x_start + i * cellWidth, y_start, x_start + i * cellWidth, y_start + NUM_ROWS * cellHeight, gridLinePaint);
        }
        for (int i = 0; i <= NUM_ROWS; i++) {
            canvas.drawLine(x_start, y_start + i * cellHeight, x_start + NUM_COLS * cellWidth, y_start + i * cellHeight, gridLinePaint);
        }

        // Draw the selected points
        for (Point point : selectedPoints) {
            float cx = x_start + (point.col + 0.5f) * cellWidth;
            float cy = y_start + (point.row + 0.5f) * cellHeight;
            float radius = Math.min(cellWidth, cellHeight) / 4f;
            canvas.drawCircle(cx, cy, radius, circlePaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            int col = (int) ((event.getX() - x_start) / cellWidth);
            int row = (int) ((event.getY() - y_start) / cellHeight);
            if (col < 0 || col > NUM_COLS - 1 || row < 0 || row > NUM_ROWS - 1) return false;
            Point point = new Point(row, col);

            if (selectedPoints.contains(point)) {
                selectedPoints.remove(point);
            } else {
                selectedPoints.add(point);
            }

            invalidate();
            return true;
        }
        return super.onTouchEvent(event);
    }

    public List<Point> getSelectedPoints() {
        return selectedPoints;
    }

    public void setInitialSelectedPoints(List<Point> points) {
        selectedPoints = points;
        invalidate();
    }

    public void setXStart(int x_start) {
        this.x_start = x_start;
        invalidate();
    }

    public void setYStart(int y_start) {
        this.y_start = y_start;
        invalidate();
    }

    public void setCellWidth(int cellWidth) {
        this.cellWidth = cellWidth;
        invalidate();
    }

    public void setCellHeight(int cellHeight) {
        this.cellHeight = cellHeight;
        invalidate();
    }

    public static List<Point> convertToPointsList(String pointsString) {
        List<Point> pointsList = new ArrayList<>();
        String[] pointsArray = pointsString.split(",");
        for (String pointString : pointsArray) {
            pointsList.add(convertToPoint(pointString.trim()));
        }
        return pointsList;
    }

    public static Point convertToPoint(String coord) {
        int col = coord.charAt(0) - 'A';
        int row = 18 - Integer.parseInt(coord.substring(1));
        return new Point(row, col);
    }

    public static class Point {
        int row;
        int col;

        Point(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Point point = (Point) o;

            if (row != point.row) return false;
            return col == point.col;
        }

        @Override
        public int hashCode() {
            int result = row;
            result = 31 * result + col;
            return result;
        }
    }
}
