/**
 * Project Name:iatp-basic-common
 * File Name:IPSourceFormat.java
 * Package Name:com.smart.platform.toolkit
 * Date:2016年11月5日下午6:22:37
 * Copyright (c) 2016, chenzhou1025@126.com All Rights Reserved.
 *
*/

package com.smart.platform.toolkit;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ClassName:IPSourceFormat <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2016年11月5日 下午6:22:37 <br/>
 * 
 * @author 01135912
 * @version
 * @since JDK 1.6
 * @see
 */
public class IPSourceUtil
{
    private static final String SOUCE_SPLIT_SPACE = " ";
    
    private static final String SOUCE_SPLIT_SIGN = ".";
    
    private static final String SOUCE_SEGMENT_SIGN = "/";
    
    private static final String SOUCE_MASK_NOE_ONE_32 = "0";
    
    private static final String SOUCE_MASK_NO_START = "0.";
    
    private static final String SOUCE_MASK_FULL_INT = "32";
    
    private final static String A_TYPE_IP_SIGN = "A";
    
    private final static String B_TYPE_IP_SIGN = "B";
    
    private final static String C_TYPE_IP_SIGN = "C";
    
    private final static String D_TYPE_IP_SIGN = "D";
    
    private final static String E_TYPE_IP_SIGN = "E";
    
    /** A类地址1.0.0.0—127.255.255.255 */
    private final static int A_TYPE_IP_START = 0X01;
    
    private final static int A_TYPE_IP_END = 0X7F;
    
    private final static String LOCAL_IP_STR = "127.0.0.1";
    
    private final static String LOCAL_MACHINE_STR = "0:0:0:0:0:0:0:1";
    
    private final static String A_TYPE_IP_SUB_PRIVATE_START = "10.0.0.0";
    
    private final static String A_TYPE_IP_SUB_PRIVATE_END = "10.255.255.255";
    
    /** B:128.0.0.0—191.255.255.255 */
    private final static int B_TYPE_IP_START = A_TYPE_IP_END + 1;
    
    private final static int B_TYPE_IP_END = 0XBF;
    
    private final static String B_TYPE_IP_SUB_PRIVATE_START = "172.16.0.0";
    
    private final static String B_TYPE_IP_SUB_PRIVATE_END = "172.31.255.255";
    
    /** C:192.0.0.0—223.255.255.255 */
    private final static int C_TYPE_IP_START = B_TYPE_IP_END + 1;
    
    private final static int C_TYPE_IP_END = 0XDF;
    
    private final static String C_TYPE_IP_SUB_PRIVATE_START = "192.168.0.0";
    
    private final static String C_TYPE_IP_SUB_PRIVATE_END = "192.168.255.255";
    
    /** D:224.0.0.0—239.255.255.255 */
    private final static int D_TYPE_IP_START = C_TYPE_IP_END + 1;
    
    private final static int D_TYPE_IP_END = 0XEF;
    
    /** E:240.0.0.0—247.255.255.255 */
    private final static int E_TYPE_IP_START = D_TYPE_IP_END + 1;
    
    private final static int E_TYPE_IP_END = 0XF7;
    
    private static final Map<String, int[]> TYPE_IP_MAP = new HashMap<String, int[]>();
    
    private static final Map<String, String[]> TYPE_IP_SUB_PRIVATE_MAP = new HashMap<String, String[]>();
    
    private static Map<String, String> IP_MASK_BIT = new HashMap<String, String>();
    
