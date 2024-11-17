package com.bob.commontools.utils;


/**
 * @ClassName : IpUtils
 * @Description : TODO
 * @Author : Bob
 * @Date : 2024/11/13 18:26
 * @Version : 1.0
 **/

import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Objects;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.URLUtil;
// import io.micrometer.common.util.StringUtils;
// import org.springframework.http.HttpHeaders;

/**
 * 获取IP方法
 */
public class IpUtils {

    // /**
    //  * 获取IP地址
    //  *
    //  * @return 本地IP地址
    //  */
    // public static String getHostIp() {
    //     try {
    //         return InetAddress.getLocalHost().getHostAddress();
    //     } catch (UnknownHostException e) {
    //     }
    //     return "127.0.0.1";
    // }
    //
    // /**
    //  * 获取客户端IP
    //  *
    //  * @param httpHeaders 请求头
    //  * @return IP地址
    //  */
    // public static String getIpAddressFromHttpHeaders(HttpHeaders httpHeaders) {
    //     if (httpHeaders == null) {
    //         return "unknown";
    //     }
    //     // 前端请求自定义请求头，转发到哪个服务
    //     List<String> ipList = httpHeaders.get("forward-to");
    //     String ip = CollectionUtil.get(ipList, 0);
    //     // 请求自带的请求头
    //     if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
    //         ipList = httpHeaders.get("x-forwarded-for");
    //         ip = CollectionUtil.get(ipList, 0);
    //     }
    //     // 从referer获取请求的ip地址
    //     if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
    //         // 从referer中获取请求的ip地址
    //         List<String> refererList = httpHeaders.get("referer");
    //         String referer = CollectionUtil.get(refererList, 0);
    //         URL url = URLUtil.url(referer);
    //         if (Objects.nonNull(url)) {
    //             ip = url.getHost();
    //         } else {
    //             ip = "unknown";
    //         }
    //     }
    //     if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
    //         ipList = httpHeaders.get("Proxy-Client-IP");
    //         ip = CollectionUtil.get(ipList, 0);
    //     }
    //     if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
    //         ipList = httpHeaders.get("X-Forwarded-For");
    //         ip = CollectionUtil.get(ipList, 0);
    //     }
    //     if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
    //         ipList = httpHeaders.get("WL-Proxy-Client-IP");
    //         ip = CollectionUtil.get(ipList, 0);
    //     }
    //     if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
    //         ipList = httpHeaders.get("X-Real-IP");
    //         ip = CollectionUtil.get(ipList, 0);
    //     }
    //     return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : getMultistageReverseProxyIp(ip);
    // }
    //
    // /**
    //  * 从多级反向代理中获得第一个非unknown IP地址
    //  * <p>
    //  *
    //  * @param ip 获得的IP地址
    //  * @return 第一个非unknown IP地址
    //  */
    //
    // public static String getMultistageReverseProxyIp(String ip) {
    //     if (ip != null && ip.indexOf(",") > 0) {
    //         final String[] ips = ip.split(",");
    //         for (String subIp : ips) {
    //             if (!isUnknown(subIp)){
    //                 ip = subIp;
    //                 break;
    //             }
    //         }
    //     }
    //     return ip;
    // }
    //
    // public static boolean isUnknown(String ip) {
    //     return StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip);
    // }
}

