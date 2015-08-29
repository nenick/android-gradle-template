package android.database;

import android.net.Uri;

import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;
import org.robolectric.annotation.RealObject;

import java.util.ArrayList;

@Implements(ContentObservable.class)
public class ShadowContentObservable {

    @RealObject
    ContentObservable realObject;

    /**
     * Through the synchronisation from foreground and background thread the mObservers access may throw ConcurrentModificationException
     * when the list gets modified after notify an observer.
     * <p/>
     * Example is a cursorLoader after insert data on background.
     * Then notify gets called and the loader does a reload where the loader add himself as observer.
     * This operation changes existing observers list.
     */
    @Implementation
    public void dispatchChange(boolean selfChange, Uri uri) {
        synchronized (realObject.mObservers) {
            @SuppressWarnings("unchecked")
            ArrayList<ContentObserver> currentObservers = (ArrayList<ContentObserver>) realObject.mObservers.clone();
            for (ContentObserver observer : currentObservers) {
                if (!selfChange || observer.deliverSelfNotifications()) {
                    observer.dispatchChange(selfChange, uri);
                }
            }
        }
    }
}