    private static final Logger LOG = LoggerFactory.getLogger(IPSourceUtil.class);
    /*
     * 存储着所有的掩码位及对应的掩码 key:掩码位 value:掩码（x.x.x.x）
     */
    static
    {
        IP_MASK_BIT.put("1", "128.0.0.0");
        IP_MASK_BIT.put("2", "192.0.0.0");
        IP_MASK_BIT.put("3", "224.0.0.0");
        IP_MASK_BIT.put("4", "240.0.0.0");
        IP_MASK_BIT.put("5", "248.0.0.0");
        IP_MASK_BIT.put("6", "252.0.0.0");
        IP_MASK_BIT.put("7", "254.0.0.0");
        IP_MASK_BIT.put("8", "255.0.0.0");
        IP_MASK_BIT.put("9", "255.128.0.0");
        IP_MASK_BIT.put("10", "255.192.0.0");
        IP_MASK_BIT.put("11", "255.224.0.0");
        IP_MASK_BIT.put("12", "255.240.0.0");
        IP_MASK_BIT.put("13", "255.248.0.0");
        IP_MASK_BIT.put("14", "255.252.0.0");
        IP_MASK_BIT.put("15", "255.254.0.0");
        IP_MASK_BIT.put("16", "255.255.0.0");
        IP_MASK_BIT.put("17", "255.255.128.0");
        IP_MASK_BIT.put("18", "255.255.192.0");
        IP_MASK_BIT.put("19", "255.255.224.0");
        IP_MASK_BIT.put("20", "255.255.240.0");
        IP_MASK_BIT.put("21", "255.255.248.0");
        IP_MASK_BIT.put("22", "255.255.252.0");
        IP_MASK_BIT.put("23", "255.255.254.0");
        IP_MASK_BIT.put("24", "255.255.255.0");
        IP_MASK_BIT.put("25", "255.255.255.128");
        IP_MASK_BIT.put("26", "255.255.255.192");
        IP_MASK_BIT.put("27", "255.255.255.224");
        IP_MASK_BIT.put("28", "255.255.255.240");
        IP_MASK_BIT.put("29", "255.255.255.248");
        IP_MASK_BIT.put("30", "255.255.255.252");
        IP_MASK_BIT.put("31", "255.255.255.254");
        IP_MASK_BIT.put("32", "255.255.255.255");
        TYPE_IP_MAP.put(A_TYPE_IP_SIGN, new int[] {A_TYPE_IP_START, A_TYPE_IP_END});
        TYPE_IP_MAP.put(B_TYPE_IP_SIGN, new int[] {B_TYPE_IP_START, B_TYPE_IP_END});
        TYPE_IP_MAP.put(C_TYPE_IP_SIGN, new int[] {C_TYPE_IP_START, C_TYPE_IP_END});
        TYPE_IP_MAP.put(D_TYPE_IP_SIGN, new int[] {D_TYPE_IP_START, D_TYPE_IP_END});
        TYPE_IP_MAP.put(E_TYPE_IP_SIGN, new int[] {E_TYPE_IP_START, E_TYPE_IP_END});
        TYPE_IP_SUB_PRIVATE_MAP.put(A_TYPE_IP_SIGN,
            new String[] {A_TYPE_IP_SUB_PRIVATE_START, A_TYPE_IP_SUB_PRIVATE_END});
        TYPE_IP_SUB_PRIVATE_MAP.put(B_TYPE_IP_SIGN,
            new String[] {B_TYPE_IP_SUB_PRIVATE_START, B_TYPE_IP_SUB_PRIVATE_END});
        TYPE_IP_SUB_PRIVATE_MAP.put(C_TYPE_IP_SIGN,
            new String[] {C_TYPE_IP_SUB_PRIVATE_START, C_TYPE_IP_SUB_PRIVATE_END});
    }
    
    /**
     * A:1.0.0.0—127.255.255.255 B:128.0.0.0—191.255.255.255 C:192.0.0.0—223.255.255.255 D:224.0.0.0—239.255.255.255
     * E:240.0.0.0—247.255.255.255
     * 
     * @author 01135912
     * @param ipStr
     * @return
     */
    public static String getIpType(String ipAddr)
    {
        String[] ipItemArray = ipAddr.split("\\.");
        if (ipItemArray.length < 4)
        {
            throw new IllegalArgumentException("IP Address " + ipAddr + " is invalid.");
        }
        Integer ipFirstItem = Integer.parseInt(ipItemArray[0]);
        Set<Entry<String, int[]>> ipTypeSet = TYPE_IP_MAP.entrySet();
        for (Entry<String, int[]> typeEntry : ipTypeSet)
        {
            int[] ipScope = typeEntry.getValue();
            if (ipFirstItem >= ipScope[0] && ipFirstItem <= ipScope[1])
            {
                return typeEntry.getKey();
            }
        }
        return null;
    }
    
    /**
     * A类：10.0.0.0—10.255.255.255 B类：172.16.0.0—172.31.255.255 C类：192.168.0.0—192.168.255.255
     * 
     * @author 01135912
     * @param ipStr
     * @return
     */
    
