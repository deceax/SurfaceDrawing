package gsn.atl.surfacedrawing;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import java.util.Random;

public class ClosedCaptioningService extends Service {

    public static final String NOTIFICATION = "gsn.atl.surfacedrawing.cc";

    private static final String[] messagePool = {
            "Closed captioning is fun times for all",
            "I don't know what text to put here",
            "The quick fox jumped over the lazy dog or something",
            "All your Closed Captioning are belong to us",
            "What is man? A miserable pile of secrets!",
            "I wish I was playing disc golf right now"
    };

    public class ClosedCaptioningBinder extends Binder {
        ClosedCaptioningService getService(){
            return ClosedCaptioningService.this;
        }
    }

    @Override
    public void onCreate(){
        // start closed captioning runnable
        ccThread.start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        return START_STICKY;
    }

    @Override
    public void onDestroy(){

    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    private final IBinder binder = new ClosedCaptioningBinder();

    private void sendCC(String text){
        Intent intent = new Intent(NOTIFICATION);
        intent.putExtra("CC_MESSAGE", text);
        sendBroadcast(intent);
    }

    // this thread runs forever and posts random byte closed captioning messages every 5 seconds
    private Thread ccThread = new Thread(){
        @Override
        public void run() {
            Random random = new Random(System.currentTimeMillis());
            int i = 0, prev = 0;
            try {
                while (true){
                    while (i == prev){ // make sure we get a new message every time
                        i = random.nextInt(messagePool.length);
                    }
                    sendCC(messagePool[i]);
                    sleep(5000);
                    prev = i;
                }
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    };
}
