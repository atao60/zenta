package org.rulez.magwas.nonnul;

import java.util.Map.Entry;

import org.rulez.magwas.zenta.model.handmade.util.Util;

public class NonNullEntry<T1, T2> implements Entry<T1,T2> {

	private T1 key;
	private T2 value;
	
	NonNullEntry(T1 key, T2 value) {
		this.key = key;
		this.value = value;
	}
	
	
	@Override
	public  T1 getKey() {
		return key;
	}

	
	@Override
	public  T2 getValue() {
		return value;
	}

	
	@Override
	public  T2 setValue( T2 arg0) {
		T2 v = value;
		value = Util.verifyNonNull(arg0);
		return v;
	}

}
