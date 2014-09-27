package analysis;
/**
 * Created by odedl on 07/08/14.
 */
import java.net.InetAddress;
import java.util.LinkedList;

public class JnetPcapPacketImplementation implements Packet {

    private boolean isIpv4;
    private boolean isCooked;
    private InetAddress srcAddress;
    private int srcPort;
    private InetAddress dstAddress;
    private int dstPort;
    private int frameNumber;
    private float timeRelative;
    private int sllPktType;
    private long tcpSequenceNumber;
    private long tcpAck;
    private int tcpLen;
    private int tcpWindowSize;
    private boolean tcpFlagsSyn;
    private boolean tcpFlagsAck;
    private boolean tcpFlagsFin;
    private boolean tcpFlagsReset;
    private int ttl;
    private int ipId;
    private int tcpOptionsWscaleMultiplier;
    private boolean isRetransmission;
    private boolean isFastRetransmission;
    private float rtt;
    private int httpResponseCode;
    private int httpContentLength;
    private HttpMethod httpMethod;
    private InetAddress httpHost;
    private String httpRequestUri;
    private String httpConnection;
    private String httpReferer;
    private LinkedList<SackRange> sackRanges = new LinkedList();

    private static final String flagIsSetString = "1";
    private boolean isUp;

    public boolean isDuplicate(Object o)
    {
        if (this == o) {
            return true;
        }
        if ((o == null) || (getClass() != o.getClass())) {
            return false;
        }
        Packet that = (Packet)o;
        if (this.timeRelative - that.getTimeRelative() > (float)Config.MaxDeltaBetweenDuplicatePacketsInMicros) {
            return false;
        }
        if ((this.isIpv4) && (that.getIsIpv4()) && (this.ipId != that.getIpId())) {
            return false;
        }
        if (!this.sackRanges.equals(that.getSackRanges())) {
            return false;
        }
        if (this.tcpSequenceNumber != that.getTcpSequenceNumber()) {
            return false;
        }
        if (this.tcpAck != that.getTcpAck()) {
            return false;
        }
        if (this.dstPort != that.getDstPort()) {
            return false;
        }
        if (Math.abs(this.ttl - that.getTtl()) > Config.MaxTTLDeltaBetweenDuplicates) {
            return false;
        }
        if (this.srcPort != that.getSrcPort()) {
            return false;
        }
        if (this.dstAddress != null ? !this.dstAddress.equals(that.getDstAddress()) : that.getDstAddress() != null) {
            return false;
        }
        if (this.srcAddress != null ? !this.srcAddress.equals(that.getSrcAddress()) : that.getSrcAddress() != null) {
            return false;
        }
        return true;
    }

	public boolean getIsIpv4() {
		return isIpv4;
	}

	public void setIpv4(boolean isIpv4) {
		this.isIpv4 = isIpv4;
	}

	public boolean getIsCooked() {
		return isCooked;
	}

	public void setIsCooked(boolean isCooked) {
		this.isCooked = isCooked;
	}

	public InetAddress getSrcAddress() {
		return srcAddress;
	}

	public void setSrcAddress(InetAddress srcAddress) {
		this.srcAddress = srcAddress;
	}

	public int getSrcPort() {
		return srcPort;
	}

	public void setSrcPort(int srcPort) {
		this.srcPort = srcPort;
	}

	public InetAddress getDstAddress() {
		return dstAddress;
	}

	public void setDstAddress(InetAddress dstAddress) {
		this.dstAddress = dstAddress;
	}

	public int getDstPort() {
		return dstPort;
	}

	public void setDstPort(int dstPort) {
		this.dstPort = dstPort;
	}

	public int getFrameNumber() {
		return frameNumber;
	}

	public void setFrameNumber(int frameNumber) {
		this.frameNumber = frameNumber;
	}

	public float getTimeRelative() {
		return timeRelative;
	}

	public void setTimeRelative(float timeRelative) {
		this.timeRelative = timeRelative;
	}

	public int getSllPktType() {
		return sllPktType;
	}

	public void setSllPktType(int sllPktType) {
		this.sllPktType = sllPktType;
	}

	public long getTcpSequenceNumber() {
		return tcpSequenceNumber;
	}

	public void setTcpSequenceNumber(long tcpSequenceNumber) {
		this.tcpSequenceNumber = tcpSequenceNumber;
	}

	public long getTcpAck() {
		return tcpAck;
	}

	public void setTcpAck(long tcpAck) {
		this.tcpAck = tcpAck;
	}

	public int getTcpLen() {
		return tcpLen;
	}

	public void setTcpLen(int tcpLen) {
		this.tcpLen = tcpLen;
	}

	public int getTcpWindowSize() {
		return tcpWindowSize;
	}

	public void setTcpWindowSize(int tcpWindowSize) {
		this.tcpWindowSize = tcpWindowSize;
	}

