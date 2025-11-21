package org.pfw.framework.aichat.pojo;

import java.util.List;

public class SSEEvent {
	private String id;
    private long created;
    private String model;
    private List<Choice> choices;
    private String system_fingerprint;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public long getCreated() { return created; }
    public void setCreated(long created) { this.created = created; }
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
    public List<Choice> getChoices() { return choices; }
    public void setChoices(List<Choice> choices) { this.choices = choices; }
    public String getSystem_fingerprint() { return system_fingerprint; }
    public void setSystem_fingerprint(String system_fingerprint) { this.system_fingerprint = system_fingerprint; }
}
