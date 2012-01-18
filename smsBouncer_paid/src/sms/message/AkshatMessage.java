package sms.message;

import android.os.Parcel;
import android.os.Parcelable;

public class AkshatMessage implements Parcelable
{
    private static final String TO_STRING_DELIMITER = "&#,#&";

    public AkshatMessage()
    {
    }

    AkshatMessage(Parcel in)
    {
	readFromParcel(in);
    }

    public AkshatMessage(String akshatMessageString)
    {
	String[] trio = akshatMessageString.split(TO_STRING_DELIMITER);
	if (trio.length == 4)
	{
	    this.smsFrom = trio[0];
	    this.smsBody = trio[2];
	    this.smsBlockedFor = trio[3];
	    try
	    {
		this.smsTimestamp = Long.parseLong(trio[1].trim());
	    }
	    catch (NumberFormatException nfe)
	    {
		this.smsTimestamp = 0;
	    }
	}
    }

    public AkshatMessage(String from, String body, long timeStamp, String blockedFor)
    {
	this.smsFrom = from;
	this.smsBody = body;
	this.smsTimestamp = timeStamp;
	this.smsBlockedFor = blockedFor;

    }

    public String getSmsBody()
    {
	return smsBody;
    }

    public void setSmsBody(String smsBody)
    {
	this.smsBody = smsBody;
    }

    public String getSmsFrom()
    {
	return smsFrom;
    }

    public void setSmsFrom(String smsFrom)
    {
	this.smsFrom = smsFrom;
    }

    public long getSmsTimestamp()
    {
	return smsTimestamp;
    }

    public void setSmsTimestamp(long smsTimestamp)
    {
	this.smsTimestamp = smsTimestamp;
    }

    private String smsBody;
    private String smsFrom;
    private String smsBlockedFor;
    private long smsTimestamp;

    public String getSmsBlockedFor()
    {
        return smsBlockedFor;
    }

    public void setSmsBlockedFor(String smsBlockedFor)
    {
        this.smsBlockedFor = smsBlockedFor;
    }

    @Override
    public String toString()
    {
	return smsFrom + TO_STRING_DELIMITER + Long.toString(smsTimestamp) + TO_STRING_DELIMITER + smsBody + TO_STRING_DELIMITER
		+ smsBlockedFor;
    }

    @Override
    public int describeContents()
    {
	// TODO Auto-generated method stub
	return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
	dest.writeString(smsFrom);
	dest.writeString(smsBody);
	dest.writeLong(smsTimestamp);
	dest.writeString(smsBlockedFor);
    }

    void readFromParcel(Parcel in)
    {
	this.smsFrom = in.readString();
	this.smsBody = in.readString();
	this.smsTimestamp = in.readLong();
	this.smsBlockedFor = in.readString();
    }

    @SuppressWarnings("rawtypes")
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator()
    {
	public AkshatMessage createFromParcel(Parcel in)
	{
	    return new AkshatMessage(in);
	}

	public AkshatMessage[] newArray(int size)
	{
	    return new AkshatMessage[size];
	}
    };
}
