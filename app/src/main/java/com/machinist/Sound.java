package com.machinist;

import android.media.SoundPool;

public class Sound {
	int soundId;
	SoundPool soundPool;
	
	public Sound(SoundPool soundPool, int soundId) {
		this.soundId = soundId;
		this.soundPool = soundPool;
	}
	
	public void Play(float volume) {
		soundPool.play(soundId, volume, volume, 0, 0, 1);
	}
	
	public void Dispose() {
		soundPool.unload(soundId);
	}
}