	public boolean getIsTcpFlagsSyn() {
		return tcpFlagsSyn;
	}

	public void setTcpFlagsSyn(boolean tcpFlagsSyn) {
		this.tcpFlagsSyn = tcpFlagsSyn;
	}

	public boolean getIsTcpFlagsAck() {
		return tcpFlagsAck;
	}

	public void setTcpFlagsAck(boolean tcpFlagsAck) {
		this.tcpFlagsAck = tcpFlagsAck;
	}

	public boolean getIsTcpFlagsFin() {
		return tcpFlagsFin;
	}

	public void setTcpFlagsFin(boolean tcpFlagsFin) {
		this.tcpFlagsFin = tcpFlagsFin;
	}

	public boolean getIsTcpFlagsReset() {
		return tcpFlagsReset;
	}

	public void setTcpFlagsReset(boolean tcpFlagsReset) {
		this.tcpFlagsReset = tcpFlagsReset;
	}

	public int getTtl() {
		return ttl;
	}

	public void setTtl(int ttl) {
		this.ttl = ttl;
	}

	public int getIpId() {
		return ipId;
	}

	public void setIpId(int ipId) {
		this.ipId = ipId;
	}

	public int getTcpOptionsWscaleMultiplier() {
		return tcpOptionsWscaleMultiplier;
	}

	public void setTcpOptionsWscaleMultiplier(int tcpOptionsWscaleMultiplier) {
		this.tcpOptionsWscaleMultiplier = tcpOptionsWscaleMultiplier;
	}

	public boolean getIsRetransmission() {
		return isRetransmission;
	}

	public void setRetransmission(boolean isRetransmission) {
		this.isRetransmission = isRetransmission;
	}

	public boolean getIsFastRetransmission() {
		return isFastRetransmission;
	}

	public void setFastRetransmission(boolean isFastRetransmission) {
		this.isFastRetransmission = isFastRetransmission;
	}

	public float getRtt() {
		return rtt;
	}

	public void setRtt(float rtt) {
		this.rtt = rtt;
	}

	public int getHttpResponseCode() {
		return httpResponseCode;
	}

	public void setHttpResponseCode(int httpResponseCode) {
		this.httpResponseCode = httpResponseCode;
	}

	public int getHttpContentLength() {
		return httpContentLength;
	}

	public void setHttpContentLength(int httpContentLength) {
		this.httpContentLength = httpContentLength;
	}

	public HttpMethod getHttpMethod() {
		return httpMethod;
	}

	public void setHttpMethod(HttpMethod httpMethod) {
		this.httpMethod = httpMethod;
	}

	public InetAddress getHttpHost() {
		return httpHost;
	}

	public void setHttpHost(InetAddress httpHost) {
		this.httpHost = httpHost;
	}

	public String getHttpRequestUri() {
		return httpRequestUri;
	}

	public void setHttpRequestUri(String httpRequestUri) {
		this.httpRequestUri = httpRequestUri;
	}

	public String getHttpConnection() {
		return httpConnection;
	}

	public void setHttpConnection(String httpConnection) {
		this.httpConnection = httpConnection;
	}

	public String getHttpReferer() {
		return httpReferer;
	}

	public void setHttpReferer(String httpReferer) {
		this.httpReferer = httpReferer;
	}

	public LinkedList<SackRange> getSackRanges() {
		return sackRanges;
	}

	public void setSackRanges(LinkedList<SackRange> sackRanges) {
		this.sackRanges = sackRanges;
	}

	public boolean isUp() {
		return isUp;
	}

	public void setUp(boolean isUp) {
		this.isUp = isUp;
	}

	public static String getFlagissetstring() {
		return flagIsSetString;
	}

	public int getMss() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getWscale() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void setIsIpv4(boolean isIpv4) {
		// TODO Auto-generated method stub
		
	}

	public void setIsTcpFlagsSyn(boolean isTcpFlagsSyn) {
		// TODO Auto-generated method stub
		
	}

	public void setIsTcpFlagsAck(boolean isTcpFlagsAck) {
		// TODO Auto-generated method stub
		
	}

	public void setIsTcpFlagsFin(boolean isTcpFlagsFin) {
		// TODO Auto-generated method stub
		
	}

	public void setIsTcpFlagsReset(boolean isTcpFlagsReset) {
		// TODO Auto-generated method stub
		
	}

	public void setIsRetransmission(boolean isRetransmission) {
		// TODO Auto-generated method stub
		
	}

	public void setIsFastRetransmission(boolean isFastRetransmission) {
		// TODO Auto-generated method stub
		
	}

	public void setHttpHost(String httpHost) {
		// TODO Auto-generated method stub
		
	}

	public void setMss(int mss) {
		// TODO Auto-generated method stub
		
	}

	public void setWscake(int wscale) {
		// TODO Auto-generated method stub
		
	}


}


