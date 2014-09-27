package analysis;
/**
 * Created by odedl on 07/08/14.
 */
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.InetAddress;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.TreeMap;

public class Connection
{
    private static final float oneMillion = 1000000;
    private static final int indexOfDown = 0;
    private static final int indexOfUp = 1;
    private static final int sizeOfArray = 2;
    private static final int numberOfDigitsToDisplay = 3;
    private int srcPort;
    private int dstPort;
    private InetAddress sourceAddress;
    private InetAddress destinationAddress;
    private long[] firstSequnce = new long[sizeOfArray];
    private int[] initialTTL = new int[sizeOfArray];
    private int[] numberOfPackets = new int[sizeOfArray];
    private int[] maxReceiveWindow = new int[sizeOfArray];
    private int[] lastCongestionWindow = new int[sizeOfArray];
    private int[] maxCongestionWindow = new int[sizeOfArray];
    private float[] minRtt = new float[sizeOfArray];
    private float[] maxRtt = new float[sizeOfArray];
    private float[] sumRtt = new float[sizeOfArray];
    private float[] minThroughput = new float[sizeOfArray];
    private float[] maxThroughput = new float[sizeOfArray];
    private float[] sumThroughput = new float[sizeOfArray];
    private float[] averageThroughput = new float[sizeOfArray];
    private int[] mss = new int[sizeOfArray];
    private int[] wscaleMultiplier = new int[sizeOfArray];
    private long[] bytesSeen = new long[sizeOfArray];
    private float[] throughput = new float[sizeOfArray];
    private float[] MbSeenToMbAckedRatio = new float[sizeOfArray];
    private float[] retransmissionPercent = new float[sizeOfArray];
    private float[] timeFirstPacketRelativeInSeconds = new float[sizeOfArray];
    private float[] timeLastPacket = new float[sizeOfArray];
    private long[] initialCongestionWindow = new long[sizeOfArray];
    private long[] maxAck = new long[sizeOfArray];
    private long[] maxSequence = new long[sizeOfArray];
    private int[] retransmittedBytes = new int[sizeOfArray];
    private int maxDataBytes;
    public String[] requestUrl = new String[sizeOfArray];
    private TreeMap<Float, Integer>[] receiveWindowAdvertisements = new TreeMap[sizeOfArray];
    private TreeMap<Float, Integer>[] congestionWindows = new TreeMap[sizeOfArray];
    private TreeMap<Float, Float>[] rtt = new TreeMap[sizeOfArray];
    private TreeMap<Float, Float>[] throughputAdvertisements = new TreeMap[sizeOfArray];
    private LinkedList<ExpectedAck>[] expectedAcks = new LinkedList[sizeOfArray];
    private String pathToTcptraceTsg;
    private String pathToTcptraceOwin;
    private String pathToTcptraceRtt;
    private String pathToTcptraceSsiz;
    private String pathToTcptraceTput;
    private String receiveWindowGraphPath;
    private String congestionWindowGraphPath;
    private String rttGraphPath;
    private String throughputGraphPath;

    public Connection(InetAddress srcAddr, int srcPort, InetAddress dstAddress, int dstPort, float relativeStartTimeInSeconds)
    {
        this.receiveWindowAdvertisements[0] = new TreeMap();
        this.receiveWindowAdvertisements[1] = new TreeMap();
        this.congestionWindows[0] = new TreeMap();
        this.congestionWindows[1] = new TreeMap();
        this.rtt[0] = new TreeMap();
        this.rtt[1] = new TreeMap();
        this.expectedAcks[1] = new LinkedList();
        this.expectedAcks[0] = new LinkedList();
        this.throughputAdvertisements[1] = new TreeMap();
        this.throughputAdvertisements[0] = new TreeMap();
        setSourceAddress(srcAddr);
        setSrcPort(srcPort);
        setDestinationAddress(dstAddress);
        setDstPort(dstPort);
        setTimeFirstPacketRelativeInSeconds(relativeStartTimeInSeconds, true);
    }

    public int getMss(boolean isUp)
    {
        return this.mss[getIndex(isUp)];
    }

    public void setMss(int mss, boolean isUp)
    {
        this.mss[getIndex(isUp)] = mss;
    }

    public int getWscaleMultiplier(boolean isUp)
    {
        return this.wscaleMultiplier[getIndex(isUp)];
    }

    public void setWscaleMultiplier(int wscale, boolean isUp)
    {
        this.wscaleMultiplier[getIndex(isUp)] = wscale;
    }

