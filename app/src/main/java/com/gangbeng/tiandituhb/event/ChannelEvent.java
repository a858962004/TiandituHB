package com.gangbeng.tiandituhb.event;

/**
 * @author zhanghao
 * @date 2018-08-02
 */

public class ChannelEvent {
    private String channel;
    public ChannelEvent(String channel){
        this.channel=channel;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }
}
