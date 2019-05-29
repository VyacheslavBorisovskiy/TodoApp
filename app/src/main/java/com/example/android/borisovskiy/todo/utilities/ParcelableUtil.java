package com.example.android.borisovskiy.todo.utilities;

import android.os.Parcel;
import android.os.Parcelable;

public class ParcelableUtil {

    private static Parcel parcel = Parcel.obtain();

    static byte[] marshall(Parcelable parcelable) {
        parcelable.writeToParcel(parcel, 0);
        byte[] bytes = parcel.marshall();
        parcel.recycle();
        return bytes;
    }

    private static Parcel unmarshall(byte[] bytes) {
        parcel.unmarshall(bytes, 0, bytes.length);
        parcel.setDataPosition(0);
        return parcel;
    }

    public static <T> T unmarshall(byte[] bytes, Parcelable.Creator<T> creator) {
        Parcel parcel = unmarshall(bytes);
        T result = creator.createFromParcel(parcel);
        parcel.recycle();
        return result;
    }
}