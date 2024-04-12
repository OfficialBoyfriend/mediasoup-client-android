package org.mediasoup.droid;

public class Device {
  private long mNativeDevice;
  public Device() {
    mNativeDevice = nativeNewDevice();
  }
  public void dispose() {
    checkDeviceExists();
    nativeFreeDevice(mNativeDevice);
    mNativeDevice = 0;
  }

  /**
   * Loads the device with the RTP capabilities of the mediasoup router. This is how the device
   * knows about the allowed media codecs and other settings.
   *
   * @param routerRtpCapabilities The mediasoup router RTP capabilities.
   * @param peerConnectionOptions PeerConnection options.
   * @throws MediasoupException, if device already loaded
   */
  public void load(
      String routerRtpCapabilities, PeerConnection.Options peerConnectionOptions)
      throws MediasoupException {
    checkDeviceExists();
    nativeLoad(
        mNativeDevice,
        routerRtpCapabilities,
        (peerConnectionOptions != null ? peerConnectionOptions.mRTCConfig : null),
        (peerConnectionOptions != null && peerConnectionOptions.mFactory != null
            ? peerConnectionOptions.mFactory.getNativePeerConnectionFactory()
            : 0L));
  }

  /**
   * Whether the device has been loaded (see {@link #load(String, PeerConnectionOptions)} method).
   *
   * @return bool
   */
  public boolean isLoaded() {
    checkDeviceExists();
    return nativeIsLoaded(mNativeDevice);
  }

  /**
   * The device RTP capabilities, generated by combining both the underlying WebRTC capabilities and
   * the router RTP capabilities (see {@link #load(String, PeerConnectionOptions)} method).
   *
   * @return string RtpCapabilities
   * @throws MediasoupException, if device not loaded
   */
  public String getRtpCapabilities() throws MediasoupException {
    checkDeviceExists();
    return nativeGetRtpCapabilities(mNativeDevice);
  }

  /**
   * The device SCTP capabilities, generated by combining both the underlying WebRTC capabilities
   * and the router SCTP capabilities (see {@link #load(String, PeerConnectionOptions)} method).
   *
   * @return string SctpCapabilities
   * @throws MediasoupException, if device not loaded
   */
  public String getSctpCapabilities() throws MediasoupException {
    checkDeviceExists();
    return nativeGetSctpCapabilities(mNativeDevice);
  }

  /**
   * Whether the device can produce media of the given kind. This depends on the media codecs
   * enabled in the mediasoup router and the media capabilities of libwebrtc.
   *
   * @param kind "audio" or "video".
   * @return bool
   * @throws MediasoupException, if device not loaded or if invalid kind
   */
  public boolean canProduce(String kind) throws MediasoupException {
    checkDeviceExists();
    return nativeCanProduce(mNativeDevice, kind);
  }

  /**
   * Creates a new WebRTC transport to send media. The transport must be previously created in the
   * mediasoup router via <a
   * href="https://mediasoup.org/documentation/v3/mediasoup/api/#router-createWebRtcTransport">router.createWebRtcTransport()</a>.
   *
   * @param listener The identifier of the server side transport.
   * @param id The identifier of the server side transport.
   * @param iceParameters ICE parameters of the server side transport.
   * @param iceCandidates ICE candidates of the server side transport.
   * @param dtlsParameters DTLS parameters of the server side transport.
   * @return {@link SendTransport}
   * @throws MediasoupException, if device not loaded
   */
  public SendTransport createSendTransport(
      SendTransport.Listener listener,
      String id,
      String iceParameters,
      String iceCandidates,
      String dtlsParameters)
      throws MediasoupException {
    return createSendTransport(
        listener, id, iceParameters, iceCandidates, dtlsParameters, null, null, null);
  }

  /**
   * Creates a new WebRTC transport to send media. The transport must be previously created in the
   * mediasoup router via <a
   * href="https://mediasoup.org/documentation/v3/mediasoup/api/#router-createWebRtcTransport">router.createWebRtcTransport()</a>.
   *
   * @param listener The identifier of the server side transport.
   * @param id The identifier of the server side transport.
   * @param iceParameters ICE parameters of the server side transport.
   * @param iceCandidates ICE candidates of the server side transport.
   * @param dtlsParameters DTLS parameters of the server side transport.
   * @param sctpParameters SCTP parameters of teh server side transport.
   * @return {@link SendTransport}
   * @throws MediasoupException, if device not loaded
   */
  public SendTransport createSendTransport(
      SendTransport.Listener listener,
      String id,
      String iceParameters,
      String iceCandidates,
      String dtlsParameters,
      String sctpParameters)
      throws MediasoupException {
    return createSendTransport(
        listener, id, iceParameters, iceCandidates, dtlsParameters, sctpParameters, null, null);
  }

  /**
   * Creates a new WebRTC transport to send media. The transport must be previously created in the
   * mediasoup router via <a
   * href="https://mediasoup.org/documentation/v3/mediasoup/api/#router-createWebRtcTransport">router.createWebRtcTransport()</a>.
   *
   * @param listener The identifier of the server side transport.
   * @param id The identifier of the server side transport.
   * @param iceParameters ICE parameters of the server side transport.
   * @param iceCandidates ICE candidates of the server side transport.
   * @param dtlsParameters DTLS parameters of the server side transport.
   * @param sctpParameters SCTP parameters of teh server side transport.
   * @return {@link SendTransport}
   * @throws MediasoupException, if device not loaded
   */
  public SendTransport createSendTransport(
      SendTransport.Listener listener,
      String id,
      String iceParameters,
      String iceCandidates,
      String dtlsParameters,
      String sctpParameters,
      PeerConnection.Options options,
      String appData)
      throws MediasoupException {
    checkDeviceExists();
    return nativeCreateSendTransport(
        mNativeDevice,
        listener,
        id,
        iceParameters,
        iceCandidates,
        dtlsParameters,
        sctpParameters,
        (options != null ? options.mRTCConfig : null),
        (options != null && options.mFactory != null
            ? options.mFactory.getNativePeerConnectionFactory()
            : 0L),
        appData);
  }

