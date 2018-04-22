package com.jsh.model.vo.materials;

import java.io.Serializable;

@SuppressWarnings("serial")
public class DepotItemShowModel implements Serializable {
    /**
     * 提示信息
     */
    private String msgTip = "";

    public String getMsgTip() {
        return msgTip;
    }

    public void setMsgTip(String msgTip) {
        this.msgTip = msgTip;
    }
}
