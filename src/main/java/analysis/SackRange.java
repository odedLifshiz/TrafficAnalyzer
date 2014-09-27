package analysis;
/**
 * Created by odedl on 07/08/14.
 */
public class SackRange
{
    private long leftEdge;
    private long rightEdge;

    public SackRange(long leftEdge, long rightEdge) {
    	setLeftEdge(leftEdge);
    	setRightEdge(rightEdge);
    }

	public boolean equals(Object o)
    {
        if (this == o) {
            return true;
        }
        if ((o == null) || (getClass() != o.getClass())) {
            return false;
        }
        
        SackRange sackRange = (SackRange)o;
        if (this.leftEdge != sackRange.leftEdge) {
            return false;
        }
        if (this.rightEdge != sackRange.rightEdge) {
            return false;
        }
        return true;
    }

    public int hashCode()
    {
        int result = (int)(this.leftEdge ^ this.leftEdge >>> 32);
        result = 31 * result + (int)(this.rightEdge ^ this.rightEdge >>> 32);
        return result;
    }

    public long getLeftEdge()
    {
        return this.leftEdge;
    }

    public void setLeftEdge(long leftEdge)
    {
        this.leftEdge = leftEdge;
    }

    public long getRightEdge()
    {
        return this.rightEdge;
    }

    public void setRightEdge(long rightEdge)
    {
        this.rightEdge = rightEdge;
    }
}
