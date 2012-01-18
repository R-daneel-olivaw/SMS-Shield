package sms.shieldpro;

public class BlockedEntity implements Comparable<BlockedEntity>
{
    public static String TO_STRING_DELIMITER = "&#,#&";

    private String entityIdentifier;
    private int blockedCount;
    private boolean blockingStatus;

    public BlockedEntity(String blockedEntityString)
    {
	String[] trio = blockedEntityString.split(TO_STRING_DELIMITER);
	if (trio.length == 3)
	{
	    entityIdentifier = trio[0];
	    try
	    {
		blockedCount = Integer.parseInt(trio[1].trim());
		blockingStatus = Boolean.parseBoolean(trio[2].trim());
	    }
	    catch (NumberFormatException nfe)
	    {
		entityIdentifier = null;
		blockedCount = 0;
	    }
	}
    }

    public BlockedEntity(String string, int i, boolean b)
    {
	entityIdentifier = string;
	blockedCount = i;
	blockingStatus = b;
    }

    public boolean isBlockingStatus()
    {
	return blockingStatus;
    }

    public void setBlockingStatus(boolean blockingStatus)
    {
	this.blockingStatus = blockingStatus;
    }

    public int getBlockedCount()
    {
	return blockedCount;
    }

    public void setBlockedCount(int blockedCount)
    {
	this.blockedCount = blockedCount;
    }

    public void setEntityIdentifier(String entityIdentifier)
    {
	this.entityIdentifier = entityIdentifier;
    }

    public String getEntityIdentifier()
    {
	return entityIdentifier;
    }

    @Override
    public String toString()
    {
	return entityIdentifier + TO_STRING_DELIMITER + Integer.toString(blockedCount) + TO_STRING_DELIMITER
		+ Boolean.toString(blockingStatus);
    }

    @Override
    public int compareTo(BlockedEntity another)
    {
	if (this.entityIdentifier != null)
	    if (this.entityIdentifier.trim().equalsIgnoreCase(another.getEntityIdentifier().trim()))
	    {
		return 0;
	    }

	return -1;
    }
}
