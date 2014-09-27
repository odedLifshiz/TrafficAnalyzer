package analysis;

import java.util.*;
import java.net.*;
import java.io.File;

import org.jnetpcap.*;
import org.jnetpcap.nio.JBuffer;
import org.jnetpcap.nio.JMemory;
import org.jnetpcap.packet.PcapPacket;
import org.jnetpcap.packet.PcapPacketHandler;
import org.jnetpcap.protocol.lan.Ethernet;
import org.jnetpcap.protocol.lan.SLL;
import org.jnetpcap.protocol.network.Ip4;
import org.jnetpcap.protocol.network.Ip6;
import org.jnetpcap.protocol.tcpip.Http;
import org.jnetpcap.protocol.tcpip.Tcp;
import org.jnetpcap.protocol.tcpip.Tcp.TcpOption;
import org.jnetpcap.PcapDumper;

public class CaptureAnalyzer {

    private static final double oneMillion = 1000000;
    private static int duplicatePackets;
    private static long analysisDuration;
    private static int invalidFrames;
    private static final int SYN_ACK = 18;
    private static final int SYN_NO_ACK = 2;
    private static final byte END_OF_OPTIONS_ID = 0x0;
    private static final byte NOP_ID = 0x01;
    private static final byte MSS_ID = 0x02;
    private static final byte WSCALE_ID = 0x03;
    private static final byte SAC_ACK_PERMITTED_ID = 4;
    private static final byte SACK_ID = 5;
    private static final byte TIME_STAMP_ID = 8;
	private static final int runOnAllPacketsConstant = -1;
    private static int numberOfPacketInCapture = 0;

	private static JnetPcapPacketImplementation constructPacket(PcapPacket pcapPacket) {
		Packet packet = new  JnetPcapPacketImplementation();
		addTcpOptions(pcapPacket, packet);
		return null;
	}  
	
	private static void addTcpOptions(PcapPacket pcapPacket, Packet packet) {
	  	Tcp tcp = new Tcp();
    	if(!pcapPacket.hasHeader(tcp)){
    		return;
    	}
        byte[] tcpHeader = tcp.getHeader();
        int headerLength = tcpHeader.length;
        byte optionKind = -1;
        byte optionLength;
        int positionInOptionsByteArray = 20;
        if (tcpHeader.length >= 20) {
            while (positionInOptionsByteArray < headerLength) {
                optionKind = tcpHeader[positionInOptionsByteArray];
                if (optionKind == END_OF_OPTIONS_ID) {
                    break;
                }
                if (optionKind == NOP_ID) {
                    positionInOptionsByteArray++;
                    continue;
                }
                optionLength = tcpHeader[positionInOptionsByteArray + 1];
                byte[] valueOfCurrentOptionsField = getOptionsValue(tcpHeader, positionInOptionsByteArray + 2, optionLength);

                switch (optionKind) {
                    case MSS_ID:
                        packet.setMss(getIntFromByteArray(valueOfCurrentOptionsField));
                        break;
                    case WSCALE_ID:
                        packet.setWscake((int) Math.pow(2, getIntFromByteArray(valueOfCurrentOptionsField)));
                        break;
                    case SAC_ACK_PERMITTED_ID:
                        //sackPermitted = true;
                        break;
                    case SACK_ID:
                        readSackValues(valueOfCurrentOptionsField);
                        break;
                    default:
                        break;

                }
                positionInOptionsByteArray += optionLength;
            }
        }	
	}
	private static void analyzerPacket(Packet packet, Connection connection) {
		
	}
    public static LinkedList<Connection> analyzerCapture(String capturePath){
    	LinkedList<Connection> connections = new LinkedList<Connection>();
    	final StringBuilder errbuf = new StringBuilder();
    	Pcap pcap = null;
    	
    	PcapPacketHandler<LinkedList<Connection>> jpacketHandler = new PcapPacketHandler<LinkedList<Connection>>() {  
    		  
             public void nextPacket(PcapPacket pcapPacket, LinkedList<Connection> connections) {   
            	 Packet packet = constructPacket(pcapPacket);
            	 
            	 //analyzerPacket(packet, connections);
             }
         };
         
        if(captureIsValid(capturePath)){
        	try{
        		pcap = Pcap.openOffline(capturePath, errbuf); 
        		pcap.loop(runOnAllPacketsConstant, jpacketHandler, connections);  
        	}
        	catch(Exception e){
        		e.printStackTrace();
        	}
        	finally{
        		pcap.close(); 
        	}
        }
        
    	return connections;
    	
    }

private static boolean captureIsValid(String capturePath) {
		// TODO Auto-generated method stub
		return false;
	}

  

    

    private static LinkedList<SackRange> readSackValues(byte[] optionsValue) {
        LinkedList<SackRange> sackRanges = new LinkedList<SackRange>();
    	int indexInOptionsValue = 0;
        while (indexInOptionsValue < optionsValue.length) {
            long leftEdge = getLongFromByteArray(optionsValue, indexInOptionsValue);
            indexInOptionsValue += 4;
            long rightEdge = getLongFromByteArray(optionsValue, indexInOptionsValue);
            indexInOptionsValue += 4;
            sackRanges.addLast(new SackRange(leftEdge, rightEdge));
        }
        return sackRanges;
    }

    private static long getLongFromByteArray(byte[] optionsValue, int indexInOptionsValue) {
        long value = 0;
        for (int i = 0; i < 4; i++) {
            value = (value << 8) + (optionsValue[indexInOptionsValue + i] & 0xFF);
        }
        return value;
    }

    private static byte[] getOptionsValue(byte[] tcpHeader, int offsetToOptionsValueInTcpHeader, byte optionLength) {
        int lengthOfOptionsValue = optionLength - 2;
        byte[] optionsValue = new byte[lengthOfOptionsValue];
        for (int i = 0; i < lengthOfOptionsValue; i++) {
            optionsValue[i] = tcpHeader[offsetToOptionsValueInTcpHeader + i];
        }
        return optionsValue;
    }

    private static int getIntFromByteArray(byte[] byteArray) {
        int value = 0;
        for (int i = 0; i < byteArray.length; i++) {
            value = (value << 8) + (byteArray[i] & 0xFF);
        }
        return value;
    }

}