  /**
   * Creates a new WebRTC transport to receive media. The transport must be previously created in
   * the mediasoup router via <a
   * href="https://mediasoup.org/documentation/v3/mediasoup/api/#router-createWebRtcTransport">router.createWebRtcTransport()</a>.
   *
   * @param listener The identifier of the server side transport.
   * @param id The identifier of the server side transport.
   * @param iceParameters ICE parameters of the server side transport.
   * @param iceCandidates ICE candidates of the server side transport.
   * @param dtlsParameters DTLS parameters of the server side transport.
   * @return {@link RecvTransport}
   * @throws MediasoupException , if device not loaded
   */
  public RecvTransport createRecvTransport(
      RecvTransport.Listener listener,
      String id,
      String iceParameters,
      String iceCandidates,
      String dtlsParameters)
      throws MediasoupException {
    return createRecvTransport(
        listener, id, iceParameters, iceCandidates, dtlsParameters, null, null, null);
  }

  /**
   * Creates a new WebRTC transport to receive media. The transport must be previously created in
   * the mediasoup router via <a
   * href="https://mediasoup.org/documentation/v3/mediasoup/api/#router-createWebRtcTransport">router.createWebRtcTransport()</a>.
   *
   * @param listener The identifier of the server side transport.
   * @param id The identifier of the server side transport.
   * @param iceParameters ICE parameters of the server side transport.
   * @param iceCandidates ICE candidates of the server side transport.
   * @param dtlsParameters DTLS parameters of the server side transport.
   * @param sctpParameters SCTP parameters of teh server side transport.
   * @return {@link RecvTransport}
   * @throws MediasoupException , if device not loaded
   */
  public RecvTransport createRecvTransport(
      RecvTransport.Listener listener,
      String id,
      String iceParameters,
      String iceCandidates,
      String dtlsParameters,
      String sctpParameters) {
    return createRecvTransport(
        listener, id, iceParameters, iceCandidates, dtlsParameters, sctpParameters, null, null);
  }
  /**
   * Creates a new WebRTC transport to receive media. The transport must be previously created in
   * the mediasoup router via <a
   * href="https://mediasoup.org/documentation/v3/mediasoup/api/#router-createWebRtcTransport">router.createWebRtcTransport()</a>.
   *
   * @param listener The identifier of the server side transport.
   * @param id The identifier of the server side transport.
   * @param iceParameters ICE parameters of the server side transport.
   * @param iceCandidates ICE candidates of the server side transport.
   * @param dtlsParameters DTLS parameters of the server side transport.
   * @param sctpParameters SCTP parameters of teh server side transport.
   * @param options PeerConnection options.
   * @param appData Custom application data.
   * @return {@link RecvTransport}
   * @throws MediasoupException, if device not loaded
   */
  public RecvTransport createRecvTransport(
      RecvTransport.Listener listener,
      String id,
      String iceParameters,
      String iceCandidates,
      String dtlsParameters,
      String sctpParameters,
      PeerConnection.Options options,
      String appData) {
    checkDeviceExists();
    return nativeCreateRecvTransport(
        mNativeDevice,
        listener,
        id,
        iceParameters,
        iceCandidates,
        dtlsParameters,
        sctpParameters,
        (options != null ? options.mRTCConfig : null),
        (options != null && options.mFactory != null
            ? options.mFactory.getNativePeerConnectionFactory()
            : 0L),
        appData);
  }

  private void checkDeviceExists() {
    if (mNativeDevice == 0) {
      throw new IllegalStateException("Device has been disposed.");
    }
  }

  private static native long nativeNewDevice();

  private static native void nativeFreeDevice(long device);

  // may throws MediasoupException;
  private static native void nativeLoad(
      long device,
      String routerRtpCapabilities,
      org.webrtc.PeerConnection.RTCConfiguration configuration,
      long peerConnectionFactory);

  private static native boolean nativeIsLoaded(long device);

  // may throws MediasoupException;
  private static native String nativeGetRtpCapabilities(long device);

  // may throws MediasoupException;
  private static native String nativeGetSctpCapabilities(long device);

  // may throws MediasoupException;
  private static native boolean nativeCanProduce(long device, String kind);

  // may throws MediasoupException;
  private static native SendTransport nativeCreateSendTransport(
      long device,
      SendTransport.Listener listener,
      String id,
      String iceParameters,
      String iceCandidates,
      String dtlsParameters,
      String sctpParameters,
      org.webrtc.PeerConnection.RTCConfiguration configuration,
      long peerConnectionFactory,
      String appData);

  // may throws MediasoupException;
  private static native RecvTransport nativeCreateRecvTransport(
      long device,
      RecvTransport.Listener listener,
      String id,
      String iceParameters,
      String iceCandidates,
      String dtlsParameters,
      String sctpParameters,
      org.webrtc.PeerConnection.RTCConfiguration configuration,
      long peerConnectionFactory,
      String appData);
}
