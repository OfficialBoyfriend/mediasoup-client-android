/*
 *  Copyright (c) 2016 The WebRTC project authors. All Rights Reserved.
 *
 *  Use of this source code is governed by a BSD-style license
 *  that can be found in the LICENSE file in the root of the source
 *  tree. An additional intellectual property rights grant can be found
 *  in the file PATENTS.  All contributing project authors may
 *  be found in the AUTHORS file in the root of the source tree.
 */

#include "media/engine/null_webrtc_video_engine.h"
#include "absl/memory/memory.h"
#include "media/engine/webrtc_voice_engine.h"
#include "modules/audio_device/include/mock_audio_device.h"
#include "modules/audio_processing/include/audio_processing.h"
#include "test/gtest.h"
#include "test/mock_audio_decoder_factory.h"
#include "test/mock_audio_encoder_factory.h"

namespace cricket {

class WebRtcMediaEngineNullVideo : public CompositeMediaEngine {
 public:
  WebRtcMediaEngineNullVideo(
      webrtc::AudioDeviceModule* adm,
      const rtc::scoped_refptr<webrtc::AudioEncoderFactory>&
          audio_encoder_factory,
      const rtc::scoped_refptr<webrtc::AudioDecoderFactory>&
          audio_decoder_factory)
      : CompositeMediaEngine(absl::make_unique<WebRtcVoiceEngine>(
                                 adm,
                                 audio_encoder_factory,
                                 audio_decoder_factory,
                                 nullptr,
                                 webrtc::AudioProcessingBuilder().Create()),
                             absl::make_unique<NullWebRtcVideoEngine>()) {}
};

// Simple test to check if NullWebRtcVideoEngine implements the methods
// required by CompositeMediaEngine.
TEST(NullWebRtcVideoEngineTest, CheckInterface) {
  testing::NiceMock<webrtc::test::MockAudioDeviceModule> adm;
  WebRtcMediaEngineNullVideo engine(
      &adm, webrtc::MockAudioEncoderFactory::CreateUnusedFactory(),
      webrtc::MockAudioDecoderFactory::CreateUnusedFactory());
  EXPECT_TRUE(engine.Init());
}

}  // namespace cricket