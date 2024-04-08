package org.mediasoup.droid;

import org.webrtc.PeerConnection;
import org.webrtc.PeerConnectionFactory;

public  class PeerConnectionOptions {

    public PeerConnection.RTCConfiguration mRTCConfig;

    public PeerConnectionFactory mFactory;

    public void setRTCConfig(PeerConnection.RTCConfiguration RTCConfig) {
      mRTCConfig = RTCConfig;
    }

    public void setFactory(PeerConnectionFactory factory) {
      mFactory = factory;
    }
  }