    public TreeMap<Float, Integer> getCongestionWindows(boolean isUp)
    {
        return this.congestionWindows[getIndex(isUp)];
    }

    public void addCongestionWindowAdvertisement(Float timeRelativeInSeconds, Integer congestionWindow, boolean isUp)
    {
        int index = getIndex(isUp);
        if (congestionWindow.intValue() > getMaxCongestionWindow(isUp)) {
            setMaxCongestionWindow(congestionWindow.intValue(), isUp);
        }
        this.congestionWindows[index].put(timeRelativeInSeconds, congestionWindow);
    }

    public TreeMap<Float, Integer> getReceiveWindowAdvertisements(boolean isUp)
    {
        return this.receiveWindowAdvertisements[getIndex(isUp)];
    }

    public void addReceiveWindowAdvertisement(Float currentRelativeTime, Integer receiveWindow, boolean isUp)
    {
        if (receiveWindow.intValue() > getMaxReceiveWindow(isUp)) {
            setMaxReceiveWindow(receiveWindow.intValue(), isUp);
        }
        this.receiveWindowAdvertisements[getIndex(isUp)].put(currentRelativeTime, receiveWindow);
    }

    public TreeMap<Float, Float> getRtts(boolean isUp)
    {
        return this.rtt[getIndex(isUp)];
    }

    public void addRttMeasurement(Float currentRelativeTime, Float rttAdvertisement, boolean isUp)
    {
        int index = getIndex(isUp);
        if (this.minRtt[index] == 0.0F) {
            this.minRtt[index] = rttAdvertisement.floatValue();
        } else if ((rttAdvertisement.floatValue() < this.minRtt[index]) && (rttAdvertisement.floatValue() > 0.0F)) {
            this.minRtt[index] = rttAdvertisement.floatValue();
        }
        if (rttAdvertisement.floatValue() > this.maxRtt[index]) {
            this.maxRtt[index] = rttAdvertisement.floatValue();
        }
        this.sumRtt[index] += rttAdvertisement.floatValue();
        this.rtt[index].put(currentRelativeTime, rttAdvertisement);
    }

    public InetAddress getSourceAddress()
    {
        return this.sourceAddress;
    }

    public void setSourceAddress(InetAddress sourceAddress)
    {
        this.sourceAddress = sourceAddress;
    }

    public int getSourcePort()
    {
        return this.srcPort;
    }

    public void setSrcPort(int srcPort)
    {
        this.srcPort = srcPort;
    }

    public InetAddress getDestinationAddress()
    {
        return this.destinationAddress;
    }

    public void setDestinationAddress(InetAddress destinationAddress)
    {
        this.destinationAddress = destinationAddress;
    }

    public int getDestinationtPort()
    {
        return this.dstPort;
    }

    public void setDstPort(int dstPort)
    {
        this.dstPort = dstPort;
    }

    public int getInitialTTL(boolean isUp)
    {
        return this.initialTTL[getIndex(isUp)];
    }

    public void setInitialTTL(int initialTTL, boolean isUp)
    {
        this.initialTTL[getIndex(isUp)] = initialTTL;
    }

    public int getInitialReceiveWindow(boolean isUp)
    {
        return ((Integer)getReceiveWindowAdvertisements(isUp).firstEntry().getValue()).intValue();
    }

    public long getInitialCongestionWindow(boolean isUp)
    {
        return this.initialCongestionWindow[getIndex(isUp)];
    }

    public void setInitialCongestionWindow(long initialCongestionWindow, boolean isUp)
    {
        this.initialCongestionWindow[getIndex(isUp)] = initialCongestionWindow;
    }

    public int getMaxReceiveWindow(boolean isUp)
    {
        return this.maxReceiveWindow[getIndex(isUp)];
    }

    private void setMaxReceiveWindow(int maxReceiveWindow, boolean isUp)
    {
        this.maxReceiveWindow[getIndex(isUp)] = maxReceiveWindow;
    }

    public int getMaxCongestionWindow(boolean isUp)
    {
        return this.maxCongestionWindow[getIndex(isUp)];
    }

    public void setMaxCongestionWindow(int maxCongestionWindow, boolean isUp)
    {
        this.maxCongestionWindow[getIndex(isUp)] = maxCongestionWindow;
    }

    public long getBytesSeen(boolean isUp)
    {
        return this.bytesSeen[getIndex(isUp)];
    }

