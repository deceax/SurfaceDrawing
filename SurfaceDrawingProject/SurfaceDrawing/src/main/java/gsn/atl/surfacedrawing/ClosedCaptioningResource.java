package gsn.atl.surfacedrawing;

import android.graphics.*;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.SurfaceHolder;

public class ClosedCaptioningResource implements Parcelable{
    private static final String TAG = "Closed Captioning Resource";

    private int x;
    private int y;
    private int action;
    private String text; // this will be a bitmap in the real app

    public static final int SHOW = 0;
    public static final int CLEAR = 1;
    public static final int CLEAR_ALL= 2;
    public static final int MOVE = 3;

    public static SurfaceHolder holder;

    public ClosedCaptioningResource(int x, int y, String text, int action){
        this.x = x;
        this.y = y;
        this.text = text;
        this.action = action;
    }

    private ClosedCaptioningResource(Parcel in){
        this.x = in.readInt();
        this.y = in.readInt();
        this.text = in.readString();
        this.action = in.readInt();
    }

    public void act(){
        switch(action){
            case SHOW:
                show();
                break;
            case CLEAR:
                clear();
                break;
            case CLEAR_ALL:
                clearAll();
                break;
            case MOVE:
                move();
                break;
        }
    }

    private void show() {
        holder.setFormat(PixelFormat.TRANSPARENT);
        Canvas canvas = holder.lockCanvas(null);
        if (canvas != null) {
            Paint paint = new Paint();
            paint.setColor(0xffff0000);
            paint.setTextSize(96.0f);
            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR); // clears screen
            canvas.drawText(text, x, y, paint);
        }
        holder.unlockCanvasAndPost(canvas);
    }

    private void clear(){
        // how are we going to clear a specific image??
    }

    private void clearAll(){
        // why can one closed captioning resource erase the entire canvas?
    }

    private void move(){
        clear();
        show();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(x);
        dest.writeInt(y);
        dest.writeString(text);
        dest.writeInt(action);
    }

    public static final Parcelable.Creator<ClosedCaptioningResource> CREATOR = new Creator<ClosedCaptioningResource>() {
        public ClosedCaptioningResource createFromParcel(Parcel in){
            return new ClosedCaptioningResource(in);
        }

        public ClosedCaptioningResource[] newArray(int size){
            return new ClosedCaptioningResource[size];
        }
    };
}
