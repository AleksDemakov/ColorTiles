package com.example.colortiles;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class TilesView extends View {
    int n = 3, m = 3, margin = 10;
    boolean[][] tiles;
    float width, height;
    float tileW, tileH;
    Paint painter = new Paint();
    int darkColor = Color.GRAY;
    int brightColor = Color.BLACK;

    int hintColor = Color.YELLOW;
    HashSet<Pair<Integer, Integer >> hintSet = new HashSet<Pair<Integer, Integer >>();
    int hintI = 0, hintJ = 0;
    int secBeforeHint = 10000;
    boolean isHint = false;
    Context context;
    CountDownTimer hintTimer = null;
    boolean checkHints;

    public TilesView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public void setSize(int rows, int columns, boolean checkHints){
        tiles =  new boolean[rows+1][columns+1];
        n = rows;
        m = columns;
        int cnt=0;
        for(int i=0;i<n;i++)
            for(int j=0;j<m;j++){
                if(cnt >= n*m/2+1)break;
                if(Math.random() < 0.5) {
                    click(i, j);
                    cnt++;
                }
            }
        isHint = false;
        this.checkHints = checkHints;
        if(checkHints)
            runCountdown();
        invalidate();
    }

    void hintSetAdd(int x, int y) {
        Pair p = new Pair(x, y);
        if(hintSet.contains(p)){
            hintSet.remove(p);
        }else{
            hintSet.add(p);
        }
    }

    public Pair<Integer, Integer> nextHint() {
        Random r = new Random();
        int item = r.nextInt(hintSet.size());
        int i = 0;
        for(Pair<Integer, Integer > obj : hintSet)
        {
            if (i == item)
                return obj;
            i++;
        }
        return null;
    }

    void click(int x, int y) {
        hintSetAdd(x, y);
        tiles[x][y] ^= true;
        System.out.println(this.m);
        for(int j = 0; j < m; j++)
            tiles[x][j]  ^= true;
        for(int i=0;i<n;i++)
            tiles[i][y]  ^= true;
        if(checkHints)
            runCountdown();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        width = canvas.getWidth();
        height = canvas.getHeight();
        tileW = (width - (m-1)*margin) / m;
        tileH = (height - (n-1)*margin) / n;
        float x = 0, y = 0;
        int cnt = 0;
        for(int i = 0; i < n; i++, y += tileH + margin) {
            x = 0;
            for (int j = 0; j < m; j++, x += tileW + margin)  {
                cnt += (tiles[i][j]==true?0:1);
                if(isHint && hintI == i && hintJ == j){
                    painter.setColor(hintColor);
                }else if(tiles[i][j])
                    painter.setColor(brightColor);
                else
                    painter.setColor(darkColor);
                canvas.drawRect(x, y, x + tileW, y + tileH, painter);
            }
        }
        if(cnt == n*m) {
            Toast.makeText(context, "You Win!", Toast.LENGTH_LONG).show();
            isHint = false;
            if(hintTimer != null)
                this.hintTimer.cancel();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        int touchJ = x / (int)tileW;
        int touchI = y / (int)tileH;
        click(touchI, touchJ);
        invalidate();
        return super.onTouchEvent(event);
    }

    private void runCountdown() {
        if(hintTimer == null) {

        }else{
            hintTimer.cancel();
            isHint = false;
        }
        hintTimer = new CountDownTimer(secBeforeHint, 1000) {

            public void onTick(long millisUntilFinished) {
                System.out.println("sec before hint: " + millisUntilFinished / 1000);
                if(millisUntilFinished / 1000 == 1){
                    Toast.makeText(context, "Click yellow tile", Toast.LENGTH_SHORT).show();

                    isHint = true;
                    Pair<Integer, Integer> p = nextHint();
                    hintI = p.first;
                    hintJ = p.second;
                    invalidate();
                }
            }

            public void onFinish() {
                System.out.println("hint!");
                isHint = false;
                invalidate();
            }
        }.start();
    }

}