    public void setBytesSeen(long bytesSeen, boolean isUp)
    {
        this.bytesSeen[getIndex(isUp)] = bytesSeen;
    }

    public float getMBSeen(boolean isUp)
    {
        return (float)getBytesSeen(isUp) / oneMillion;
    }

    public float getThroughputInMBytesps(boolean isUp)
    {
        return getMbAcked(!isUp) / getConnectionDurationInSeconds();
    }

    public float getMbAcked(boolean isUp)
    {
        return (float)getMaxAck(isUp) / oneMillion;
    }

    public float getConnectionDurationInSeconds()
    {
        return Math.max(getTimeLastPacketRelativeInSeconds(false) - getTimeFirstPacketRelativeInSeconds(true), getTimeLastPacketRelativeInSeconds(true) - getTimeFirstPacketRelativeInSeconds(true));
    }

    public void setThroughput(float throughput, boolean isUp)
    {
        int index = getIndex(isUp);
        this.throughput[index] = throughput;
    }

    public float getMbSeenToMbAckedRatio(boolean isUp)
    {
        if (getMBSeen(isUp) != 0 && getMbAcked(!isUp) != 0) {
            return ((getMBSeen(isUp) / getMbAcked(!isUp)) - 1) * 100;
        }else {
            return 0;
        }
    }

    public float getTimeFirstPacketRelativeInSeconds(boolean isUp)
    {
        return this.timeFirstPacketRelativeInSeconds[getIndex(isUp)];
    }

    public void setTimeFirstPacketRelativeInSeconds(float timeFirstPkt, boolean isUp)
    {
        this.timeFirstPacketRelativeInSeconds[getIndex(isUp)] = timeFirstPkt;
    }

    public long getMaxSequence(boolean isUp)
    {
        return this.maxSequence[getIndex(isUp)];
    }

    public void setMaxSequence(long maxSequence, boolean isUp)
    {
        this.maxSequence[getIndex(isUp)] = maxSequence;
    }

    public float getTimeLastPacketRelativeInSeconds(boolean isUp)
    {
        return this.timeLastPacket[getIndex(isUp)];
    }

    public void setTimeLastPacket(float timeLastPacket, boolean isUp)
    {
        this.timeLastPacket[getIndex(isUp)] = timeLastPacket;
    }

    public String getRequestUrl(boolean isUp)
    {
        return this.requestUrl[getIndex(isUp)];
    }

    public void setRequestUrl(String requestUrl, boolean isUp)
    {
        this.requestUrl[getIndex(isUp)] = requestUrl;
    }

    public int getRetransmittedBytes(boolean isUp)
    {
        return this.retransmittedBytes[getIndex(isUp)];
    }

    public void setRetransmittedBytes(int retransmittedBytes, boolean isUp)
    {
        this.retransmittedBytes[getIndex(isUp)] = retransmittedBytes;
    }

    public float getRetransmissionPercent(boolean isUp)
    {
        float MbSeen = getMBSeen(isUp);
        float MbAcked = getMbAcked(!isUp);
        float ratio = MbSeen / MbAcked;
        if (ratio > 1.0F) {
            return 100.0F * (ratio - 1.0F);
        }
        return 0.0F;
    }

    public boolean equals(Object o)
    {
        if (this == o) {
            return true;
        }
        if ((o == null) || (getClass() != o.getClass())) {
            return false;
        }
        Connection that = (Connection)o;
        if (this.dstPort != that.dstPort) {
            return false;
        }
        if (this.srcPort != that.srcPort) {
            return false;
        }
        if (this.destinationAddress != null ? !this.destinationAddress.equals(that.destinationAddress) : that.destinationAddress != null) {
            return false;
        }
        if (this.sourceAddress != null ? !this.sourceAddress.equals(that.sourceAddress) : that.sourceAddress != null) {
            return false;
        }
        if (this.timeFirstPacketRelativeInSeconds[1] != that.timeFirstPacketRelativeInSeconds[1]) {
            return false;
        }
        return true;
    }

    public int hashCode()
    {
        int result = this.sourceAddress != null ? this.sourceAddress.hashCode() : 0;
        result = 31 * result + this.srcPort;
        result = 31 * result + (this.destinationAddress != null ? this.destinationAddress.hashCode() : 0);
        result = 31 * result + this.dstPort;
        result = 31 * result + (int)this.timeFirstPacketRelativeInSeconds[1];
        return result;
    }

