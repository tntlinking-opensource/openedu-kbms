package org.pfw.framework.aichat.pojo;

public class Choice {
	private int index;
    private Delta delta;
    private String finish_reason;

    public int getIndex() { return index; }
    public void setIndex(int index) { this.index = index; }
    public Delta getDelta() { return delta; }
    public void setDelta(Delta delta) { this.delta = delta; }
    public String getFinishReason() { return finish_reason; }
    public void setFinishReason(String finish_reason) { this.finish_reason = finish_reason; }
}
