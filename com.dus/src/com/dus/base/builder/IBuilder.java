package com.dus.base.builder;

import com.dus.base.IEntity;

public interface IBuilder<T extends IEntity> {
	T build();
}