    public String toString()
    {
        String delimeter = "\t";
        StringBuilder result = new StringBuilder();
        boolean isDownload = isConnectionDownload();
        result.append(
                (isConnectionDownload() ? "Download" : "Upload") + delimeter +
                getSourceAddress().getHostAddress() + delimeter +
                getSourcePort() + delimeter +
                getDestinationAddress().getHostAddress() + delimeter +
                getDestinationtPort() + delimeter +
                round(getTimeFirstPacketRelativeInSeconds(true)) + delimeter +
                round(getConnectionDurationInSeconds()) + delimeter +
                round(getMBSeen(!isDownload)) + delimeter +
                round(getMbAcked(isDownload)) + delimeter +
                round(getThroughputInMbps(!isDownload)) + delimeter +
                round(getRetransmissionPercent(!isDownload)) + delimeter +
                getInitialReceiveWindow(isDownload) + delimeter +
                getMaxReceiveWindow(isDownload) + delimeter +
                getInitialCongestionWindow(!isDownload) + delimeter +
                getMaxCongestionWindow(!isDownload) + delimeter +
                round(getMinRtt(!isDownload)) + delimeter +
                round(getMaxRtt(!isDownload)) + delimeter +
                round(getAverageRtt(!isDownload)) + delimeter +
                round(getMinThroughput(!isDownload)) + delimeter +
                round(getMaxThroughput(!isDownload)) + delimeter +
                round(getAverageThroughput(!isDownload)) + delimeter +
                getMaxDataBytes() + delimeter +
                getRequestUrl(isDownload) + delimeter +
                getPathToTcptraceTsg() + delimeter +
                getPathToTcptraceOwin() + delimeter +
                getPathToTcptraceRtt() + delimeter +
                getPathToTcptraceSsiz() + delimeter +
                getPathToTcptraceTput() + delimeter +
                getReceiveWindowGraphPath() + delimeter +
                getCongestionWindowGraphPath() + delimeter +
                getRttGraphPath() + delimeter +
                getThroughputGraphPath() + "\n");

        return result.toString();
    }

    public boolean isConnectionDownload()
    {
        return getMbAcked(true) > getMbAcked(false);
    }

    public float getThroughputInMbps(boolean isUp)
    {
        return getThroughputInMBytesps(isUp) * 8.0F;
    }

    public void setFirstSequnce(long tcpSequenceNumber, boolean isUp)
    {
        int index = getIndex(isUp);
        this.firstSequnce[index] = tcpSequenceNumber;
    }

    public long getFirstSequnce(boolean isUp)
    {
        int index = getIndex(isUp);
        return this.firstSequnce[index];
    }

    public int getNumberOfPackets(boolean isUp)
    {
        return this.numberOfPackets[getIndex(isUp)];
    }

    public void incrementNumberOfPackets(boolean isUp)
    {
        this.numberOfPackets[getIndex(isUp)] += 1;
    }

    public int getTotalNumberOfPackets()
    {
        return getNumberOfPackets(true) + getNumberOfPackets(false);
    }

    private static float round(float value)
    {
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(3, RoundingMode.HALF_UP);
        return  bd.floatValue();

    }

    public String getPathToTcptraceTsg()
    {
        return this.pathToTcptraceTsg;
    }

    public void setPathToTcptraceTsg(String pathToTcptraceTsg)
    {
        this.pathToTcptraceTsg = pathToTcptraceTsg;
    }

    public String getPathToTcptraceOwin()
    {
        return this.pathToTcptraceOwin;
    }

    public void setPathToTcptraceOwin(String pathToTcptraceOwin)
    {
        this.pathToTcptraceOwin = pathToTcptraceOwin;
    }

    public String getPathToTcptraceRtt()
    {
        return this.pathToTcptraceRtt;
    }

    public void setPathToTcptraceRtt(String pathToTcptraceRtt)
    {
        this.pathToTcptraceRtt = pathToTcptraceRtt;
    }

    public String getPathToTcptraceSsiz()
    {
        return this.pathToTcptraceSsiz;
    }

    public void setPathToTcptraceSsiz(String pathToTcptraceSsiz)
    {
        this.pathToTcptraceSsiz = pathToTcptraceSsiz;
    }

    public String getPathToTcptraceTput()
    {
        return this.pathToTcptraceTput;
    }

    public void setPathToTcptraceTput(String pathToTcptraceTput)
    {
        this.pathToTcptraceTput = pathToTcptraceTput;
    }

