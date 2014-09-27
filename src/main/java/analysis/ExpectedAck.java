package analysis;

/**
 * Created by odedl on 07/08/14.
 */
import java.util.AbstractMap;
import java.util.AbstractMap.SimpleEntry;

public class ExpectedAck
        extends AbstractMap.SimpleEntry<Long, Float>
{
    public ExpectedAck(Long key, Float value)
    {
        super(key, value);
    }
}