    public static String getSubIpPrivateType(String ipAddr)
    {
        String[] ipItemArray = ipAddr.split("\\.");
        if (ipItemArray.length < 4)
        {
            throw new IllegalArgumentException("IP Address " + ipAddr + " is invalid.");
        }
        Set<Entry<String, String[]>> ipSubTypeSet = TYPE_IP_SUB_PRIVATE_MAP.entrySet();
        for (Entry<String, String[]> subTypeEntry : ipSubTypeSet)
        {
            String[] ipScope = subTypeEntry.getValue();
            String[] ipItemStart = ipScope[0].split("\\.");
            String[] ipItemEnd = ipScope[1].split("\\.");
            boolean isAllMeet = true;
            for (int itemIdx = 0; itemIdx < 2; itemIdx++)
            {
                boolean isCurrentMeet = ipItemArray[itemIdx].compareTo(ipItemStart[itemIdx]) >= 0
                    && ipItemArray[itemIdx].compareTo(ipItemEnd[itemIdx]) <= 0;
                isAllMeet = isAllMeet && isCurrentMeet;
            }
            
            if (isAllMeet)
            {
                return subTypeEntry.getKey();
            }
        }
        return null;
    }
    
    /**
     * 判断网络设备掩码类别 h3c 存在 0 和 0.0.0.255的情况 juniper 存在 255.255.255.0 或者 IP/24的情况 IP 和 mask 需要格式化，mask 变为为数，标准格式
     * 192.168.1.103/32
     **/
    public static String[] getIpAndMask(String inputIpSource)
    {
        String[] ipSegments = null;
        if (inputIpSource.contains(SOUCE_SEGMENT_SIGN))
        {
            ipSegments = inputIpSource.split(SOUCE_SEGMENT_SIGN);
        }
        else if (inputIpSource.contains(SOUCE_SPLIT_SPACE))
        {
            ipSegments = inputIpSource.split(SOUCE_SPLIT_SPACE);
        }
        else
        {
            ipSegments = new String[] {inputIpSource, SOUCE_MASK_FULL_INT};
        }
        ipSegments[1] = getFormatIpMask(ipSegments[1].trim());
        return ipSegments;
    }
    
    public static String getFormatIpMask(String ipMaskValue)
    {
        if (SOUCE_MASK_NOE_ONE_32.equals(ipMaskValue))
        {
            ipMaskValue = SOUCE_MASK_FULL_INT;
        }
        
        if (!ipMaskValue.contains(SOUCE_SPLIT_SIGN))
        {
            return ipMaskValue;
        }
        
        if (ipMaskValue.startsWith(SOUCE_MASK_NO_START))
        {
            ipMaskValue = String.valueOf(32 - getNetMask(ipMaskValue));
        }
        else
        {
            ipMaskValue = String.valueOf(getNetMask(ipMaskValue));
        }
        return ipMaskValue;
    }
    
    /**
     * 把long类型的Ip转为一般Ip类型：xx.xx.xx.xx
     * 
     * @param ip
     * @return
     */
    public static String getIpStrFromIpLong(Long ip)
    {
        String s1 = String.valueOf(ip /( 256 * 256 * 256));
        long ipLeft = ip % (256 * 256 * 256L);
        String s2 = String.valueOf(ipLeft / (256 * 256));
        ipLeft = ip % (256 * 256L);
        String s3 = String.valueOf(ipLeft / 256);
        ipLeft = ip % (256L);
        String s4 = String.valueOf(ipLeft);
        return s1 + "." + s2 + "." + s3 + "." + s4;
    }
    
    /**
     * 把xx.xx.xx.xx类型的转为long类型的
     * 
     * @param ip
     * @return
     */
    public static Long getIpLongFromStr(String ip)
    {
        if (ip == null || ip.split("\\.").length < 4)
        {
            throw new IllegalArgumentException("Find invalid ip string " + ip);
        }
        
        Long ipLong = 0L;
        String ipTemp = ip;
        ipLong = ipLong * 256 + Long.parseLong(ipTemp.substring(0, ipTemp.indexOf(".")));
        ipTemp = ipTemp.substring(ipTemp.indexOf(".") + 1, ipTemp.length());
        ipLong = ipLong * 256 + Long.parseLong(ipTemp.substring(0, ipTemp.indexOf(".")));
        ipTemp = ipTemp.substring(ipTemp.indexOf(".") + 1, ipTemp.length());
        ipLong = ipLong * 256 + Long.parseLong(ipTemp.substring(0, ipTemp.indexOf(".")));
        ipTemp = ipTemp.substring(ipTemp.indexOf(".") + 1, ipTemp.length());
        ipLong = ipLong * 256 + Long.parseLong(ipTemp);
        return ipLong;
    }
    