    public void setReceiveWindowGraphPath(String receiveWindowGraphPath)
    {
        this.receiveWindowGraphPath = receiveWindowGraphPath;
    }

    public String getReceiveWindowGraphPath()
    {
        return this.receiveWindowGraphPath;
    }

    public void setCongestionWindowGraphPath(String congestionWindowGraphPath)
    {
        this.congestionWindowGraphPath = congestionWindowGraphPath;
    }

    public String getCongestionWindowGraphPath()
    {
        return this.congestionWindowGraphPath;
    }

    public long getBiggestExpectedAck(boolean isUp)
    {
        int upOrDownIndex = getIndex(isUp);
        if (this.expectedAcks[upOrDownIndex].isEmpty()) {
            return -1L;
        }
        return ((Long)((ExpectedAck)this.expectedAcks[upOrDownIndex].getLast()).getKey()).longValue();
    }

    public void addExpectedAck(ExpectedAck expectedAck, boolean isUp)
    {
        int upOrDownIndex = getIndex(isUp);
        this.expectedAcks[upOrDownIndex].add(expectedAck);
    }

    public float getRttForThisAck(long ack, float time, boolean isUp)
    {
        int index = getIndex(isUp);
        float rtt = -1.0F;
        if ((!this.expectedAcks[index].isEmpty()) && (ack <= ((Long)((ExpectedAck)this.expectedAcks[index].getLast()).getKey()).longValue()))
        {
            ListIterator<ExpectedAck> iter = this.expectedAcks[index].listIterator();
            while (iter.hasNext())
            {
                ExpectedAck current = (ExpectedAck)iter.next();
                iter.remove();
                if (((Long)current.getKey()).longValue() == ack) {
                    rtt = time - ((Float)current.getValue()).floatValue();
                }
            }
        }
        return rtt;
    }

    private int getIndex(boolean isUp)
    {
        if (isUp) {
            return 1;
        }
        return 0;
    }

    public String getRttGraphPath()
    {
        return this.rttGraphPath;
    }

    public void setRttGraphPath(String rttGraphPath)
    {
        this.rttGraphPath = rttGraphPath;
    }

    public void addThrouputMeasurement(float timeRelative, float throughput, boolean isUp)
    {
        int index = getIndex(isUp);
        if (this.minThroughput[index] == 0.0F) {
            this.minThroughput[index] = throughput;
        } else if ((throughput > 0.0F) && (throughput < this.minThroughput[index])) {
            this.minThroughput[index] = throughput;
        }
        if (throughput > this.maxThroughput[index]) {
            this.maxThroughput[index] = throughput;
        }
        this.sumThroughput[index] += throughput;

        this.throughputAdvertisements[index].put(Float.valueOf(timeRelative), Float.valueOf(throughput));
    }

    public TreeMap<Float, Float> getThroughputAdvertisements(boolean isUp)
    {
        int index = getIndex(isUp);
        return this.throughputAdvertisements[index];
    }

    public long getMaxAck(boolean isUp)
    {
        int index = getIndex(isUp);
        return this.maxAck[index];
    }

    public void setMaxAck(long maxAck, boolean isUp)
    {
        int index = getIndex(isUp);
        this.maxAck[index] = maxAck;
    }

    public void setThroughputGraphPath(String throughputGraphPath)
    {
        this.throughputGraphPath = throughputGraphPath;
    }

    public String getThroughputGraphPath()
    {
        return this.throughputGraphPath;
    }

    public float getMinRtt(boolean isup)
    {
        int index = getIndex(isup);
        return this.minRtt[index];
    }

    public float getAverageRtt(boolean isup)
    {
        int index = getIndex(isup);
        return this.sumRtt[index] / this.rtt[index].size();
    }

    public float getMaxRtt(boolean isup)
    {
        int index = getIndex(isup);
        return this.maxRtt[index];
    }

    public float getMinThroughput(boolean isup)
    {
        int index = getIndex(isup);
        return this.minThroughput[index];
    }

    public float getAverageThroughput(boolean isup)
    {
        int index = getIndex(isup);
        return this.sumThroughput[index] / this.throughputAdvertisements[index].size();
    }

    public float getMaxThroughput(boolean isup)
    {
        int index = getIndex(isup);
        return this.maxThroughput[index];
    }

    public int getMaxDataBytes()
    {
        return this.maxDataBytes;
    }

    public void setMaxDataBytes(int dataBytes)
    {
        this.maxDataBytes = dataBytes;
    }
}
