package com.zocarro.myvideobook;

public class StreamModel {

    String streamId,streamName;

    public StreamModel(String streamId, String streamName) {
        this.streamId = streamId;
        this.streamName = streamName;
    }

    public String getStreamId() {
        return streamId;
    }

    public void setStreamId(String streamId) {
        this.streamId = streamId;
    }

    public String getStreamName() {
        return streamName;
    }

    public void setStreamName(String streamName) {
        this.streamName = streamName;
    }
}