    /**
     * 根据掩码位获取掩码
     * 
     * @param maskBit 掩码位数，如"28"、"30"
     * @return
     */
    public static String getMaskByMaskBit(String maskBit)
    {
        return IP_MASK_BIT.get(maskBit);
    }

    /**
     * 根据子网掩码转换为掩码位 如 255.255.255.252转换为掩码位 为 30
     * 
     * @param netmarks
     * @return
     */
    public static int getNetMask(String netmarks)
    {
        StringBuffer sbf;
        String str;
        int inetmask = 0, count = 0;
        String[] ipList = netmarks.split("\\.");
        for (int n = 0; n < ipList.length; n++)
        {
            sbf = toBin(Integer.parseInt(ipList[n]));
            str = sbf.reverse().toString();
            count = 0;
            for (int i = 0; i < str.length(); i++)
            {
                i = str.indexOf('1', i);
                if (i == -1)
                {
                    break;
                }
                count++;
            }
            inetmask += count;
        }
        return inetmask;
    }
    
    /**
     * 计算子网大小
     * 
     * @param netmask 掩码位
     * @return
     */
    public static int getPoolMax(int maskBit)
    {
        if (maskBit <= 0 || maskBit >= 32)
        {
            return 0;
        }
        return (int)Math.pow(2, 32 - maskBit) - 2;
    }
    
    public static String getIpSegmentStr(String[] ipArrayStrs)
    {
        if (ipArrayStrs == null || ipArrayStrs.length == 0)
        {
            return "";
        }
        if (ipArrayStrs.length <= 1 || SOUCE_MASK_FULL_INT.equals(ipArrayStrs[1]))
        {
            return ipArrayStrs[0];
        }
        return StringUtil.appendArrayToString(ipArrayStrs, SOUCE_SEGMENT_SIGN);
    }
    
    private static StringBuffer toBin(int x)
    {
        StringBuffer result = new StringBuffer();
        result.append(x % 2);
        x /= 2;
        while (x > 0)
        {
            result.append(x % 2);
            x /= 2;
        }
        return result;
    }
    
    /*
     * 验证IP是否属于某个IP段 ipSection IP段（以'-'分隔） ip 所验证的IP号码
     */
    public static boolean ipExistsInRange(String ip, String ipSection)
    {
        ipSection = ipSection.trim();
        ip = ip.trim();
        int idx = ipSection.indexOf('-');
        String beginIP = ipSection.substring(0, idx);
        String endIP = ipSection.substring(idx + 1);
        return getIpLongFromStr(beginIP) <= getIpLongFromStr(ip) 
            && getIpLongFromStr(ip) <= getIpLongFromStr(endIP);
    }
    
    public static long[] getIPIntScope(String ip, String maskBit)
    {
        int netMask = Integer.valueOf(maskBit.trim());
        if (netMask < 0 || netMask > 32)
        {
            throw new IllegalArgumentException("invalid ipAndMask with: " + maskBit);
        }
        long ipLong = getIpLongFromStr(ip);
        long netIP = ipLong & (0xFFFFFFFF << (32 - netMask));
        
        if (netMask == 32)
        {
            return new long[] {netIP, netIP};
        }
        int hostScope = (0xFFFFFFFF >>> netMask);
        return new long[] {netIP, netIP + hostScope};
    }
    
    public static long[] getIPIntScope(String ipAndMask)
    {
        String[] ipArr = ipAndMask.split("/");
        String mastBit = SOUCE_MASK_FULL_INT;
        if (ipArr.length > 1)
        {
            mastBit = ipArr[1];
        }
        return getIPIntScope(ipArr[0], mastBit);
    }
    
    /**
     * 获取当前网络ip
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request)
    {
        String ipAddress = request.getHeader("x-forwarded-for");
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress))
        {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress))
        {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress))
        {
            ipAddress = request.getRemoteAddr();
            if (ipAddress.equals(LOCAL_IP_STR) || ipAddress.equals(LOCAL_MACHINE_STR))
            {
                // 根据网卡取本机配置的IP
                InetAddress inet = null;
                try
                {
                    inet = InetAddress.getLocalHost();
                }
                catch (UnknownHostException e)
                {
                    LOG.error("get ip address with unknow exception:{}", e.toString());
                }
                ipAddress = inet.getHostAddress();
            }
        }
        // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ipAddress != null && ipAddress.length() > 15)
        {
            if (ipAddress.indexOf(",") > 0)
            {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
            }
        }
        return ipAddress;
    }
}